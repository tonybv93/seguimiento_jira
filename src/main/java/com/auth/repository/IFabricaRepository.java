package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Fabrica;

@Repository
public interface IFabricaRepository extends CrudRepository<Fabrica, Fabrica>{
	Fabrica findByNombre(String nombre);
}
