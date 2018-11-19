package com.auth.service;

import java.util.List;

import com.auth.entity.Jira_Detalle;

public interface IJiraDetalleService {
	public Jira_Detalle guardarNuevo(Jira_Detalle jbdd);
	public List<Jira_Detalle> listarTodos();
	public Jira_Detalle buscarPorId(Integer id);
	public Jira_Detalle buscarPorJira(String jira);
}
