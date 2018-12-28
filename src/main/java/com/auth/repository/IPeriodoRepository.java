package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Periodo;

@Repository
public interface IPeriodoRepository extends CrudRepository<Periodo, Integer>{
	public Periodo findByCodigo(String codigo);
	public Periodo findByPeriodo(String periodo);

}
