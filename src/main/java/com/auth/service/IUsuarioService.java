package com.auth.service;

import java.util.List;

import com.auth.entity.Usuario;

public interface IUsuarioService {
	public Usuario buscarPorUsername(String username);	 
	public Usuario buscarPorId(int id);
	public List<Usuario> listarUsuarios();
	public void guardarUsuario(Usuario user);
}
