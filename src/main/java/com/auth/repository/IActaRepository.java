package com.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.auxiliar.DetalleActaPre;
import com.auth.entity.Acta;
import com.auth.entity.Acta_Estado;
import com.auth.entity.Empresa;
import com.auth.entity.Fabrica;
import com.auth.entity.Indicador_Contable;

@Repository
public interface IActaRepository extends CrudRepository<Acta, Integer> {
	@Query(value="SELECT NEW com.auth.auxiliar.DetalleActaPre(pr.hjira,  SUM(pr.nro_horas), SUM(pr.nro_horas_gestion)) "+
			" FROM Proveedor_Reg_Horas pr" + 
			" WHERE pr.fecha_real_trabajo >= to_date(?1)"+
			" AND pr.fecha_real_trabajo <= to_date(?2)"+
			" AND pr.usuario.fabrica = ?3"+
			" AND pr.estado.id = 1"+
			" AND pr.flagfacturar = true"+
			" AND pr.hjira.indicador = ?4"+
			" AND pr.hjira.empresa = ?5"+
			" AND pr.flag_acta = false"+
			" GROUP BY pr.hjira"
			,nativeQuery= false)
	public List<DetalleActaPre> listarDetalleActaPre(String fecha1, String fecha2, Fabrica fab, Indicador_Contable indicador, Empresa e);	
	
	
	public Acta findByCodigo(String codigo);
	public List<Acta> findAllByEstado(Acta_Estado estado);
	public List<Acta> findAllByFabrica(Fabrica fabrica);
	public List<Acta> findAllByEmpresa(Empresa empresa);	
	
	@Query(value="SELECT * FROM BVLSEGDB.ACTA WHERE ID_FABRICA = ?1 AND ID_PERIODO = ?2 AND ID_EMPRESA = ?3 AND ID_INDICADOR_CONTABLE = ?4",nativeQuery=true)
	public Acta verificarSiExistePrevia(int id_fab, int id_periodo,int id_empresa,int id_indicador);
}
