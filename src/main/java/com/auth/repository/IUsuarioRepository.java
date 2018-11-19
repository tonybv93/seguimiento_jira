package com.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.entity.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {
	public Usuario findByUsername(String username);
	
	@Query(value ="INSERT INTO JIRABD(PASSWORD, ENABLE, USERNAME) VALUES (pass , flag , us)",nativeQuery = true)
	public Usuario registrar(@Param("insertLink") String us,@Param("insertLink") String pass,@Param("insertLink") int flag,@Param("insertLink") int id);
	
	
}
