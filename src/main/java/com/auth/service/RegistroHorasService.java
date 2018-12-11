package com.auth.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Desarrollador;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.repository.IDesarrolladorRepository;
import com.auth.repository.IEstadoRegHorasRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.repository.IProveedorRegHorasRepository;
import com.auth.repository.IUsuarioRepository;
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
			registro = regHorasRepo.save(registro);
			
		return registro.getId().toString();		
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
	public long horasSemanalesPorUsuario(int id, String fecha) {
		return regHorasRepo.horasPorDia(id,fecha);
	}
	
	private String[] encontrarSemana(String fecha) {
		String[] arregloFechas =  new String[13];
		for (int i = 0; i < 14; i++) {
			arregloFechas[i] = "a";
		}
		return arregloFechas;
	}
}
