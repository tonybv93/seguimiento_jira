package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Horas_X_Jira;

@Repository
public interface IHorasXJiraRepository extends CrudRepository<Horas_X_Jira, Integer>{
	public Horas_X_Jira findByJira(String jira);
}
