package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Indicador_Contable;

@Repository
public interface IIndicadorContableRepository extends CrudRepository<Indicador_Contable, Integer>{
	Indicador_Contable findByIndicador(String indicador);
}
