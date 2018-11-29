package com.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Area_Solicitante;
import com.auth.entity.Estado_Jira;
import com.auth.entity.Etiqueta;
import com.auth.entity.Fabrica;
import com.auth.entity.Indicador_Contable;
import com.auth.entity.Jira;
import com.auth.entity.Jira_Detalle;
import com.auth.entity.JsoJira;
import com.auth.entity.JsoPersonalizado;
import com.auth.entity.Tipo_Requerimiento;
import com.auth.repository.IAreaSolicitanteRepository;
import com.auth.repository.IEstadoJiraRepository;
import com.auth.repository.IEtiquetaRepository;
import com.auth.repository.IFabricaRepository;
import com.auth.repository.IIndicadorContableRepository;
import com.auth.repository.IJiraDetalleRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.ITipoRequerimientoRepository;
import com.auth.repository.JiraApiRepository;
@Service
public class JiraService implements IJiraService {
	@Autowired
	IJiraRepository jiraRepo;
	@Autowired
	JiraApiRepository apiJira;
	@Autowired
	IJiraDetalleRepository detalleRepo;
	@Autowired
	IFabricaRepository fabricaRepo;
	@Autowired
	IEtiquetaRepository etiquetaRepo;
	@Autowired
	IAreaSolicitanteRepository areaRepo;
	@Autowired
	IIndicadorContableRepository indicadorRepository;
	@Autowired
	ITipoRequerimientoRepository tipoRepository;
	@Autowired
	IEstadoJiraRepository estadoRepo;

	@Override
	public Jira buscarPorId(Integer id) {
		return jiraRepo.findById(id).orElse(null);
	}

	@Override
	public Jira guardar(Jira j) {
		return jiraRepo.save(j);
	}

	@Override
	public Jira buscarPorJira(String jira) {
		return jiraRepo.findByJira(jira);
	}

	@Override
	public List<Jira> listarJiras() {
		return jiraRepo.findAllByOrderByEtiqueta();
	}

