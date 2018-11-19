package com.auth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Acuerdos;
import com.auth.entity.Jira;
import com.auth.repository.IAcuerdosRepository;
import com.auth.repository.IJiraRepository;
@Service
public class AcuerdosService implements IAcuerdosService {
	
	@Autowired
	IAcuerdosRepository acuerdoRepo;
	@Autowired
	IJiraRepository jiraRepo;
	
	@Override
	public Acuerdos guardar(Acuerdos ac) {
		Jira jira = jiraRepo.findByJira(ac.getJira());		
		if (jira != null) {
			ac.setJiraDesc(jira.getResumen());
			//ac.setArea(jira.getAreaSolicitante());			
		}		
		//Los otros estados serán "en proceso" y "terminado"		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = ac.getFecha_entrega();
		try {
			Date date = formatter.parse(fecha);
			ac.setFecha_entrega(new SimpleDateFormat("dd-MM-yyyy").format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ac.setFecha_registro(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		ac.setEstado("En proceso");
		ac.setTerminado(0);
		ac.setClase("est_aprob");
		
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

}
	