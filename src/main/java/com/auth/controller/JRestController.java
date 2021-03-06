package com.auth.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.auth.entity.Acuerdos;
import com.auth.entity.Estado_Acuerdo;
import com.auth.entity.Jira;
import com.auth.entity.Jira_Detalle;
import com.auth.entity.Menu;
import com.auth.entity.Usuario;
import com.auth.rest.RespAcuerdoTerminado;
import com.auth.rest.RespFechas;
import com.auth.rest.RespGenerica;
import com.auth.service.IActaService;
import com.auth.service.IAcuerdosService;
import com.auth.service.IJiraDetalleService;
import com.auth.service.IJiraService;
import com.auth.service.IMenuService;
import com.auth.service.IUsuarioService;


@RestController
@RequestMapping("/rest")
public class JRestController {
	
	@Autowired
	private IJiraService jiraService;
	@Autowired
	private IJiraDetalleService jdetalleService;
	@Autowired
	private IAcuerdosService acuerdoService;
	@Autowired
	private IUsuarioService usuarioService;		
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IActaService actaService;
// -------------------------------------------------------- ACTA -------------------------------------
	@PostMapping("/acta/confirmarenvio")
	@ResponseBody
	public String confirmarEnvioActa(@RequestBody RespGenerica respuesta){		
		actaService.revisarActaGerente(respuesta);
		return "Hecho!";
	}
// -------------------------------------------------------- JIRA--------------------------------------
	// Enviar una lista: JIRAS
	@RequestMapping("/jira/lista")
	@ResponseBody
	public List<Jira> listarJiras(){
		List<Jira> lista = jiraService.listarJiras();
		return lista;
	}
	
	// Buscar JIRAS
	@GetMapping("/jira/{jira}")
	@ResponseBody
	public Jira buscarJira(@PathVariable(name="jira") String jira){
		Jira rjira = jiraService.buscarPorJira(jira);
		return rjira;
	}
	
	// ACTUALIZAR FECHAS JIRA
	@PostMapping("/jira/fechas")
	public String guardarFechas (@RequestBody RespFechas jsonFechas) {		
		Jira_Detalle jdet = null;	
		String jira = jsonFechas.getJira();
		Jira jbd = jiraService.buscarPorJira(jira);
		
		Jira_Detalle jira_detalle = jdetalleService.buscarPorJira(jira);
		if (jira_detalle != null) 
			jdet = jira_detalle;
		else {
			jdet = new Jira_Detalle();
			jdet.setJira(jira);
		}

		String str_f1 = jsonFechas.getFecha2();
		String str_f2 = jsonFechas.getFecha1();
		Date fecha = null;
		DateFormat formatoFechas = new SimpleDateFormat("dd-MM-yyyy");
		
		if (str_f1 == "")
			fecha = null;
		else
			try {
				fecha = formatoFechas.parse(str_f1);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		
		jdet.setFecha_produccion(fecha);
		jbd.setFecha_produccion(fecha);
		
		if (str_f2 == "")
			fecha = null;
		else
			try {
				fecha = formatoFechas.parse(str_f2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		jdet.setFecha_pr_usuario(fecha);		
		jbd.setFecha_pruebas(fecha);
		
		jdetalleService.guardarNuevo(jdet);			
		jiraService.guardar(jbd);
		return "Actualización exitosa";
	}
	
// -------------------------------------------------------- ACUERDOS-----------------------------------
	// Obtener respuesta de ajax : NUEVO ACUERDO
	@PostMapping("/acuerdo/nuevo")
	public Acuerdos nuevoAcuerdo (@RequestBody RespGenerica jsonAcuerdo) {	
		Acuerdos acuerdo = new Acuerdos();		
		acuerdo.setAcuerdo(jsonAcuerdo.getTexto2());	
		DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha;
		try {
			fecha = formatoFechas.parse(jsonAcuerdo.getTexto1());			
			acuerdo.setFecha_limite(fecha);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		acuerdo.setTipo(acuerdoService.buscarTipoAcuerdoPorId((int)jsonAcuerdo.getNumero1()));
		if (jsonAcuerdo.getNumero1() == 1)
			acuerdo.setId_jira(jiraService.buscarPorId((int)jsonAcuerdo.getNumero4()).getJira());
		else
			acuerdo.setAreaSolicitante(acuerdoService.buscarAreaPorId((int)jsonAcuerdo.getNumero2()));	
			
		acuerdo.setResponsable(acuerdoService.buscarUsuarioPorId((int)jsonAcuerdo.getNumero3()));
		acuerdo.setEstado(acuerdoService.buscarEstadoPorId(1)); //Estado por defecto: 1-En proceso
		acuerdo = acuerdoService.guardar(acuerdo);		
		return acuerdo;
	}
	
	// Obtener respuesta de ajax: TERMINAR ACUERDO
	@PostMapping("/acuerdos/terminar")
	public Acuerdos terminarAcuerdo (@RequestBody RespAcuerdoTerminado jsonAcuerdo) {
		Acuerdos acuerdo = acuerdoService.buscarPorId(jsonAcuerdo.getId());
		Estado_Acuerdo estado = acuerdoService.buscarEstadoPorId(jsonAcuerdo.getTipo());
		
		Date hoy = (new Date());
		acuerdo.setFlagterminado(true);
		acuerdo.setEstado(estado);
		acuerdo.setFecha_cierre(hoy);
		acuerdo.setObservacion(jsonAcuerdo.getObservacion());
		acuerdo = acuerdoService.actualizar(acuerdo);			
		return acuerdo;
	}
// -------------------------------------------------------- GRÁFICOS----------------------------------
	@GetMapping("/pie/cavali/atencion")
	@ResponseBody
	public int[] cavaliAtencion () {		
		return jiraService.jirasAtendidosPorArea();
	}
	
	@GetMapping("/pie/cavali/noatencion")
	@ResponseBody
	public int[] cavaliNoAtencion () {		
		return jiraService.jirasNoAtendidosPorArea();
	}

// --------------------------------------------------CONSTRUIR MENU
	@GetMapping("/sidemenu")
	@ResponseBody
	public List<Menu> hacerMenu(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
		List<Menu> menus = menuService.listarMenuPorRol(usuario.getRoles().get(0).getId());		
		return menus;
	}
}

