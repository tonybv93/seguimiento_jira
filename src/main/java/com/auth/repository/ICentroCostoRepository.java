package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Centro_Costo;

@Repository
public interface ICentroCostoRepository extends CrudRepository<Centro_Costo, Integer>{
	public Centro_Costo findByCcjira(String ccjira);

}