	@Override
	public void actualizarBD() {			
		List<JsoJira> lstJsoJira = getListaJsonJiraActualizada("labels+in+(GSOCompromiso,GSOCritico,GSOMejoraBD,GSOPrio,BVLCompromiso,BVLCritico,BVLMejoraBD,BVLPrio)+and+status+not+in(Terminado,Anulado)+ORDER+BY+labels+DESC");
		//Limpiar tabla y reiniciar id (PK)
		jiraRepo.deleteAll(); 
		int id = 1;
		//PASAR LOS DATOS DE JIRA-JSON A JIRA-BD
		for (JsoJira jsonJira:lstJsoJira) {				
			Jira bdJira = new Jira();
			// ID
			bdJira.setId(id);
			// JIRA
			bdJira.setJira(jsonJira.getKey());
			// RESUMEN
			bdJira.setResumen(jsonJira.getFields().getSummary());
			// INFORMADOR
			if (jsonJira.getFields().getReporter() != null)
				bdJira.setInformador(jsonJira.getFields().getReporter().getDisplayName());						
			// ASIGNADO
			if (jsonJira.getFields().getAssignee() != null)
				bdJira.setAsignado(jsonJira.getFields().getAssignee().getDisplayName());						
			// RESPONSABLE
			if (jsonJira.getFields().getCustomfield_11236() != null)
				bdJira.setResponsable(jsonJira.getFields().getCustomfield_11236().getDisplayName());
			// HORAS  CERT Y DESA
			bdJira.setHoras_des(jsonJira.getFields().getCustomfield_14850());
			bdJira.setHoras_cert(jsonJira.getFields().getCustomfield_14851());			
			if (!noEsNuloOVacio(bdJira.getHoras_cert()) || !noEsNuloOVacio(bdJira.getHoras_des())) {
				int horaCert = 0;
				int horasDes= 0;
				List<JsoJira> subTareas = jsonJira.getFields().getSubTareas();						
				for (JsoJira jhijo: subTareas) {
					horaCert = horaCert + jhijo.getFields().getCustomfield_14851();
					horasDes = horasDes + jhijo.getFields().getCustomfield_14850();
				}
				if (bdJira.getHoras_cert() == 0)
					bdJira.setHoras_cert(horaCert);				
				if (bdJira.getHoras_des() == 0)
					bdJira.setHoras_des(horasDes);				
			}			
			// FÁBRICA 	
			if (jsonJira.getFields().getCustomfield_11016() != null) {
				Fabrica fabrica = fabricaExisteOnuevo(jsonJira.getFields().getCustomfield_11016().getValue());				
				bdJira.setFabrica(fabrica);				
			}
				
			if (bdJira.getHoras_des() != 0 && bdJira.getFabrica() != null )
				bdJira.setMonto_des(bdJira.getHoras_des()*bdJira.getFabrica().getTarifa());
			else 
				bdJira.setMonto_des(0);
			
			if (bdJira.getHoras_cert() != 0 && bdJira.getFabrica() != null )
				bdJira.setMonto_cert(bdJira.getHoras_cert()*bdJira.getFabrica().getTarifa());
			else
				bdJira.setMonto_cert(0);
			
			bdJira.setMonto_total(bdJira.getMonto_cert() + bdJira.getMonto_des());
			
			// FECHAS*/
			bdJira.setFecha_actualizacion(jsonJira.getFields().getUpdated());
			bdJira.setFecha_creacion(jsonJira.getFields().getCreated());
			
			Jira_Detalle detalle_jira = detalleRepo.findByJira(bdJira.getJira());
			if (detalle_jira != null) {
				bdJira.setFecha_pruebas(detalle_jira.getFecha_pr_usuario());
				bdJira.setFecha_produccion(detalle_jira.getFecha_produccion());
			}
			
			// ETIQUETAS
			Etiqueta etiqueta = etiquetaExisteOnuevo(jsonJira.getFields().getEtiqueta());
			bdJira.setEtiqueta(etiqueta);
			// AREA SOLICITANTE
			if (noEsNuloOVacio(jsonJira.getFields().getCustomfield_10800() )) {
				Area_Solicitante area = areaExisteOnuevo(jsonJira.getFields().getCustomfield_10800().getValue());
				bdJira.setAreaSolicitante(area);
			}
			// INDICADOR CONTABLE
			if (noEsNuloOVacio(jsonJira.getFields().getCustomfield_11483())) {
				Indicador_Contable indicador =   indicadorExisteOnuevo(jsonJira.getFields().getCustomfield_11483().getValue());
				bdJira.setIndicador(indicador);
			}		
			// TIPO DE REQUERIMIENTO
			if (noEsNuloOVacio(jsonJira.getFields().getIssuetype())) {
				Tipo_Requerimiento tipo = tipoExisteOnuevo(jsonJira.getFields().getIssuetype().getName());
				bdJira.setTipoRequerimiento(tipo);
			}
			// ESTADO JIRA
			Estado_Jira estado = estadoExistenteOnuevo(jsonJira.getFields().getNuevoEstado());
			bdJira.setEstadoJira(estado);	
			jiraRepo.save(bdJira);
			id++;
		}
	}

	// PROCEDIMIENTOS COMPLEMENTARIOS

	// CONSUME SERVICIO REST DE JIRA Y ACTUALIZA LOS CAMPOS
	private List<JsoJira> getListaJsonJiraActualizada(String filtro) {
		List<JsoJira> jsoJiras = new ArrayList<>();
		filtro = filtro + "&maxResults=500&fields=key,customfield_10800,customfield_11483,issuetype,summary,status,assignee,reporter,created,updated,customfield_11016,customfield_14851,customfield_14850,priority,parent,labels,customfield_11236";
		jsoJiras = apiJira.busquedaJQL(filtro);
		
		//Actualizar hijos
		jsoJiras = reconocerHijos(jsoJiras);		
		//Actualizar
		for (JsoJira j : jsoJiras) {
			j = actualizarTipo(j);			//tipo
			j = indicadorContableSCP(j);	//IC (En caso SCP)
			j = cambiarAreaSolicitante(j);			
			j = cambiaEstado(j);		
		}		
		return jsoJiras;
	}
	
