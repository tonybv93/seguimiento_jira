package com.auth.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Empresa;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Fabrica;
import com.auth.entity.HJira;
import com.auth.entity.Indicador_Contable;
import com.auth.entity.JsoJira;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Tipo_Actividad_Proveedor;
import com.auth.entity.Usuario;
import com.auth.repository.IEmpresaRepository;
import com.auth.repository.IEstadoRegHorasRepository;
import com.auth.repository.IFabricaRepository;
import com.auth.repository.IHJiraRepository;
import com.auth.repository.IIndicadorContableRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.repository.IProveedorRegHorasRepository;
import com.auth.repository.ITipoActividadProveedor;
import com.auth.repository.IUsuarioRepository;
import com.auth.repository.JiraApiRepository;
import com.auth.rest.RespGenerica;

@Service
public class RegistroHorasService implements IRegistroHorasService {
	@Autowired
	IProveedorRegHorasRepository regHorasRepo;
	@Autowired
	IJiraRepository jiraRepo;
	@Autowired
	IUsuarioRepository usuarioRepo;	
	@Autowired
	IPeriodoRepository periodoRepo;
	@Autowired
	IEstadoRegHorasRepository estadoRepo;
	@Autowired
	IHJiraRepository hxjRepo;
	@Autowired
	JiraApiRepository jiraResRepo;
	@Autowired
	ITipoActividadProveedor tipoActividadRepo;
	@Autowired
	IFabricaRepository fabricaRepo;
	@Autowired
	IEmpresaRepository empresaRepo;
	@Autowired
	IIndicadorContableRepository indicadorRepo;
	
	
	@Override
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros) {
		for (Proveedor_Reg_Horas registro: listaRegistros ) {
			regHorasRepo.save(registro);
		}		
	}

	@Override
	public Proveedor_Reg_Horas guardarRegHoras(Proveedor_Reg_Horas registro) {
		return regHorasRepo.save(registro);
	}

	@Override
	public void eliminarRegistro(int id) {
		regHorasRepo.deleteById(id);
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosEnviadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarEnviadoPorUsuario(desarrollador.getId());
	}
	
	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarConfirmadosPorUsuario(desarrollador.getId());
	}
	
	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosAprobadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarAprobadosPorUsuario(desarrollador.getId());
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado) {
		// TODO Auto-generated method stub
		return null;
	}
// --------------- PERIODO
	
	@Override
	public List<Periodo> listarPeriodos() {
		return (List<Periodo>) periodoRepo.findAll();
	}

	

	@Override
	public Proveedor_Reg_Horas buscarRegPorID(int id) {
		return regHorasRepo.findById(id).orElse(null);
	}

	@Override
	public Estado_Reg_Horas buscarEstadoPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HorasPorSemana> listarDiasPorSemana(int id_usuario) {
		//Variables
		Usuario u = usuarioRepo.findById(id_usuario).orElse(null);
		Estado_Reg_Horas e1 = estadoRepo.findById(1).orElse(null); //Estado 1: 1 = aprobado
		Estado_Reg_Horas e2 = estadoRepo.findById(2).orElse(null); //Estado 2: 2 = confirmado
		List<HorasPorSemana> lstHoras = regHorasRepo.horasSemanales(u,e1,e2);
		List<HorasPorSemana> listaFinal = new ArrayList<>();
		int j = 0; //Contador
		
		//Encontrar día de la semana
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		int dow = calendario.get(Calendar.DAY_OF_WEEK); 
		//Ultimo domingo de la semana actual:
		calendario.add(Calendar.DAY_OF_YEAR, 8- dow); 	

		//Formatos
		SimpleDateFormat formatoCorto = new SimpleDateFormat("dd");
		SimpleDateFormat formatoLargo = new SimpleDateFormat("dd-MM-yyyy");		
		String fechal1, fechal2, fechaCorta;

		//Llenar nuevo arreglo		
		for (int i = 0; i < 14; i++) {	
			HorasPorSemana hxdia = new HorasPorSemana();
			fechaCorta = formatoCorto.format(calendario.getTime());
			fechal1 = formatoLargo.format(calendario.getTime());
			
			if (lstHoras.size() > j)
				fechal2 = formatoLargo.format(lstHoras.get(j).getFecha());
			else 
				fechal2 = "";
			hxdia.setFecha(calendario.getTime());
			hxdia.setLeyenda(fechaCorta);
			if (fechal1.equals(fechal2)) {				
				hxdia.setTotal(lstHoras.get(j).getTotal());
				j++;
			}else {
				hxdia.setTotal(0);
			}
			listaFinal.add(hxdia);
			calendario.add(Calendar.DAY_OF_YEAR, -1);
		}
		return listaFinal;
	}

	@Override
	public double horasTrabajadas(String jira) {
		return regHorasRepo.horasTrabajadas(jira);
	}

	@Override
	public HJira buscarHXJiraXFab(String jira, String fabrica) {
		//PRIMERO: Verificar si existe en la base de datos
		HJira hxj = hxjRepo.findByJira(jira);
		if (hxj == null) {
			//Si NO existe, se creará un registro nuevo
			hxj = new HJira();		
		}
		// Ya sea nuevo registro o no, se actualizan todos los campos		
		List<JsoJira> respuestaAPI = jiraResRepo.busquedaJQL("key="+jira+"+and+fabrica="+fabrica +"&fields=key,summary,issuetype,customfield_14851,customfield_14850,customfield_11483,customfield_11640"); // Se consult al api
		if (respuestaAPI != null && respuestaAPI.size() == 1) {
			JsoJira j = respuestaAPI.get(0); 
			
			hxj.setJira(j.getKey());
			hxj.setDescripcion(j.getFields().getSummary());
			hxj.setTipo(j.getFields().getIssuetype().getName());
			hxj.setHoras_desarrollo(BigDecimal.valueOf(j.getFields().getCustomfield_14850()));
			hxj.setHoras_prueba(BigDecimal.valueOf(j.getFields().getCustomfield_14851()));
			//Fabrica
			Fabrica f = fabricaRepo.findByNombre(fabrica);
			hxj.setFabrica(f);
			//Indicador contable
			if (j.getFields().getCustomfield_11483() != null) {
				Indicador_Contable ic = indicadorRepo.findByIndicador(j.getFields().getCustomfield_11483().getValue());
				hxj.setIndicador(ic);
			}				
			//Empresa
			if (j.getFields().getCustomfield_11640() != null) {
				Empresa emp = empresaRepo.findByNombre(j.getFields().getCustomfield_11640().getValue());		
				hxj.setEmpresa(emp);
			}
			
			hxj = hxjRepo.save(hxj);
			
			return hxj;			
		}else {
			return null;
		}
	}
	

	@Override
	public List<Tipo_Actividad_Proveedor> listarTiposActividad() {
		return tipoActividadRepo.findAllByOrderById();
	}

	@Override
	public Tipo_Actividad_Proveedor buscarTipoActividadPorID(int id) {
		return tipoActividadRepo.findById(id).orElse(null);
	}

