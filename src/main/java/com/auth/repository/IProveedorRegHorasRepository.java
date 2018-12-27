package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;

@Repository
public interface IProveedorRegHorasRepository extends CrudRepository<Proveedor_Reg_Horas, Integer> {
	
	public List<Proveedor_Reg_Horas> findAllByUsuario(Usuario u);
	public List<Proveedor_Reg_Horas> findAllByEstado(Estado_Reg_Horas e);
	
	@Query(value ="(SELECT * FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE ID_USUARIO = ?1 AND ID_ESTADO_REG_HORAS in (3,4) )",nativeQuery = true)
	public List<Proveedor_Reg_Horas> listarEnviadoPorUsuario(int id);	
	
	@Query(value ="(SELECT * FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE ID_USUARIO = ?1 AND ID_ESTADO_REG_HORAS = 2)",nativeQuery = true)
	public List<Proveedor_Reg_Horas> listarConfirmadosPorUsuario(int id);
	
	@Query(value ="(SELECT * FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE ID_USUARIO = ?1 AND ID_ESTADO_REG_HORAS = 1)",nativeQuery = true)
	public List<Proveedor_Reg_Horas> listarAprobadosPorUsuario(int id);
	
	
	
	@Query(value="SELECT NEW com.auth.auxiliar.HorasPorSemana(pr.fecha_real_trabajo, sum(pr.nro_horas)) "+
				" FROM Proveedor_Reg_Horas pr" + 
				" WHERE pr.usuario = ?1"+
				" AND (pr.estado = ?2 or pr.estado = ?3)"+
				" GROUP BY pr.fecha_real_trabajo" +
				" ORDER BY pr.fecha_real_trabajo DESC"
				,nativeQuery= false)
	public List<HorasPorSemana> horasSemanales(Usuario usuario, Estado_Reg_Horas e1, Estado_Reg_Horas e2);
	
	@Query(value="SELECT sum(NRO_HORAS) FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE jira = ?1", nativeQuery = true)
	public long horasTrabajadas(String jira);
	
}
