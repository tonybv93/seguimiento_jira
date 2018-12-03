package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Empresa;
import com.auth.entity.Jira;

@Repository
public interface IJiraRepository extends CrudRepository<Jira, Integer> {
	public List<Jira> findAllByOrderByEtiqueta();
	public List<Jira> findAllByEmpresaOrderByEtiqueta(Empresa emp);
	public Jira findByJira(String j);
}
