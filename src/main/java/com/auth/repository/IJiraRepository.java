package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Area_Solicitante;
import com.auth.entity.Empresa;
import com.auth.entity.Jira;

@Repository
public interface IJiraRepository extends CrudRepository<Jira, Integer> {
	public List<Jira> findAllByOrderByEtiqueta();
	public List<Jira> findAllByEmpresaOrderByEtiqueta(Empresa emp);
	public List<Jira> findAllByAreaSolicitante(Area_Solicitante area);
	public Jira findByJira(String j);
	public long count();
	
	@Query(value ="(SELECT COUNT(*) FROM BVLSEGDB.JIRA WHERE ID_AREA_SOLICITANTE = ?1 and id_empresa=1)",nativeQuery = true)
	public long contar_por_area(int id);	
}
