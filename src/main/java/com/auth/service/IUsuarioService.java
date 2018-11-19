package com.auth.service;

import com.auth.entity.Usuario;

public interface IUsuarioService {
	public Usuario buscarPorUsername(String username);	 
	public void guardarUsuario(Usuario user);
}
