package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Menu;

@Repository
public interface IMenuRepository extends CrudRepository<Menu, Integer> {
	public List<Menu> findAllByNivel(int niv);
	
	@Query(value ="(SELECT A.ID, A.TEXTO, A.NIVEL, A.RAIZ, A.URL FROM BVLSEGDB.MENU A INNER JOIN BVLSEGDB.MENU_ROL MR ON A.ID = MR.ID_MENU WHERE MR.ID_ROL = ?1) order by orden ASC",nativeQuery = true)
	public List<Menu> findMenusPorRol(int id_rol);	
	
	@Query(value ="(SELECT * FROM BVLSEGDB.MENU A WHERE NIVEL = 2 AND a.RAIZ = ?1)",nativeQuery = true)
	public List<Menu> findSubMenusPorMenu(int id_menu);
	
}
