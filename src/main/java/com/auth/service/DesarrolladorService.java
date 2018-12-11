package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Desarrollador;
import com.auth.entity.Usuario;
import com.auth.repository.IDesarrolladorRepository;
@Service
public class DesarrolladorService implements IDesarrolladorService {
	@Autowired
	IDesarrolladorRepository desarrolladorRepo;
	@Override
	public Desarrollador buscarPorID(int id) {
		return desarrolladorRepo.findById(id).orElse(null);
	}

	@Override
	public List<Desarrollador> listarTodos() {
		return (List<Desarrollador>) desarrolladorRepo.findAll();
	}

	@Override
	public Desarrollador buscarPorUsuario(Usuario u) {
		return desarrolladorRepo.findByUsuario(u);
	}

}
