package com.auth.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Acuerdos;
import com.auth.entity.Area_Solicitante;
import com.auth.entity.Estado_Acuerdo;
import com.auth.entity.Jira;
import com.auth.entity.Tipo_Acuerdo;
import com.auth.entity.Usuario;
import com.auth.repository.IAcuerdosRepository;
import com.auth.repository.IAreaSolicitanteRepository;
import com.auth.repository.IEstadoAcuerdoRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.ITipoAcuerdoRepository;
import com.auth.repository.IUsuarioRepository;
@Service
public class AcuerdosService implements IAcuerdosService {
	
	@Autowired
	IAcuerdosRepository acuerdoRepo;
	@Autowired
	IJiraRepository jiraRepo;
	@Autowired
	IAreaSolicitanteRepository areaRepo;
	@Autowired
	IUsuarioRepository usuarioRepo;
	@Autowired
	ITipoAcuerdoRepository tipoRepo;
	@Autowired
	IEstadoAcuerdoRepository estadoAcuerdoRepo;
	
	
	@Override
	public Acuerdos guardar(Acuerdos ac) {
		Jira jira = jiraRepo.findByJira(ac.getId_jira());		
		if (jira != null) {
			//ac.set(jira.getResumen());
			ac.setAreaSolicitante(jira.getAreaSolicitante());			
		}				
		ac.setFecha_creacion(new Date());		
		ac.setFlagterminado(false);	
		return acuerdoRepo.save(ac);
	}
	@Override
	public Acuerdos actualizar(Acuerdos ac) {
		return acuerdoRepo.save(ac);
	}
	@Override
	public List<Acuerdos> listarTodos() {
		return acuerdoRepo.findAllByOrderByIdDesc();
	}

	@Override
	public Acuerdos buscarPorId(Integer id) {
		return acuerdoRepo.findById(id).orElse(null);
	}
	@Override
	public void eliminar(Integer id) {
		acuerdoRepo.deleteById(id);
		
	}
	@Override
	public List<Acuerdos> listarActivos() {
		return acuerdoRepo.findAll();
	}
	
//---------- AREA REPO ---------------------
	@Override
	public List<Area_Solicitante> listarAreaSolicitante() {
		return areaRepo.findAll();
	}
	@Override
	public Area_Solicitante buscarAreaPorId(int id) {
		return areaRepo.findById(id).orElse(null);
	}
//---------- USUARIO REPO ---------------------
	@Override
	public List<Usuario> listarUsuarios() {
		return (List<Usuario>) usuarioRepo.findAll();
	}
	@Override
	public Usuario buscarUsuarioPorId(int id) {
		return usuarioRepo.findById(id).orElse(null);
	}
//----------- TIPO DE ACUERDO -------------------
	@Override
	public List<Tipo_Acuerdo> listarTiposAcuerdo() {
		return (List<Tipo_Acuerdo>) tipoRepo.findAll();
	}
	
	@Override
	public Tipo_Acuerdo buscarTipoAcuerdoPorId(int id) {
		return tipoRepo.findById(id).orElse(null);
	}
//----------- TIPO DE ACUERDO -------------------	
	@Override
	public Estado_Acuerdo buscarEstadoPorId(int id) {
		return estadoAcuerdoRepo.findById(id).orElse(null);
	}
}
	