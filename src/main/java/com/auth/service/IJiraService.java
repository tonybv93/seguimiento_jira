package com.auth.service;

import java.util.List;

import com.auth.entity.Jira;

public interface IJiraService {
	
	
	public List<Jira> listarJiras();		//LISTAR TODOS
	public Jira buscarPorJira(String jira);	//BUSCAR CODIGO JIRA
	public Jira buscarPorId(Integer id);	//BUSCAR 1 JIRA
	public Jira guardar(Jira j);			//ACTUALIZAR FECHAS
	
	public void actualizarBD();
}
