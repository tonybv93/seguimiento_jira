package com.auth.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auxiliar.DetalleActaPre;
import com.auth.entity.Acta;
import com.auth.entity.Acta_Estado;
import com.auth.entity.Acta_detalle;
import com.auth.entity.Empresa;
import com.auth.entity.Fabrica;
import com.auth.entity.HJira;
import com.auth.entity.Indicador_Contable;
import com.auth.entity.Periodo;
import com.auth.entity.Usuario;
import com.auth.repository.IActaDetalleRepository;
import com.auth.repository.IActaRepository;
import com.auth.repository.IEmpresaRepository;
import com.auth.repository.IEstadoActaRepository;
import com.auth.repository.IFabricaRepository;
import com.auth.repository.IHJiraRepository;
import com.auth.repository.IHorasGestionDemandaRepository;
import com.auth.repository.IIndicadorContableRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.rest.RespGenerica;

@Service
public class ActaService implements IActaService {

	@Autowired
	IPeriodoRepository periodoRepo;
	@Autowired
	IEmpresaRepository empresaRepo;
	@Autowired
	IActaRepository actaRepo;
	@Autowired
	IIndicadorContableRepository indicadorRepo;
	@Autowired
	IFabricaRepository fabricaRepo;
	@Autowired
	IEstadoActaRepository estadoActaRepo;
	@Autowired
	IActaDetalleRepository actaDetalleRepo;
	@Autowired
	IHJiraRepository hjiraRepo;
	@Autowired
	IHorasGestionDemandaRepository gestionDemandRepo;
	
	@Override
	public List<Acta> listarTodoActas() {
		return (List<Acta>) actaRepo.findAll();
	}

	@Override
	public List<Acta> listaActasPorUsuario(Usuario usuario) {
		return null;
	}

	@Override
	public Acta buscarActaPorID(int id) {
		return actaRepo.findById(id).orElse(null);
	}

	@Override
	public Acta buscarActaPorCodigo(String codigo) {

		return actaRepo.findByCodigo(codigo);
	}

	@Override
	public Acta guardarActa(Acta a) {
		return actaRepo.save(a);
	}

	@Override
	public void desactivarActa(Acta a) {
		a.setFlagactivo(false);
		actaRepo.save(a);
	}

	@Override
	public List<Acta_Estado> listarEstadoActa() {
		return (List<Acta_Estado>) estadoActaRepo.findAll();
	}
	
//------------------------ PERIODO
	@Override
	public List<Periodo> listarPeriodos() {
		return (List<Periodo>) periodoRepo.findAll();
	}

	@Override
	public Periodo buscarPeriodo(int id) {
		return periodoRepo.findById(id).orElse(null);
	}
	
//------------------------ PERIODO
	@Override
	public List<Empresa> listarEmpresas() {
		return (List<Empresa>) empresaRepo.findAll();
	}

	@Override
	public Empresa buscarEmpresa(int id) {
		return empresaRepo.findById(id).orElse(null);
	}
//------------------------ ESPECIALES
	@Override
	public List<DetalleActaPre> listarDetalleActaPRE(Usuario u, RespGenerica respuesta ) {
		Periodo p = periodoRepo.findById((int)respuesta.getNumero1()).orElse(null);
		Indicador_Contable indicador = indicadorRepo.findById((int)respuesta.getNumero2()).orElse(null);
		Empresa e = empresaRepo.findById((int) respuesta.getNumero3()).orElse(null);
		return actaRepo.listarDetalleActaPre(p.getInicio(), p.getFin(), u.getFabrica(),indicador,e);
	}
	
	@Override
	public Acta registrarNuevaActa(RespGenerica respuesta, Usuario usuario) {
		//variables para acta
		Fabrica fabrica = fabricaRepo.findById((int)respuesta.getNumero4()).orElse(null);
		Empresa empresa = empresaRepo.findById((int)respuesta.getNumero3()).orElse(null);
		Indicador_Contable tipo = indicadorRepo.findById((int) respuesta.getNumero2()).orElse(null);
		Periodo periodo = periodoRepo.findById((int) respuesta.getNumero1()).orElse(null);
		Acta_Estado estado = estadoActaRepo.findById(1).orElse(null); //ESTADO POR DEFECTO, 1 = REGISTRADO		
		Acta acta = new Acta();
		
		// CREACIÓN DE ACTA
		acta.setVersion("1.0");
		acta.setRazon_social_proveedor(fabrica.getNombre());
		acta.setDescripcion_servicio(respuesta.getTexto1());
		acta.setFecha_registro(new Date());
		acta.setFlagactivo(true);
		acta.setFlagaprobado(false);
		acta.setEstado(estado);
		acta.setPeriodo(periodo);
		acta.setIndicador(tipo);
		acta.setEmpresa(empresa);
		acta.setFabrica(fabrica);				
		acta = actaRepo.save(acta);

			
		double total_horas_desarrollo = 0.0;
		// SEPRAR LISTA DE JIRAS (APROBADOS Y PATEADOS
		List<DetalleActaPre> listaJirasPateados = new ArrayList<>();
		List<DetalleActaPre> listadetallePre = actaRepo.listarDetalleActaPre(periodo.getInicio(), periodo.getFin(), usuario.getFabrica(), tipo, empresa);
		for (DetalleActaPre dpre: listadetallePre) {
			if (!respuesta.getArregloStr().contains(dpre.getJira())) {
				listaJirasPateados.add(dpre);
				listadetallePre.remove(dpre);
			}else {
				total_horas_desarrollo = total_horas_desarrollo + dpre.getTotalHoras().doubleValue();
			}
		}
		
		// JIRAS APROBADOS
		// --Horas totales		
		for (DetalleActaPre dpre: listadetallePre) {
			HJira hjira = hjiraRepo.findByJira(dpre.getJira());
			Acta_detalle detalle = new Acta_detalle();
			detalle.setActa(acta);
			detalle.setJira(hjira);
			detalle.setNro_horas_jira(dpre.getTotalHoras());
			detalle.setNro_horas_concepto1(BigDecimal.valueOf((respuesta.getNumero5()/total_horas_desarrollo)*dpre.getTotalHoras().doubleValue()));
			detalle.setNro_horas_concepto1(BigDecimal.valueOf((respuesta.getNumero6()/total_horas_desarrollo)*dpre.getTotalHoras().doubleValue()));
			detalle.setNro_total_horas(detalle.getNro_horas_jira().add(detalle.getNro_horas_concepto1().add(detalle.getNro_horas_concepto2())));
			actaDetalleRepo.save(detalle);
		}	
		//Segundo guardado
		acta.setCodigo("ACT-" + acta.getId());
		return actaRepo.save(acta);
	}
//--------------------------- FABRICA
	@Override
	public List<Fabrica> listarFabricas() {
		return (List<Fabrica>) fabricaRepo.findAll();
	}

	@Override
	public Fabrica buscarFabricaPorID(int id) {
		return fabricaRepo.findById(id).orElse(null);
	}
//------------------------------------
	@Override
	public List<Acta> listaActasPorEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acta> listaActasPorIndicador(Indicador_Contable indicador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acta> listaActasPorEstado(Acta_Estado estado) {
		// TODO Auto-generated method stub
		return null;
	}
//--------------------------- indicador contable
	@Override
	public Indicador_Contable buscarIndicadorPorId(int id) {
		return indicadorRepo.findById(id).orElse(null);
	}

	@Override
	public List<Indicador_Contable> listarIndicadorContable() {
		return (List<Indicador_Contable>) indicadorRepo.findAll();
	}

}
