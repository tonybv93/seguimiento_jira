package com.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auth.entity.Jira_Detalle;
@Service
public interface IJiraDetalleService {
	public Jira_Detalle guardarNuevo(Jira_Detalle jbdd);
	public List<Jira_Detalle> listarTodos();
	public Jira_Detalle buscarPorId(Integer id);
}
