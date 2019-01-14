package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Fabrica;
import com.auth.entity.HJira;

@Repository
public interface IHJiraRepository extends CrudRepository<HJira, Integer>{
	public HJira findByJira(String jira);
	public List<HJira> findAllByFabrica(Fabrica f);
}
