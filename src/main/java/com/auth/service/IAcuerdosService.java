package com.auth.service;

import java.util.List;

import com.auth.entity.Acuerdos;
import com.auth.entity.Area_Solicitante;
import com.auth.entity.Estado_Acuerdo;
import com.auth.entity.Tipo_Acuerdo;
import com.auth.entity.Usuario;

public interface IAcuerdosService {
	public Acuerdos guardar(Acuerdos ac);
	public List<Acuerdos> listarTodos();
	public List<Acuerdos> listarActivos();
	public Acuerdos buscarPorId(Integer id);
	public void eliminar(Integer id);
	public Acuerdos actualizar(Acuerdos ac);
	
	public List<Area_Solicitante> listarAreaSolicitante();
	public Area_Solicitante buscarAreaPorId(int id);
	
	public List<Usuario> listarUsuarios();
	public Usuario buscarUsuarioPorId(int id);
	
	public List<Tipo_Acuerdo> listarTiposAcuerdo();
	public Tipo_Acuerdo buscarTipoAcuerdoPorId(int id);
	
	public Estado_Acuerdo buscarEstadoPorId(int id);
}
