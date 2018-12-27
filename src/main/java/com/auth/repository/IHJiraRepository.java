package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.HJira;

@Repository
public interface IHJiraRepository extends CrudRepository<HJira, Integer>{
	public HJira findByJira(String jira);
}
