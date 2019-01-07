package com.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Horas_Gestion_Demanda;
@Repository
public interface IHorasGestionDemandaRepository extends CrudRepository<Horas_Gestion_Demanda, Integer>{
	@Query(value ="(SELECT * FROM BVLSEGDB.HORAS_GESTION_DEMANDA WHERE ID_USUARIO = ?1 AND ID_PERIODO = ?2)",nativeQuery = true)
	public Horas_Gestion_Demanda buscarPorUsuarioYPeriodo(int id_usuario,int id_periodo);
}
