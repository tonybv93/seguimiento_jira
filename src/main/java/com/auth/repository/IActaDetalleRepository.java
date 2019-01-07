package com.auth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.Acta;
import com.auth.entity.Acta_detalle;
@Repository
public interface IActaDetalleRepository extends CrudRepository<Acta_detalle, Integer>{
	List<Acta_detalle> findAllByActa(Acta acta);
}
