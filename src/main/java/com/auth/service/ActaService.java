package com.auth.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.repository.IActaDetalleRepository;
import com.auth.repository.IActaEstadoRepository;
import com.auth.repository.IActaRepository;
import com.auth.repository.IEmpresaRepository;
import com.auth.repository.IEstadoActaRepository;
import com.auth.repository.IFabricaRepository;
import com.auth.repository.IHJiraRepository;
import com.auth.repository.IHorasGestionDemandaRepository;
import com.auth.repository.IIndicadorContableRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.repository.IProveedorRegHorasRepository;
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
	@Autowired
	IActaEstadoRepository actaEstadoRepo;
	@Autowired
	IProveedorRegHorasRepository registrosRepo;
	
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
		return empresaRepo.findAllByOrderById();
	}

	@Override
	public Empresa buscarEmpresa(int id) {
		return empresaRepo.findById(id).orElse(null);
	}
//------------------------ ESPECIALES
	@Override
	public List<DetalleActaPre> listarDetalleActaPRE(Usuario u, RespGenerica respuesta ) {
		
		//Validar que no exista un acta con los parámetros de búsqueda		
		Periodo p = periodoRepo.findById((int)respuesta.getNumero1()).orElse(null);
		Indicador_Contable indicador = indicadorRepo.findById((int)respuesta.getNumero2()).orElse(null);
		Empresa e = empresaRepo.findById((int) respuesta.getNumero3()).orElse(null);
		// Si no existe el acta, se realiza la búsqueda de registros para dicho periodo  
		if (actaRepo.verificarSiExistePrevia(u.getFabrica().getId(),p.getId(),e.getId(),indicador.getId()) == null) {
			return actaRepo.listarDetalleActaPre(p.getInicio(), p.getFin(), u.getFabrica(),indicador,e);
		}		
		return null;
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

		// Buscar detalle acta PRE y Validar cantidad
		List<DetalleActaPre> listadetallePre = actaRepo.listarDetalleActaPre(periodo.getInicio(), periodo.getFin(), usuario.getFabrica(), tipo, empresa);
		List<Proveedor_Reg_Horas> listaRegistrosCompletos = registrosRepo.listarDetalleActaCompleto(usuario.getFabrica().getId(), periodo.getInicio(), periodo.getFin(), tipo.getId(), empresa.getId());
		if (respuesta.getArregloStr().size() == listadetallePre.size()) {
			
			//Horas de desarrollo totales (Sin prorrateo)
			BigDecimal total_horas_desarrollo = BigDecimal.ZERO;
			BigDecimal total_horas_concepto1_pre = BigDecimal.ZERO;
			BigDecimal total_horas_concepto1_final = BigDecimal.ZERO;
			BigDecimal acta_montobruto = BigDecimal.ZERO;
			for (DetalleActaPre dpre: listadetallePre) {
				total_horas_desarrollo = total_horas_desarrollo.add(dpre.getTotalHoras());
				total_horas_concepto1_pre = total_horas_concepto1_pre.add(dpre.getTotalHorasGesDem());
			}						
			for (DetalleActaPre dpre: listadetallePre) {
				HJira hjira = hjiraRepo.findByJira(dpre.getHjira().getJira());
				Acta_detalle detalle = new Acta_detalle();
				detalle.setActa(acta);
				detalle.setJira(hjira);				
				detalle.setTarifa( respuesta.getArregloDecimal().get(indexStrEnArreglo(hjira.getJira(), respuesta.getArregloStr())));
				detalle.setNro_horas_jira(dpre.getTotalHoras());
				// GESTION DE DEMANDA
				detalle.setNro_horas_concepto1(dpre.getTotalHorasGesDem().add( (BigDecimal.valueOf(respuesta.getNumero5()).divide(total_horas_desarrollo,8,RoundingMode.HALF_UP)).multiply(dpre.getTotalHoras()) ));
				total_horas_concepto1_final = total_horas_concepto1_final.add(detalle.getNro_horas_concepto1());
				//GESTION CONFIGURACION
				detalle.setNro_horas_concepto2( (BigDecimal.valueOf(respuesta.getNumero6()).divide(total_horas_desarrollo,8,RoundingMode.HALF_UP)).multiply(dpre.getTotalHoras()) );
				//Total de horas
				detalle.setNro_total_horas(detalle.getNro_horas_jira().add(detalle.getNro_horas_concepto1().add(detalle.getNro_horas_concepto2())));
				detalle.setCosto_total(detalle.getNro_total_horas().multiply(detalle.getTarifa()));			
				actaDetalleRepo.save(detalle);	
				acta_montobruto = acta_montobruto.add(detalle.getCosto_total());
			}	
			//TOTALES ACTA
			acta.setCodigo("ACT-" + acta.getId());
			acta.setTotal_horas_desarrollo(total_horas_desarrollo);
			acta.setTotal_horas_concepto1(total_horas_concepto1_final);		
			acta.setTotal_horas_concepto2(BigDecimal.valueOf(respuesta.getNumero6()));
			acta.setTarifa(BigDecimal.valueOf(respuesta.getNumero7()));
			
			BigDecimal total_horas = acta.getTotal_horas_desarrollo().add(acta.getTotal_horas_concepto1().add(acta.getTotal_horas_concepto2()));
			acta.setTotal_horas(total_horas);
			acta.setMonto_bruto(acta_montobruto);
			acta.setMonto_neto(acta_montobruto);
			
			// REGISTROS: Setear el acta en los registros
			for (Proveedor_Reg_Horas prh: listaRegistrosCompletos) {
				prh.setActa(acta);
				prh.setFlag_acta(true);
				registrosRepo.save(prh);
			}
			
			return actaRepo.save(acta);
		}else {
			return null;
		}
		
	}
	private int indexStrEnArreglo(String s, List<String> arreglo) {
		int i = 0;
		for (String elemento: arreglo) {
			if (elemento.equals(s)) {
				return i;
			}
			i++;
		}
		return -1;
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
		return actaRepo.findAllByEmpresa(empresa);
	}
	
	@Override
	public List<Acta> listaActasPorFabrica(Fabrica fab) {
		return actaRepo.findAllByFabrica(fab);
	}

	@Override
	public List<Acta> listaActasPorIndicador(Indicador_Contable indicador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acta> listaActasPorEstado(int id_estado) {
		Acta_Estado estado = estadoActaRepo.findById(id_estado).orElse(null);
		return actaRepo.findAllByEstado(estado);
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

	@Override
	public List<Acta_detalle> listarDetalle(Acta acta) {

		return actaDetalleRepo.findAllByActa(acta);
	}

	@Override
	public void cambiarEstadoActa(RespGenerica respuesta, Usuario u,int estado_nuevo_id) {
		Acta acta = actaRepo.findById((int)respuesta.getNumero1()).orElse(null);
		// Validar que el usuario tenga permisos sobre el acta
		if (acta.getFabrica().getId() == u.getFabrica().getId()) {			
			Acta_Estado estado = actaEstadoRepo.findById(estado_nuevo_id).orElse(null); 
			acta.setDescripcion_servicio(respuesta.getTexto1());
			acta.setEstado(estado);
			actaRepo.save(acta);
		}
	}

	@Override
	public void revisarActaGerente(RespGenerica respuesta) {
		Acta acta = actaRepo.findById((int)respuesta.getNumero1()).orElse(null);
		Acta_Estado estado = actaEstadoRepo.findById((int)respuesta.getNumero2()).orElse(null); 
		acta.setEstado(estado);
		actaRepo.save(acta);

	}

	@Override
	public void eliminarActa(int id_acta) {
		Acta acta = actaRepo.findById(id_acta).orElse(null);
		if (acta.getEstado().getId() == 1 ||acta.getEstado().getId() == 4 ) {
			// Eliminar DETALLE ACTA
			List<Acta_detalle> listaDetalle = actaDetalleRepo.findAllByActa(acta);
			for (Acta_detalle detalle:listaDetalle) {
				actaDetalleRepo.delete(detalle);
			}			
			//Liberar REGISTROS DE HORAS
			int id_fabrica = acta.getFabrica().getId();
			String inicio = acta.getPeriodo().getInicio();
			String fin = acta.getPeriodo().getFin();
			int id_tipo = acta.getIndicador().getId();
			int id_empresa = acta.getEmpresa().getId();	
			List<Proveedor_Reg_Horas> listaRegistrosCompletos = registrosRepo.listarDetalleActaCompleto(id_fabrica, inicio, fin, id_tipo, id_empresa);

			for (Proveedor_Reg_Horas prh: listaRegistrosCompletos) {
				prh.setActa(null);
				prh.setFlag_acta(false);
				registrosRepo.save(prh);
			}			
			// Eliminar ACTA
			actaRepo.delete(acta);
		}		
	}

}
