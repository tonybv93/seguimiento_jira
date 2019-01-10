package com.auth.repository;

import java.util.List;

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
	
	@Query(value="SELECT u.ID, u.PASSWORD, u.ENABLE, u.USERNAME,u.DESCRIPCION,u.ID_FABRICA, u.ID_EMPRESA FROM BVLSEGDB.USUARIO u RIGHT JOIN BVLSEGDB.USUARIO_ROL ur ON u.id = ur.ID_USER WHERE ur.ID_ROL = ?1",nativeQuery = true)
	public List<Usuario> usuariosPorRol(int id_rol);
	
	@Query(value="SELECT u.ID, u.PASSWORD, u.ENABLE, u.USERNAME,u.DESCRIPCION,u.ID_FABRICA, u.ID_EMPRESA FROM BVLSEGDB.USUARIO u RIGHT JOIN BVLSEGDB.USUARIO_ROL ur ON u.id = ur.ID_USER WHERE ur.ID_ROL = ?1 and u.ID_FABRICA = ?2",nativeQuery = true)
	public List<Usuario> usuariosPorRolyFabrica(int id_rol, int id_dabrica);
}
