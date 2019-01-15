package com.auth.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.HJira;
import com.auth.entity.Horas_Gestion_Demanda;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Tipo_Actividad_Proveedor;
import com.auth.entity.Usuario;
import com.auth.repository.IEmpresaRepository;
import com.auth.repository.IEstadoRegHorasRepository;
import com.auth.repository.IFabricaRepository;
import com.auth.repository.IHJiraRepository;
import com.auth.repository.IHorasGestionDemandaRepository;
import com.auth.repository.IIndicadorContableRepository;
import com.auth.repository.IJiraRepository;
import com.auth.repository.IPeriodoRepository;
import com.auth.repository.IProveedorRegHorasRepository;
import com.auth.repository.ITipoActividadProveedor;
import com.auth.repository.IUsuarioRepository;
import com.auth.rest.RespGenerica;

@Service
public class RegistroHorasService implements IRegistroHorasService {
	@Autowired
	IProveedorRegHorasRepository regHorasRepo;
	@Autowired
	IJiraRepository jiraRepo;
	@Autowired
	IUsuarioRepository usuarioRepo;	
	@Autowired
	IPeriodoRepository periodoRepo;
	@Autowired
	IEstadoRegHorasRepository estadoRepo;
	@Autowired
	IHJiraRepository hxjRepo;	
	@Autowired
	ITipoActividadProveedor tipoActividadRepo;
	@Autowired
	IFabricaRepository fabricaRepo;
	@Autowired
	IEmpresaRepository empresaRepo;
	@Autowired
	IIndicadorContableRepository indicadorRepo;
	@Autowired
	IHorasGestionDemandaRepository hgDemandaRepo;
	
	
	@Override
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros) {
		for (Proveedor_Reg_Horas registro: listaRegistros ) {
			regHorasRepo.save(registro);
		}		
	}

	@Override
	public Proveedor_Reg_Horas guardarRegHoras(Proveedor_Reg_Horas registro) {
		return regHorasRepo.save(registro);
	}

	@Override
	public void eliminarRegistro(int id) {
		regHorasRepo.deleteById(id);
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosEnviadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarEnviadoPorUsuario(desarrollador.getId());
	}
	
	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarConfirmadosPorUsuario(desarrollador.getId());
	}
	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrolladorMes(Usuario desarrollador) {
		return regHorasRepo.listarConfirmadosPorUsuario(desarrollador.getId());
	}
	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosAprobadosPorDesarrollador(Usuario desarrollador) {
		return regHorasRepo.listarAprobadosPorUsuario(desarrollador.getId());
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado) {
		// TODO Auto-generated method stub
		return null;
	}
