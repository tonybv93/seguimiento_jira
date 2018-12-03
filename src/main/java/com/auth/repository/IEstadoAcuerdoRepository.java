package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Estado_Acuerdo;

@Repository
public interface IEstadoAcuerdoRepository extends CrudRepository<Estado_Acuerdo, Integer> {
	
}
