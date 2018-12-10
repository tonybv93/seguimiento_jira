package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Proveedor_Reg_Horas;

@Repository
public interface IProveedorRegHorasRepository extends CrudRepository<Proveedor_Reg_Horas, Integer> {
	
}
