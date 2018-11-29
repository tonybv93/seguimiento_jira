package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Acuerdos;
@Repository
public interface IAcuerdosRepository extends CrudRepository<Acuerdos, Integer> {
	public List<Acuerdos> findAll();
	public List<Acuerdos> findAllByOrderByIdDesc();
	public List<Acuerdos> findAllByFlagterminado(Integer i);
}
