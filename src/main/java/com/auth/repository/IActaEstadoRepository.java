package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Acta_Estado;

@Repository
public interface IActaEstadoRepository extends CrudRepository<Acta_Estado, Integer>{
	
}
