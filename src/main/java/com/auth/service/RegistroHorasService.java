package com.auth.service;

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
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Horas_X_Jira;
import com.auth.entity.JsoJira;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Tipo_Actividad_Proveedor;
import com.auth.entity.Usuario;
import com.auth.repository.IEstadoRegHorasRepository;
import com.auth.repository.IHorasXJiraRepository;
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
	IHorasXJiraRepository hxjRepo;
	@Autowired
	JiraApiRepository jiraResRepo;
	@Autowired
	ITipoActividadProveedor tipoActividadRepo;
	
	
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
	public Horas_X_Jira buscarHXJira(String jira) {
		//PRIMERO: Verificar si existe en la base de datos
		Horas_X_Jira hxj = hxjRepo.findByJira(jira);
		if (hxj == null) {
			//Si NO existe, se creará un registro nuevo
			hxj = new Horas_X_Jira();			
			JsoJira j = jiraResRepo.busquedaJQL("key="+jira).get(0); // Se consult al api			
			if (j != null) {
				hxj.setJira(j.getKey());
				hxj.setDescripcion(j.getFields().getSummary());
				hxj.setTipo(j.getFields().getIssuetype().getName());
				hxj.setHoras_desarrollo(j.getFields().getCustomfield_14851());
				hxj.setHoras_prueba(j.getFields().getCustomfield_14850());
				hxj = hxjRepo.save(hxj);
				return hxj;				
			}else {
				return null;
			}
		}else {
			//Si SÍ existe, se retorne el registro
			return hxj;
		}
	}

	

	@Override
	public List<Tipo_Actividad_Proveedor> listarTiposActividad() {
		return (List<Tipo_Actividad_Proveedor>) tipoActividadRepo.findAll();
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
			registro.setUsuario(u);
			registro.setEstado(estadoRepo.findById(3).orElse(null)); 
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(respuesta.getTexto2());
				registro.setFecha_real_trabajo(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return "Error con la fecha!";
			}			
			registro.setFecha_registro(new Date());
			registro.setJira(respuesta.getTexto1());
			registro.setNro_horas(respuesta.getNumero1());
			registro.setTipoActividad(tipoActividadRepo.findById((int) respuesta.getNumero2()).orElse(null));
			registro.setComentario(respuesta.getTexto5());
			registro.setTipojira(respuesta.getTexto3());
			registro.setResumen(respuesta.getTexto4());
			
			Horas_X_Jira hxj = hxjRepo.findByJira(registro.getJira());
			
			// Si las horas son desarrollo o mejora, se SUMAN de las horas CONSUMIDAS
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1) {
				if((hxj.getHoras_desarrollo() - hxj.getConsumido_desarrollo()) > registro.getNro_horas()) {
					registro = regHorasRepo.save(registro);
					hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo() + registro.getNro_horas());
					hxjRepo.save(hxj);
					return registro.getId().toString();	
				}else {
					return "No quedan horas";
				}
			}else {
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
			Horas_X_Jira hxj = hxjRepo.findByJira(registro.getJira());
			// Si las horas son desarrollo o mejora, se restan de las horas CONSUMIDAS 
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1) { //4- Mejora | 1- Desarrollo 
				hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo() - registro.getNro_horas());
				hxjRepo.save(hxj);
			}
			return "Eliminado";			
		}else {
			return "Error, no puede eliminar registros de otras personas";
		}

	}	
}
