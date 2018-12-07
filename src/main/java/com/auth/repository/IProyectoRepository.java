package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Proyecto;

@Repository
public interface IProyectoRepository extends CrudRepository<Proyecto, Integer>{
	
}
