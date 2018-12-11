package com.auth.service;

import java.util.List;

import com.auth.entity.Desarrollador;
import com.auth.entity.Usuario;

public interface IDesarrolladorService {
	public Desarrollador buscarPorID(int id);
	public List<Desarrollador> listarTodos();
	public Desarrollador buscarPorUsuario(Usuario u);
}
