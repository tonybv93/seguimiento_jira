package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Estado_Jira;

@Repository
public interface IEstadoJiraRepository extends CrudRepository<Estado_Jira, Integer>{
	Estado_Jira findByEstado(String estado);
}
