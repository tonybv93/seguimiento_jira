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
import com.auth.entity.Desarrollador;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Horas_X_Jira;
import com.auth.entity.JsoJira;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.repository.IDesarrolladorRepository;
import com.auth.repository.IEstadoRegHorasRepository;
import com.auth.repository.IHorasXJiraRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.repository.IProveedorRegHorasRepository;
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
	IDesarrolladorRepository desarrolladorRepo;
	@Autowired
	IEstadoRegHorasRepository estadoRepo;
	@Autowired
	IHorasXJiraRepository hxjRepo;
	@Autowired
	JiraApiRepository jiraResRepo;
	
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
	public List<Proveedor_Reg_Horas> listarRegistrosEnviadosPorDesarrollador(Desarrollador desarrollador) {
		return regHorasRepo.listarEnviadoPorUsuario(desarrollador.getId());
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
	public String registrarHoras(Usuario u, RespGenerica respuesta) {
		Desarrollador desarrollador = desarrolladorRepo.findByUsuario(u);	

			Proveedor_Reg_Horas registro = new Proveedor_Reg_Horas();
			registro.setDesarrollador(desarrollador);
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
			registro.setTipo(respuesta.getTexto3());
			registro.setResumen(respuesta.getTexto4());
			
			Horas_X_Jira hxj = hxjRepo.findByJira(registro.getJira());
			if((hxj.getHoras_desarrollo() - hxj.getConsumido_desarrollo()) > registro.getNro_horas()) {
				hxj.setConsumido_desarrollo(registro.getNro_horas());
				registro = regHorasRepo.save(registro);
				hxjRepo.save(hxj);
				return registro.getId().toString();	
			}else {
				return "No quedan horas";
			}	
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
	public String confirmarRegistro(Proveedor_Reg_Horas registro) {
		registro.setEstado(estadoRepo.findById(2).orElse(null));
		regHorasRepo.save(registro);
		return "Confirmado";
	}

	@Override
	public List<HorasPorSemana> listarDiasPorSemana(int id) {
		//Variables
		Desarrollador d = desarrolladorRepo.findById(id).orElse(null);
		List<HorasPorSemana> lstHoras = regHorasRepo.horasSemanales(d);
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
	public long horasTrabajadas(String jira) {
		return regHorasRepo.horasTrabajadas(jira);
	}

	@Override
	public Horas_X_Jira buscarHXJira(String jira) {
		Horas_X_Jira hxj = hxjRepo.findByJira(jira);
		if (hxj == null) {
			hxj = new Horas_X_Jira();
			JsoJira j = jiraResRepo.busquedaJQL("key="+jira).get(0);
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
			return hxj;
		}
	}

	@Override
	public String eliminarHoras(Usuario u, RespGenerica respuesta) {
		
		Proveedor_Reg_Horas registro = regHorasRepo.findById((int)respuesta.getNumero1()).orElse(null);
		
		if (registro.getDesarrollador().getUsuario() == u) {
			regHorasRepo.deleteById((int)respuesta.getNumero1()); 
			Horas_X_Jira hxj = hxjRepo.findByJira(registro.getJira());
			hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo() - registro.getNro_horas());
			hxjRepo.save(hxj);
			return "Eliminado";			
		}else {
			return "Error, no puede eliminar registros de otras personas";
		}

	}
	
}