// --------------- PERIODO
	
	@Override
	public List<Periodo> listarPeriodos() {
		return (List<Periodo>) periodoRepo.findAllByOrderByPeriodoAsc();
	}
	
	@Override
	public Periodo periodoActual() {
		Calendar fecha = Calendar.getInstance();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        String codigo = Integer.toString(anio);
        if (mes > 9) {
        	codigo = codigo.concat(Integer.toString(mes));
        }else {
        	codigo = codigo.concat("0".concat(Integer.toString(mes)));
        }
		return periodoRepo.findByPeriodo(codigo);
	}

	

	@Override
	public Proveedor_Reg_Horas buscarRegPorID(int id) {
		return regHorasRepo.findById(id).orElse(null);
	}

	@Override
	public Estado_Reg_Horas buscarEstadoPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HorasPorSemana> listarDiasdelMes(int id_usuario, String periodo) {
		//Variables
		Usuario u = usuarioRepo.findById(id_usuario).orElse(null);
		Estado_Reg_Horas e1 = estadoRepo.findById(1).orElse(null); //Estado 1: 1 = aprobado
		Estado_Reg_Horas e2 = estadoRepo.findById(2).orElse(null); //Estado 2: 2 = confirmado
		
		//Formatos
		SimpleDateFormat formatoCorto = new SimpleDateFormat("dd");
		SimpleDateFormat formatoLargo = new SimpleDateFormat("dd-MM-yyyy");		
		String fechal1, fechal2, fechaCorta;
				
		//Fechas
		Date inicio = new Date();
		Date fin = new Date();
		//Encontrar día inicial del mes		
		String fecha_inicial ="01-" + periodo.substring(4,6) +'-'+ periodo.substring(0, 4);		

		Calendar calendario2 = Calendar.getInstance();
		try {
			calendario2.setTime(formatoLargo.parse(fecha_inicial));
			inicio = calendario2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Encontrar último dia del mes
		calendario2.add(Calendar.MONTH, 1);  
		calendario2.set(Calendar.DAY_OF_MONTH, 1);  
		calendario2.add(Calendar.DATE, -1);  		
		int nro_dias = calendario2.get(Calendar.DAY_OF_MONTH);
		fin = calendario2.getTime();		
		//Resetear calendaro
		try {
			calendario2.setTime(formatoLargo.parse(fecha_inicial));			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		//ARREGLOS
		List<HorasPorSemana> lstHoras = regHorasRepo.horasMes(u,e1,e2, fin, inicio);
		List<HorasPorSemana> listaFinal = new ArrayList<>();
		int j = 0;
		//Llenar nuevo arreglo		
		for (int i = 0; i < nro_dias; i++) {	
			HorasPorSemana hxdia = new HorasPorSemana();
			fechaCorta = formatoCorto.format(calendario2.getTime());
			fechal1 = formatoLargo.format(calendario2.getTime());
			
			if (lstHoras.size() > j)
				fechal2 = formatoLargo.format(lstHoras.get(j).getFecha());
			else 
				fechal2 = "";
			hxdia.setFecha(calendario2.getTime());
			hxdia.setLeyenda(fechaCorta);
			if (fechal1.equals(fechal2)) {				
				hxdia.setTotal(lstHoras.get(j).getTotal());
				j++;
			}else {
				hxdia.setTotal(BigDecimal.ZERO);
			}
			listaFinal.add(hxdia);
			calendario2.add(Calendar.DAY_OF_YEAR, +1);
		}
		return listaFinal;
	}
	@Override
	public List<HorasPorSemana> listarDiasPorSemana(int id_usuario) {
		//Variables
		Usuario u = usuarioRepo.findById(id_usuario).orElse(null);
		Estado_Reg_Horas e1 = estadoRepo.findById(1).orElse(null); //Estado 1: 1 = aprobado
		Estado_Reg_Horas e2 = estadoRepo.findById(2).orElse(null); //Estado 2: 2 = confirmado		
		int j = 0; //Contador
		
		//Encontrar día de la semana
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		int dow = calendario.get(Calendar.DAY_OF_WEEK); 
		//Ultimo domingo de la semana actual:
		calendario.add(Calendar.DAY_OF_YEAR, 8- dow); 	

		//Formatos
		SimpleDateFormat formatoCorto = new SimpleDateFormat("dd");
		SimpleDateFormat formatoLargo = new SimpleDateFormat("dd-MM-yyyy");		
		String fechal1, fechal2, fechaCorta;
		
		//Fechas limite
		Calendar calendario2 = Calendar.getInstance();
		calendario2.setTime(calendario.getTime());
		calendario2.add(Calendar.DAY_OF_YEAR, -14);		
		
		//Arreglos
		List<HorasPorSemana> lstHoras = regHorasRepo.horasSemanales(u,e1,e2, calendario.getTime(), calendario2.getTime());
		List<HorasPorSemana> listaFinal = new ArrayList<>();
		//Llenar nuevo arreglo		
		for (int i = 0; i < 14; i++) {	
			HorasPorSemana hxdia = new HorasPorSemana();
			fechaCorta = formatoCorto.format(calendario.getTime());
			fechal1 = formatoLargo.format(calendario.getTime());			
			if (lstHoras.size() > j)
				fechal2 = formatoLargo.format(lstHoras.get(j).getFecha());
			else 
				fechal2 = "";
			hxdia.setFecha(calendario.getTime());
			hxdia.setLeyenda(fechaCorta);
			if (fechal1.equals(fechal2)) {				
				hxdia.setTotal(lstHoras.get(j).getTotal());
				j++;
			}else {
				hxdia.setTotal(BigDecimal.ZERO);
			}
			listaFinal.add(hxdia);
			calendario.add(Calendar.DAY_OF_YEAR, -1);
		}
		return listaFinal;
	}

	@Override
	public double horasTrabajadas(String jira) {
		return regHorasRepo.horasTrabajadas(jira);
	}

	@Override
	public List<Tipo_Actividad_Proveedor> listarTiposActividad() {
		return tipoActividadRepo.findAllByOrderById();
	}

	@Override
	public Tipo_Actividad_Proveedor buscarTipoActividadPorID(int id) {
		return tipoActividadRepo.findById(id).orElse(null);
	}

// TABLA DE REGISTROS DESARROLLADOR
	//primer registro
	@Override
	public String registrarHoras(Usuario u, RespGenerica respuesta) {
		//Encontrar Periodo
		String[] arreglostr_fecja = respuesta.getTexto2().split("-");
		String periodo = arreglostr_fecja[0] + arreglostr_fecja[1];
		Periodo p = periodoRepo.findByPeriodo(periodo);
		if (p != null) {
			Proveedor_Reg_Horas registro = new Proveedor_Reg_Horas();
			// JIRA HISTORICO
			HJira hjira = hxjRepo.findByJira(respuesta.getTexto1());
			registro.setHjira(hjira);
			// TIPO DE ACTIVIDAD (DESARROLLO Y MEJORA SON FACTURABLES)
			registro.setTipoActividad(tipoActividadRepo.findById((int) respuesta.getNumero2()).orElse(null));
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1 ||  registro.getTipoActividad().getId() == 5) {
				registro.setFlagfacturar(true);
			}else {
				registro.setFlagfacturar(false);
			}
			// NRO HORAS
			registro.setNro_horas( BigDecimal.valueOf(respuesta.getNumero1()));
			// VALIDAR si quedan horas: 
			if (registro.isFlagfacturar()) {
				if (hjira.getFabrica().getNombre().equals("GMD") && !hjira.getTipo().equals("Proyecto") && registro.getTipoActividad().getId() != 5) {
					// FACTOR DE GESTIÓN DE DEMANDA
					Usuario gestor = usuarioRepo.findByUsername("patricia.chocano");
					if (gestor == null) {
						return "No se encontró información sobre el Gestor de Demanda";
					}
					Horas_Gestion_Demanda hgd = hgDemandaRepo.buscarPorUsuarioYPeriodo(gestor.getId(), p.getId());
					BigDecimal factor = hgd.getFactor();
					if (quedanHorasConGesDemanda(hjira, registro, factor)) {
						// GENERAR HORAS GESTION DEMANDA
						registro.setNro_horas_gestion(registro.getNro_horas().multiply(factor));
						//Las horas facturables se restan del pull de horas totales (DESARROLLO o PRUEBAS)
						hjira.setConsumido_desarrollo(hjira.getConsumido_desarrollo().add(registro.getNro_horas()).add(registro.getNro_horas_gestion()));					
						hxjRepo.save(hjira);
					}else {
						return "No quedan horas.";
					}					
				//Validar que quedan horas simple
				}else if (registro.getNro_horas().compareTo(hjira.getHoras_desarrollo().subtract(hjira.getConsumido_desarrollo())) == 1) {
					registro.setNro_horas_gestion(BigDecimal.ZERO);
					//Las horas facturables se restan del pull de horas totales (DESARROLLO o PRUEBAS)
					hjira.setConsumido_desarrollo(hjira.getConsumido_desarrollo().add(registro.getNro_horas()).add(registro.getNro_horas_gestion()));					
					hxjRepo.save(hjira);
				}else {
					return "No quedan horas";
				}
			}
			// USUARIO QUE REGISTRA
			registro.setUsuario(u);
			// ESTADO DE REGISTRO (CREADO)
			registro.setEstado(estadoRepo.findById(3).orElse(null)); //ESTADO INICIAL: 3=CREADO
			// FECHAS			
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(respuesta.getTexto2());
				registro.setFecha_real_trabajo(date);
				registro.setFecha_facturacion(date);	// FECHA DE FACTURACION POR DEFECTO: FECHA DE TRABAJO REAL
			} catch (ParseException e) {
				e.printStackTrace();
				return "Error con la fecha!";
			}			
			registro.setFecha_registro(new Date());						
			// COMENTARIO (OPCIONAL)
			registro.setComentario(respuesta.getTexto5());	
			// RETORNAR REGISTRO
			registro = regHorasRepo.save(registro);
			return registro.getId().toString();
		}else {
			return "No se puede registrar actividades en el periodo seleccionado. Si crees que se trata de un error, comunícate con el administrador del sistema.";
		}	
	}
	// AUXILIAR : QUEDAN HORAS
	public boolean quedanHorasConGesDemanda(HJira hjira, Proveedor_Reg_Horas registro, BigDecimal factor ) {
		BigDecimal horas_disponibles = (hjira.getHoras_desarrollo().subtract(hjira.getConsumido_desarrollo()));
		if (horas_disponibles.compareTo(registro.getNro_horas().multiply(factor.add(BigDecimal.valueOf(1)))) == 1) {
			return true;
		}else {
			return false;
		}
		
	}
	
	// CAMBIAR ESTADOS
	@Override
	public String cambiarEstadoRegistro(Proveedor_Reg_Horas registro, int id_estado, Usuario u) {
		registro.setEstado(estadoRepo.findById(id_estado).orElse(null));
		regHorasRepo.save(registro);
		//EN CASO SEA APROBACIÓN: CONSIDERAR HORAS DE GESTIÓN DE DEMANDA (SOLO PARA REQUES FACTURABLES DE GMD(id 21))
		if (id_estado == 1 && registro.isFlagfacturar() && registro.getUsuario().getFabrica().getId() == 21) { 					
			//--- Periodo 
			String fecha =  registro.getFecha_real_trabajo().toString().substring(0, 4) + registro.getFecha_real_trabajo().toString().substring(5, 7);
			Periodo p = periodoRepo.findByPeriodo(fecha);			
			//--- Horas Gestion demanda
			Horas_Gestion_Demanda hgd = hgDemandaRepo.buscarPorUsuarioYPeriodo(u.getId(), p.getId());
			//- Verificar que existan horas de GESTIÓN DE DEMANDA	
			if (hgd.getHoras_consumidas().add(registro.getNro_horas_gestion()).compareTo(hgd.getTotal_horas()) == 1) { //Si es mayor (Supera el tope de horas)
				// En caso no, realizar el prorrateo
				prorateoRegistros(u.getId(),u.getFabrica().getId(),p,registro.getNro_horas_gestion(),hgd); 
			}else {
				hgd.setHoras_consumidas(hgd.getHoras_consumidas().add(registro.getNro_horas_gestion()));
				hgDemandaRepo.save(hgd);
			}
			
		}
		return "Confirmado";
	}
	
	//ELIMINAR
	@Override
	public String eliminarHoras(Usuario u, RespGenerica respuesta) {		
		Proveedor_Reg_Horas registro = regHorasRepo.findById((int)respuesta.getNumero1()).orElse(null);		
		if (registro.getUsuario() == u) {
			regHorasRepo.deleteById((int)respuesta.getNumero1());
			HJira hxj = hxjRepo.findByJira(registro.getHjira().getJira());
			// Si las horas son desarrollo o mejora, se restan de las horas CONSUMIDAS 
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1 || registro.getTipoActividad().getId() == 5) { //4- Mejora | 1- Desarrollo 
				hxj.setConsumido_desarrollo(hxj.getConsumido_desarrollo().subtract(registro.getNro_horas()).subtract(registro.getNro_horas_gestion()));
				hxjRepo.save(hxj);
			}
			return "Eliminado";			
		}else {
			return "Error, no puede eliminar registros de otras personas";
		}

	}

	@Override
	public Horas_Gestion_Demanda buscarHGDemandaPorUsuario(int u, int p) {
		return hgDemandaRepo.buscarPorUsuarioYPeriodo(u,p);
	}


	private String prorateoRegistros(int id_usu, int id_fab, Periodo periodo, BigDecimal horas, Horas_Gestion_Demanda hgestDem) {
		List<Proveedor_Reg_Horas> lstRegistros = regHorasRepo.listarParaProrateo(id_fab, periodo.getInicio(), periodo.getFin());
		// FORMULA PARA PRORATEAR: encontrar nuevo factor
		BigDecimal factor_actual = hgestDem.getFactor();
		BigDecimal tope = hgestDem.getTotal_horas();
		BigDecimal total_horas = hgestDem.getHoras_consumidas().add(horas);
		BigDecimal nuevo_factor = (factor_actual.multiply(tope)).divide(total_horas,8, RoundingMode.HALF_UP);		
		BigDecimal nuevo_consumido = BigDecimal.ZERO;
		String str = "";
		for (Proveedor_Reg_Horas prh: lstRegistros) {
			prh.setNro_horas_gestion(prh.getNro_horas().multiply(nuevo_factor));
			regHorasRepo.save(prh);	//Se cambian el total de GEST DEM en cada registro
			if (prh.getEstado().getId() == 1) {
				
				
				//str = str + nuevo_/consumido.add(prh.getNro_horas_gestion()).toString() + " + ";
				//System.out.println(nuevo_consumido.doubleValue());
				//System.out.println(nuevo_consumido.toString());
				//System.out.println(prh.getNro_horas_gestion());
				//System.out.println(nuevo_consumido.add(prh.getNro_horas_gestion()));
				nuevo_consumido = nuevo_consumido.add(prh.getNro_horas_gestion());
				
			}
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>> _c" + str);
		hgestDem.setHoras_consumidas(nuevo_consumido);
		hgestDem.setFactor(nuevo_factor);
		hgDemandaRepo.save(hgestDem); // Se actualiza el total de GEST DEM y el FACTOR
		return "El trabajo está hecho";
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabricayEstado(int id_estado, int id_fabrica) {
		return regHorasRepo.listarPorFabricaYEstado(id_fabrica, id_estado);
	}

	@Override
	public String registrarHorasCertificacion(Usuario u, RespGenerica respuesta) {
		Proveedor_Reg_Horas registro = new Proveedor_Reg_Horas();
		// JIRA HISTORICO
		HJira hjira = hxjRepo.findByJira(respuesta.getTexto1());
		registro.setHjira(hjira);
		// TIPO DE ACTIVIDAD (DESARROLLO, bloqueante Y MEJORA SON FACTURABLES)
		registro.setTipoActividad(tipoActividadRepo.findById((int) respuesta.getNumero2()).orElse(null));
		if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1 ||  registro.getTipoActividad().getId() == 5) {
			registro.setFlagfacturar(true);
		}else {
			registro.setFlagfacturar(false);
		}
		// NRO HORAS
		registro.setNro_horas( BigDecimal.valueOf(respuesta.getNumero1()));
		// VALIDAR si quedan horas: 
		if (registro.isFlagfacturar()) {			
			if (registro.getNro_horas().compareTo(hjira.getHoras_prueba().subtract(hjira.getConsumido_prueba())) != 1) {
				registro.setNro_horas_gestion(BigDecimal.ZERO);
				//Las horas facturables se restan del pull de horas totales (DESARROLLO o PRUEBAS)
				hjira.setConsumido_prueba(hjira.getConsumido_prueba().add(registro.getNro_horas()));					
				hxjRepo.save(hjira);
			}else {
				return "No quedan suficientes horas disponibles.";
			}
		}
		// USUARIO QUE REGISTRA
		registro.setUsuario(u);
		// ESTADO DE REGISTRO (CREADO)
		registro.setEstado(estadoRepo.findById(3).orElse(null)); //ESTADO INICIAL: 3=CREADO
		// FECHAS			
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(respuesta.getTexto2());
			registro.setFecha_real_trabajo(date);
			registro.setFecha_facturacion(date);	// FECHA DE FACTURACION POR DEFECTO: FECHA DE TRABAJO REAL
		} catch (ParseException e) {
			e.printStackTrace();
			return "Error con la fecha!";
		}			
		registro.setFecha_registro(new Date());						
		// COMENTARIO (OPCIONAL)
		registro.setComentario(respuesta.getTexto5());	
		// RETORNAR REGISTRO
		registro = regHorasRepo.save(registro);
		return registro.getId().toString();
	
		}

	@Override
	public String eliminarHorasCertificacion(Usuario u, RespGenerica respuesta) {
		Proveedor_Reg_Horas registro = regHorasRepo.findById((int)respuesta.getNumero1()).orElse(null);		
		if (registro.getUsuario() == u) {
			regHorasRepo.deleteById((int)respuesta.getNumero1());
			HJira hxj = hxjRepo.findByJira(registro.getHjira().getJira());
			// Si las horas son desarrollo, bloqueante o mejora, se restan de las horas CONSUMIDAS 
			if (registro.getTipoActividad().getId() == 4 ||  registro.getTipoActividad().getId() == 1 || registro.getTipoActividad().getId() == 5) { //4- Mejora | 1- Desarrollo | 5- Bloqueante
				hxj.setConsumido_prueba(hxj.getConsumido_prueba().subtract(registro.getNro_horas()));
				hxjRepo.save(hxj);
			}
			return "Eliminado";			
		}else {
			return "Error, no puede eliminar registros de otras personas";
		}
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabrica(int id_fabrica) {
		return regHorasRepo.listarPorFabrica(id_fabrica);
	}

	@Override
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabricaEntrePeriodos(int id_fabrica, String fecha1,
			String fecha2) {
		return regHorasRepo.listarPorFabricaEntreFechas(id_fabrica, fecha1, fecha2);
	}

	@Override
	public String actualizarFechaEntrega(Usuario u, RespGenerica objeto) {
		HJira hjira = hxjRepo.findByJira(objeto.getTexto2());
		SimpleDateFormat formatoLargo = new SimpleDateFormat("dd-MM-yyyy");	
		
		if (u.getFabrica().getId() != 22) { // 22 = pandora
			if (objeto.getTexto1().equals("") ) {
				hjira.setFecha_entrega_desarrollo(null);
				hxjRepo.save(hjira);
			}else {
				try {
					Date fecha = formatoLargo.parse(objeto.getTexto1());
					hjira.setFecha_entrega_desarrollo(fecha);
					hxjRepo.save(hjira);
					
				} catch (ParseException e) {
					return "error";
				}
			}
		}else {
			if (objeto.getTexto1().equals("") ) {
				hjira.setFecha_entrega_certificacion(null);
				hxjRepo.save(hjira);
			}else {
				try {
					Date fecha = formatoLargo.parse(objeto.getTexto1());
					hjira.setFecha_entrega_certificacion(fecha);
					hxjRepo.save(hjira);
					
				} catch (ParseException e) {
					return "error";
				}
			}
		}		
		
		return "exito";
	}
}
