package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Desarrollador;
import com.auth.entity.Proveedor_Reg_Horas;

@Repository
public interface IProveedorRegHorasRepository extends CrudRepository<Proveedor_Reg_Horas, Integer> {
	public List<Proveedor_Reg_Horas> findAllByDesarrollador(Desarrollador d);
	
	@Query(value ="(SELECT * FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE ID_DESARROLLADOR = ?1 AND ID_ESTADO_REG_HORAS = 3)",nativeQuery = true)
	public List<Proveedor_Reg_Horas> listarEnviadoPorUsuario(int id);	
	
	@Query(value ="(SELECT SUM(NRO_HORAS) FROM BVLSEGDB.PROVEEDOR_REG_HORAS WHERE ID_DESARROLLADOR = ?1 AND ID_ESTADO_REG_HORAS != 1  AND TO_CHAR(FECHA_REAL_TRABAJO,'dd-mm-yyyy') = ?2 GROUP BY FECHA_REAL_TRABAJO)",nativeQuery = true)
	public long horasPorDia(int id,String fecha);	
}
