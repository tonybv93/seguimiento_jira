package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Area_Solicitante;

@Repository
public interface IAreaSolicitanteRepository extends CrudRepository<Area_Solicitante, Integer>{
	Area_Solicitante findByNombrecorto(String nombre);
	
	public List<Area_Solicitante> findAll();
}
