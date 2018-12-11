package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Desarrollador;
import com.auth.entity.Usuario;

@Repository
public interface IDesarrolladorRepository extends CrudRepository<Desarrollador, Integer>{
	public Desarrollador findByUsuario(Usuario u);
}
