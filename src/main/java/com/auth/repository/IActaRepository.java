package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.auxiliar.DetalleActaPre;
import com.auth.entity.Acta;
import com.auth.entity.Empresa;
import com.auth.entity.Fabrica;
import com.auth.entity.Indicador_Contable;

@Repository
public interface IActaRepository extends CrudRepository<Acta, Integer> {
	@Query(value="SELECT NEW com.auth.auxiliar.DetalleActaPre(pr.hjira.jira, pr.hjira.indicador.indicador, pr.hjira.descripcion,  SUM(pr.nro_horas)) "+
			" FROM Proveedor_Reg_Horas pr" + 
			" WHERE pr.fecha_real_trabajo > to_date(?1)"+
			" AND pr.fecha_real_trabajo < to_date(?2)"+
			" AND pr.usuario.fabrica = ?3"+
			" AND pr.estado.id = 1"+
			" AND pr.flagfacturar = true"+
			" AND pr.hjira.indicador = ?4"+
			" AND pr.hjira.empresa = ?5"+
			" GROUP BY pr.hjira.jira, pr.hjira.indicador.indicador, pr.hjira.descripcion"
			,nativeQuery= false)
	public List<DetalleActaPre> listarDetalleActaPre(String fecha1, String fecha2, Fabrica fab, Indicador_Contable indicador, Empresa e);	
	
	public Acta findByCodigo(String codigo);
	
}
