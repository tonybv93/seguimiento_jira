package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Rol;
@Repository
public interface IRolRepository extends CrudRepository<Rol, Integer>{
	public Rol findByRol(String rol);
}
