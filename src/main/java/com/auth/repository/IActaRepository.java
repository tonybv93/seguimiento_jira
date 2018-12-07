package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Acta;

@Repository
public interface IActaRepository extends CrudRepository<Acta, Integer> {
	
}
