package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Periodo;

@Repository
public interface IPeriodoRepository extends CrudRepository<Periodo, Integer>{

}
