package com.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Acta_Comentario;

@Repository
public interface IComentarioRepository extends CrudRepository<Acta_Comentario, Integer>{

}
