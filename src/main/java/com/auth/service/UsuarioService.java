package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.entity.Rol;
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

	@Override
	public Rol buscarRolPorId(int id) {
		// TODO Auto-generated method stub
		return rolRepo.findById(id).orElse(null);
	}

	@Override
	public boolean cambiarPass(String pas_antigua, String pas_nueva, Usuario u) {
		if (encriptador.matches(pas_antigua, u.getPassword())) {
			u.setPassword(encriptador.encode(pas_nueva));
			userRepo.save(u);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Usuario> listarUsuarioPorRolYEmpresa(int id_rol, int id_empresa) {
		return userRepo.usuariosPorRolyFabrica(id_rol,id_empresa);
	}

}
