package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Jira_Detalle;
import com.auth.repository.IJiraDetalleRepository;

@Service
public class JiraBDDetalleService implements IJiraDetalleService {
	@Autowired
	IJiraDetalleRepository repo;
	
	@Override
	public Jira_Detalle guardarNuevo(Jira_Detalle jbdd) {

		return repo.save(jbdd);
	}

	@Override
	public List<Jira_Detalle> listarTodos() {
		return repo.findAll();
	}

	@Override
	public Jira_Detalle buscarPorId(Integer id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Jira_Detalle buscarPorJira(String jira) {
		return repo.findByJira(jira);
	}

}
