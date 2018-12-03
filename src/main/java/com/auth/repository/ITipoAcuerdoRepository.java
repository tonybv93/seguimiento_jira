package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Tipo_Acuerdo;

@Repository
public interface ITipoAcuerdoRepository extends CrudRepository<Tipo_Acuerdo, Integer> {
	Tipo_Acuerdo findByNombre(String nombre); 
}
