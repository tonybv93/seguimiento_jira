package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.entity.Usuario;
import com.auth.repository.IRolRepository;
import com.auth.repository.IUsuarioRepository;
@Service
public class UsuarioService implements IUsuarioService {
	@Autowired
	IRolRepository rolRepo;
	@Autowired
	IUsuarioRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder encriptador;
	
	
	@Override
	public Usuario buscarPorUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public void guardarUsuario(Usuario user) {
		
		user.setPassword(encriptador.encode(user.getPassword()));
		userRepo.save(user);
	}

	@Override
	public Usuario buscarPorId(int id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return (List<Usuario>) userRepo.findAll();
	}

	@Override
	public List<Usuario> listarUsuarioPorRol(int id_rol) {
		return userRepo.usuariosPorRol(id_rol);
	}

}
