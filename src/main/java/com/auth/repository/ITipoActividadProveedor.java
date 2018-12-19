package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Tipo_Actividad_Proveedor;

@Repository
public interface ITipoActividadProveedor extends CrudRepository<Tipo_Actividad_Proveedor, Integer>{
	
}
