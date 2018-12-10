package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Estado_Reg_Horas;

@Repository
public interface IEstadoRegHorasRepository extends CrudRepository<Estado_Reg_Horas, Integer>{

}
