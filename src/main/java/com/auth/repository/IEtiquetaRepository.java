package com.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.auth.entity.Etiqueta;

public interface IEtiquetaRepository extends CrudRepository<Etiqueta, Integer> {
	Etiqueta findByNombre(String nombre);
}
