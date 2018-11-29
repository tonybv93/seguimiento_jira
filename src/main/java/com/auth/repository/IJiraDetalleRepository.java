package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Jira_Detalle;

@Repository
public interface IJiraDetalleRepository extends CrudRepository<Jira_Detalle, Integer>{
	public List<Jira_Detalle> findAll();
	public Jira_Detalle findByJira(String jira);
}
