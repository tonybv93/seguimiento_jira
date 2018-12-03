package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Empresa;

@Repository
public interface IEmpresaRepository extends CrudRepository<Empresa, Integer>{
	Empresa findByNombre(String nombre);
}