	private List<JsoJira> reconocerHijos(List<JsoJira> jiras) {		
		String filtro = "parent+in+(";	//Primeros 1000 resupuestas
		String filtro2 ="";				//De 1000 a 2000 ...
		int i = 0;
		//Se crea el texto para consultar al API y se realiza la consulta
		for(JsoJira j: jiras) {
			if (i == 0)
				filtro = filtro + j.getKey();
			else
				filtro = filtro +","+ j.getKey();
			i++;
		}					
		filtro = filtro +")+and+status+not+in+(Anulado,Creado)&maxResults=1000&fields=key,issuetype,parent,status,assignee,updated,customfield_14851,customfield_14850";		
		filtro2 = filtro + "&startAt=1000";		
		List<JsoJira> lstSubTareas = apiJira.busquedaJQL(filtro);				
		lstSubTareas.addAll(apiJira.busquedaJQL(filtro2));		
		//Se asignan todas las sub tareas a los padres
		if (lstSubTareas != null) {					
			for (JsoJira jpadre: jiras) {
				List<JsoJira> lstaux = new ArrayList<>();
				lstaux = lstSubTareas.stream().filter(x -> x.getFields().getParent().getKey().equals(jpadre.getKey())).collect(Collectors.toList());
				jpadre.getFields().setSubTareas(lstaux);
			}
		}
		return jiras;
	}
	//Elegir una etiqueta de la lista
	private JsoJira actualizarTipo(JsoJira j) {
		if (j.getFields().getLabels().contains("GSOCompromiso") || j.getFields().getLabels().contains("BVLCompromiso")) 
			j.getFields().setEtiqueta("Compromiso");
		else if (j.getFields().getLabels().contains("GSOCritico") || j.getFields().getLabels().contains("BVLCritico")) 
			j.getFields().setEtiqueta("Crítico");
		else if (j.getFields().getLabels().contains("GSOMejoraBD") || j.getFields().getLabels().contains("BVLMejoraBD")) 
			j.getFields().setEtiqueta("Mejora BD");
		else if (j.getFields().getLabels().contains("GSOPrio") || j.getFields().getLabels().contains("BVLPrio")) 
			j.getFields().setEtiqueta("Prioritario");	
		else if (j.getFields().getLabels().contains("GSORegular") || j.getFields().getLabels().contains("BVLRegular")) 
			j.getFields().setEtiqueta("Regular");	
		else 
			j.getFields().setEtiqueta("No definido");
		return j;
	}
	//Si hay requerimientos de tipo SCP, asignará el tipo inversión
	private JsoJira indicadorContableSCP(JsoJira j) {		
		JsoPersonalizado nuevo_per = new JsoPersonalizado();
		nuevo_per.setValue("Inversión");
		String codigo = j.getKey().substring(0, 3);			
		if (codigo.equals("SCP")) {
			j.getFields().setCustomfield_11483(nuevo_per);
		}	
		return j;
	}	
	private JsoJira cambiarAreaSolicitante(JsoJira j) {		
		//LIQUIDACIONES
		if (j.getFields().getCustomfield_10800().getValue().equals("Negocios Internacionales") ||
				j.getFields().getCustomfield_10800().getValue().equals("Liquidaciones") ||
				j.getFields().getCustomfield_10800().getValue().equals("SERVICIO DE LIQUIDACIONES") ||
				j.getFields().getCustomfield_10800().getValue().equals("GERENCIA DE SERVICIOS Y OPERACIONES") )
			j.getFields().getCustomfield_10800().setValue("LIQ");		
		// SISTEMAS
		else if (j.getFields().getCustomfield_10800().getValue().equals("Sistemas") ||
				j.getFields().getCustomfield_10800().getValue().equals("OPERACIONES TI"))
			j.getFields().getCustomfield_10800().setValue("OTI");
		
		//GESTIÓN DE ACTIVOS
		else if (j.getFields().getCustomfield_10800().getValue().equals("SERVICIOS GENERALES Y CONTROL DE ACTIVOS") ||
				j.getFields().getCustomfield_10800().getValue().equals("GESTION DE ACTIVOS") ||
				j.getFields().getCustomfield_10800().getValue().equals("GESTIÓN DE ACTIVOS") ||
				j.getFields().getCustomfield_10800().getValue().equals("Valores")) 
			j.getFields().getCustomfield_10800().setValue("GA");
		
		//PARTICIPANTES
		else if (j.getFields().getCustomfield_10800().getValue().equals("Participantes") ||
				j.getFields().getCustomfield_10800().getValue().equals("SERVICIO DE PARTICIPANTES")) 
			j.getFields().getCustomfield_10800().setValue("PAR");
		
		//FACTRACK
		else if (j.getFields().getCustomfield_10800().getValue().equals("Facturas Negociables") ||
				j.getFields().getCustomfield_10800().getValue().equals("GERENCIA DE NEGOCIOS TRANSACCIONALES")) 
			j.getFields().getCustomfield_10800().setValue("FT");
		else 
			j.getFields().getCustomfield_10800().setValue("Otros");		
		
		return j;
	}
	private JsoJira cambiaEstado(JsoJira j) {		
		String strEstadoAnterior = j.getFields().getStatus().getName();
		String strEstadoNuevo = "";
		String grupoEstado= "";
		String nuevoResponsable="";
		String clase_estado="est_";
					
		if(strEstadoAnterior.equals("En Revisión")){				
			strEstadoNuevo = "En revisión";
	        grupoEstado = "Postproducción";
	        clase_estado="est_pospro";
	        nuevoResponsable = j.getFields().getAssignee().getDisplayName();
		}else if(strEstadoAnterior.equals("Alcance")){
	        strEstadoNuevo = "En validación de alcance";
	        grupoEstado = "Análisis";
	        clase_estado="est_ana";
	        nuevoResponsable = j.getFields().getAssignee().getDisplayName();
		}else if(strEstadoAnterior.equals("En Espera")){
	        strEstadoNuevo = "En pausa";
	        grupoEstado = "En espera";
	        clase_estado="est_";
	        nuevoResponsable = j.getFields().getAssignee().getDisplayName();
		}else if(strEstadoAnterior.equals("Análisis")){
	        strEstadoNuevo = "En preanálisis";
	        grupoEstado = "Análisis";
	        clase_estado="est_ana";
	        nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	        
	        JsoJira ultimoHijo = hijoMasReciente(j);
	    	if (noEsNuloOVacio(ultimoHijo)) {
	    		if (ultimoHijo.getFields().getIssuetype().getName().equals("Análisis")){
		        	strEstadoNuevo = "En análisis";
                    grupoEstado = "Análisis";
                    clase_estado="est_ana";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Alcance Funcional")){
		        	strEstadoNuevo = "En preparación de alcance";
                    grupoEstado = "Inicial";
                    clase_estado="est_ini";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Alcance")){
		        	strEstadoNuevo = "En preparación de alcance";
                    grupoEstado = "Inicial";
                    clase_estado="est_ini";
	    		}else {
	    			strEstadoNuevo = "?";
                    grupoEstado = "?";
                    clase_estado="est_";
	    		}	
	    	}
	    }else if(strEstadoAnterior.equals("En Aprobación")){
	         strEstadoNuevo = "En aprobación de horas";
	         grupoEstado = "Aprobación";
	         clase_estado="est_aprob";
	         nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    }else if(strEstadoAnterior.equals("Control de Calidad")){
	         strEstadoNuevo = "Pruebas QA";
	         grupoEstado = "QA";
	         clase_estado="est_qa";
	         nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    }else if(strEstadoAnterior.equals("Supervisión QA") || strEstadoAnterior.equals("Pruebas QA")){
	         strEstadoNuevo = "Pruebas QA";
	         grupoEstado = "QA";
	         clase_estado="est_qa";
	         nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    }else if(strEstadoAnterior.equals("Desarrollo") || strEstadoAnterior.equals("En Desarrollo")){
	         strEstadoNuevo = "Construcción";
	         grupoEstado = "Construcción";
	         clase_estado="est_cons";
	         nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    }else if(strEstadoAnterior.equals("Creado")){
	         strEstadoNuevo = "Pendiente de enviar a sistemas";
	         grupoEstado = "Inicial";
	         clase_estado="est_ini";
	         nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    }else if(strEstadoAnterior.equals("Ejecución")){	
	  
	    	JsoJira ultimoHijo = hijoMasReciente(j);
	    	if (noEsNuloOVacio(ultimoHijo)) {
	    		if (ultimoHijo.getFields().getIssuetype().getName().equals("Evidencia")) {
		        	strEstadoNuevo = "Pruebas de Usuario";
                    grupoEstado = "Usuario";	
                    clase_estado="est_usu";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Validación Usuario")){
		        	strEstadoNuevo = "Pruebas de usuario";
                    grupoEstado = "Usuario";
                    clase_estado="est_usu";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Observacion Calidad")){
		        	strEstadoNuevo = "Pruebas QA";
                    grupoEstado = "QA";
                    clase_estado="est_qa";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Desarrollo")){
		        	strEstadoNuevo = "En construcción";
                    grupoEstado = "Construcción";
                    clase_estado="est_cons";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Análisis")){
		        	strEstadoNuevo = "En análisis";
                    grupoEstado = "Análisis";
                    clase_estado="est_ana";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Alcance Funcional")){
		        	strEstadoNuevo = "En preparación de alcance";
                    grupoEstado = "Inicial";
                    clase_estado="est_ini";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Alcance")){
		        	strEstadoNuevo = "En preparación de alcance";
                    grupoEstado = "Inicial";
                    clase_estado="est_ini";
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Estimación")){
		        	strEstadoNuevo = "En estimación";
                    grupoEstado = "Inicial";
                    clase_estado="est_ini";	     
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Funcionalidad Adjunta")){
		        	strEstadoNuevo = "Funcionalidad Adjunta";
                    grupoEstado = "xxx";
                    clase_estado="est_";	 
	    		}else if (ultimoHijo.getFields().getIssuetype().getName().equals("Caso de Prueba")){
	    			if (ultimoHijo.getFields().getStatus().getName().equals("Pruebas QA")) {
	    				strEstadoNuevo = "Pruebas QA";
	                    grupoEstado = "QA";
	                    clase_estado="est_qa";
	    			}
	    			else if (ultimoHijo.getFields().getStatus().getName().equals("Pruebas QC")) {
	    				strEstadoNuevo = "Pruebas QC";
	                    grupoEstado = "Construcción";
	                    clase_estado="est_cons";
	    			}
	    			else if (ultimoHijo.getFields().getStatus().getName().equals("Observado")) {
	    				strEstadoNuevo = "Observado en pruebas";
	                    grupoEstado = "QA";
	                    clase_estado="est_qa";
	    			}
	    			else if (ultimoHijo.getFields().getStatus().getName().equals("Por Hacer")) {
	    				strEstadoNuevo = "En construcción";
	                    grupoEstado = "Construcción";
	                    clase_estado="est_cons";
	    			}
	    			else if (ultimoHijo.getFields().getStatus().getName().equals("En Revisión")) {
	    				strEstadoNuevo = "Pruebas QA";
	                    grupoEstado = "QA";
	                    clase_estado="est_qa";
	    			}else {
	    				strEstadoNuevo = "Indefinido";
	                    grupoEstado = "xx";
	                    clase_estado="est_";
	    			}		    	
	    		}else {
	    			strEstadoNuevo = "?";
                    grupoEstado = "?";
                    clase_estado="est_";
	    		}	    		
	    		if(noEsNuloOVacio(ultimoHijo.getFields().getAssignee()))
	    			nuevoResponsable = ultimoHijo.getFields().getAssignee().getDisplayName();		    		
	    	}		    	         		         

	    }else if(strEstadoAnterior.equals("Pendiente") || strEstadoAnterior.equals("PENDIENTE") ){
	    	
	    	JsoJira ultimoHijo = hijoMasReciente(j);
	    	if (noEsNuloOVacio(ultimoHijo)) {		    
		    	if (ultimoHijo.getFields().getIssuetype().getName().equals("Evidencia")) {
		        	strEstadoNuevo = "Pruebas de Usuario";
                    grupoEstado = "Usuario";	
                    clase_estado="est_usu";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Observacion Calidad")){
		        	strEstadoNuevo = "Pruebas QA";
                    grupoEstado = "QA";
                    clase_estado="est_qa";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Desarrollo")){
		        	strEstadoNuevo = "En construcción";
                    grupoEstado = "Construcción";
                    clase_estado="est_cons";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Análisis")){
		        	strEstadoNuevo = "En análisis";
                    grupoEstado = "Análisis";
                    clase_estado="est_ana";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Alcance Funcional")){
		        	strEstadoNuevo = "En preparación de alcance";
                    grupoEstado = "Análisis";
                    clase_estado="est_ana";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Estimación")){
		        	strEstadoNuevo = "En estimación";
                    grupoEstado = "Análisis";
                    clase_estado="est_ana";
		        }else if (ultimoHijo.getFields().getIssuetype().getName().equals("Caso de Prueba")){
		        	if(ultimoHijo.getFields().getStatus().getName().equals("Pruebas QA")) {
		        		strEstadoNuevo = "Pruebas QA";
                        grupoEstado = "QA";		
                        clase_estado="est_qa";
		        	}else if (ultimoHijo.getFields().getStatus().getName().equals("Pruebas QC")) {
		        		strEstadoNuevo = "Pruebas QC";
		        		grupoEstado = "Construcción";
		        		clase_estado="est_cons";
		        	}else if (ultimoHijo.getFields().getStatus().getName().equals("Observado")) {
		        		strEstadoNuevo = "Observado en pruebas";
		        		grupoEstado = "QA";
		        		clase_estado="est_qa";
		        	}else if (ultimoHijo.getFields().getStatus().getName().equals("Por Hacer")) {
		        		strEstadoNuevo = "En construcción";
		        		grupoEstado = "Construcción";
		        		clase_estado="est_cons";
		        	}else {
		        		strEstadoNuevo = "Falta definir - " + ultimoHijo.getKey();
		        		grupoEstado = "??";
		        		clase_estado="est_";
		        	}
		        }else {
		        	strEstadoNuevo = "Falta definir - " + ultimoHijo.getKey();
		        	grupoEstado = "???";
		        	clase_estado="est_";
		        }   
		    	nuevoResponsable = ultimoHijo.getFields().getAssignee().getDisplayName();
	    	}else {
	    		 strEstadoNuevo = "Por iniciar";
	    		 grupoEstado = "Inicial";
	    		 clase_estado="est_ini";
	    		 nuevoResponsable = j.getFields().getAssignee().getDisplayName();
	    	}
	    	
	    }else{
	         strEstadoNuevo = "Falta definir - " + j.getKey();
	         grupoEstado = "Indefinido";
	         clase_estado="est_";
	         nuevoResponsable = "Indefinido";
	    }			
		j.getFields().setNuevoEstado(strEstadoNuevo);
		j.getFields().setGrupoEstado(grupoEstado);
		j.getFields().setNuevoResponsable(nuevoResponsable);
		j.getFields().setGestado_class(clase_estado);
		return j;
	}
	//Hijo más reciente con estado no terminado
	private JsoJira hijoMasReciente(JsoJira j) {						
		List<JsoJira> lsthijos = j.getFields().getSubTareas();
		if (noEsNuloOVacio(lsthijos) && lsthijos.size() != 0) {	
			JsoJira ultimoHijo = null;				
			//Obtener un hijo cuyo estado no sea terminado o resuleto para empezar la siguiente iteración
			for(JsoJira hijo: lsthijos) {
				if (!hijo.getFields().getStatus().getName().equals("Terminado") && !hijo.getFields().getStatus().getName().equals("Resuelto")){
					ultimoHijo = hijo;
					break;
				}											
			}
			if (ultimoHijo == null)
				return null;	//Todos los hijos están resueltos, no hay ultimo hijo
			//Se determina el hijo con cambios más recientes
			for(JsoJira hijo: lsthijos) {	
				if((hijo.getFields().getUpdated().compareTo(ultimoHijo.getFields().getUpdated()) >= 0) && 
						(!hijo.getFields().getStatus().getName().equals("Terminado") && !hijo.getFields().getStatus().getName().equals("Resuelto"))) {
					ultimoHijo = hijo;
				}
			}	
			return ultimoHijo;				
		}else {
			return null;
		}
	}
	private static boolean noEsNuloOVacio(Object obj) {
        if (obj != null && obj.toString().trim().length() > 0) {
            return true;
        }
        return false;
    }	
// ----------------------------------------------------------- BASE DE DATOS ------------------------------------------
	private Estado_Jira estadoExistenteOnuevo(String estado) {
		Estado_Jira estado_jira = estadoRepo.findByEstado(estado);
		if (estado_jira == null) {
			estado_jira = new Estado_Jira();
			estado_jira.setEstado(estado);
			estado_jira = estadoRepo.save(estado_jira);
		}	
		return estado_jira;
	}
	private Tipo_Requerimiento tipoExisteOnuevo(String tipo) {
		Tipo_Requerimiento tipo_reque = tipoRepository.findByNombre(tipo);
		if (tipo_reque == null) {
			tipo_reque = new Tipo_Requerimiento();
			tipo_reque.setNombre(tipo);
			tipo_reque = tipoRepository.save(tipo_reque);
		}
		return tipo_reque;
	}
	private Indicador_Contable indicadorExisteOnuevo(String str) {
		Indicador_Contable indicador = indicadorRepository.findByIndicador(str);
		if (indicador == null) {
			indicador = new Indicador_Contable();
			indicador.setIndicador(str);
			indicador = indicadorRepository.save(indicador);
		}
		return indicador;
	}
	private Area_Solicitante areaExisteOnuevo(String str) {
		Area_Solicitante area = areaRepo.findByNombrecorto(str);
		if (area == null) {
			area = new Area_Solicitante();
			area.setNombrecorto(str);
			area = areaRepo.save(area);			
		}
		return area;
	}
	private Etiqueta etiquetaExisteOnuevo(String str) {
		Etiqueta etiqueta = etiquetaRepo.findByNombre(str);
		if (etiqueta == null) {
			etiqueta = new Etiqueta();
			etiqueta.setNombre(str);
			etiqueta = etiquetaRepo.save(etiqueta);
		}
		return etiqueta;
	}
	private Fabrica fabricaExisteOnuevo(String str) {
		Fabrica fab = fabricaRepo.findByNombre(str);
		if (fab == null) {
			fab = new Fabrica();
			fab.setNombre(str);
			fab = fabricaRepo.save(fab);
		}
		return fab;
	}
}
