package com.auth.service;

import java.util.List;

import com.auth.entity.Acuerdos;

public interface IAcuerdosService {
	public Acuerdos guardar(Acuerdos ac);
	public List<Acuerdos> listarTodos();
	public List<Acuerdos> listarActivos();
	public Acuerdos buscarPorId(Integer id);
	public void eliminar(Integer id);
	Acuerdos actualizar(Acuerdos ac);
}
