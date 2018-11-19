package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Tipo_Requerimiento;

@Repository
public interface ITipoRequerimientoRepository extends CrudRepository<Tipo_Requerimiento, Integer>{
	Tipo_Requerimiento findByNombre(String nombre);
}
