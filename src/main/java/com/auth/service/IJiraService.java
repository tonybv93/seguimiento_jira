package com.auth.service;

import java.util.List;

import com.auth.entity.HJira;
import com.auth.entity.Jira;

public interface IJiraService {
	
	
	public List<Jira> listarJiras();		//LISTAR TODOS
	public int[] jirasNoAtendidosPorArea();
	public int[] jirasAtendidosPorArea();
	public List<Jira> listarJirasPorEmpresa(Integer id);		//LISTAR TODOS por empresa	
	public Jira buscarPorJira(String jira);	//BUSCAR CODIGO JIRA
	public Jira buscarPorId(Integer id);	//BUSCAR 1 JIRA
	public Jira guardar(Jira j);			//ACTUALIZAR FECHAS
	
	public void actualizarBD();
	
	public List<HJira> BuscadorPersonalizado(String str);
	
	public List<HJira> buscarJiraPorFabrica(String str, String fabrica);
	
}