// TABLA DE REGISTROS DESARROLLADOR
	//primer registro
	@Override
	public String registrarHoras(Usuario u, RespGenerica respuesta) {

			Proveedor_Reg_Horas registro = new Proveedor_Reg_Horas();
			// USUARIO QUE REGISTRA
			registro.setUsuario(u);
			// ESTADO DE REGISTRO (CREADO)
			registro.setEstado(estadoRepo.findById(3).orElse(null)); //ESTADO INICIAL: 3=CREADO
			// FECHAS
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(respuesta.getTexto2());
				registro.setFecha_real_trabajo(date);
				registro.setFecha_facturacion(date);	// FECHA DE FACTURACION POR DEFECTO: FECHA DE TRABAJO REAL
			} catch (ParseException e) {
				e.printStackTrace();
				return "Error con la fecha!";
			}			
			registro.setFecha_registro(new Date());
			// JIRA HISTORICO
			registro.setHjira(hxjRepo.findByJira(respuesta.getTexto1()));
			// NRO HORAS
			registro.setNro_horas( BigDecimal.valueOf(respuesta.getNumero1()));
			// TIPO DE ACTIVIDAD (DESARROLLO Y MEJORA SON FACTURABLES)
			registro.setTipoActividad(tipoActividadRepo.findById((int) respuesta.getNumero2()).orElse(null));
			// COMENTARIO (OPCIONAL)
			registro.setComentario(respuesta.getTexto5());			
			HJira hxj = registro.getHjira();
			// NRO DE HORAS (TIPO 4:MEJORA Y 1:DESARROLLO SE RESTAN DEL TOTAL)
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1) {	// Validar tipos
				if((hxj.getHoras_desarrollo().subtract(hxj.getConsumido_desarrollo())).compareTo(registro.getNro_horas()) == 1) {
					registro.setFlagfacturar(true);
					registro = regHorasRepo.save(registro);
					
					//Las horas facturables se restan del pull de horas totales (DESARROLLO)
					hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo().add(registro.getNro_horas()));					
					hxjRepo.save(hxj);
					return registro.getId().toString();	
				}else {
					return "No quedan horas";
				}
			}else {
				registro.setFlagfacturar(false);
				registro = regHorasRepo.save(registro);
				return registro.getId().toString();	
			}
	}
	
	// CAMBIAR ESTADOS
	@Override
	public String cambiarEstadoRegistro(Proveedor_Reg_Horas registro, int id_estado) {
		registro.setEstado(estadoRepo.findById(id_estado).orElse(null));
		regHorasRepo.save(registro);	
		return "Confirmado";
	}
	
	//ELIMINAR
	@Override
	public String eliminarHoras(Usuario u, RespGenerica respuesta) {		
		Proveedor_Reg_Horas registro = regHorasRepo.findById((int)respuesta.getNumero1()).orElse(null);		
		if (registro.getUsuario() == u) {
			regHorasRepo.deleteById((int)respuesta.getNumero1());
			HJira hxj = hxjRepo.findByJira(registro.getHjira().getJira());
			// Si las horas son desarrollo o mejora, se restan de las horas CONSUMIDAS 
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1) { //4- Mejora | 1- Desarrollo 
				hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo().subtract(registro.getNro_horas()));
				hxjRepo.save(hxj);
			}
			return "Eliminado";			
		}else {
			return "Error, no puede eliminar registros de otras personas";
		}

	}	
}
