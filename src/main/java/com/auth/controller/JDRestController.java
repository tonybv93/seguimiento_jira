package com.auth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entity.Acuerdos;
import com.auth.entity.Jira;
import com.auth.entity.Jira_Detalle;
import com.auth.rest.RespAcuerdoTerminado;
import com.auth.rest.RespFechas;
import com.auth.service.IAcuerdosService;
import com.auth.service.IJiraService;
import com.auth.service.IJiraDetalleService;


@RestController
@RequestMapping("/rest")
public class JDRestController {
	
	@Autowired
	private IJiraService jiraService;
	@Autowired
	private IJiraDetalleService detalleService;
	@Autowired
	private IAcuerdosService acuerdoService;
	
// -------------------------------------------------------- JIRA--------------------------------------
	// Enviar una lista: JIRAS
	@RequestMapping("/jsonjira")
	@ResponseBody
	public List<Jira> listarJiras(){
		List<Jira> lista = jiraService.listarJiras();
		return lista;
	}
	
	// Buscar JIRAS
	@GetMapping("/jira/{id}")
	@ResponseBody
	public Jira buscarJira(@PathVariable(name="id") Integer id){
		String str = "RSIS18-" + id;
		Jira jira = jiraService.buscarPorJira(str);
		return jira;
	}
	
	/* Obtener respuesta de ajax : ACTUALIZAR FECHAS JIRA
	@PostMapping("/fechas")
	public String guardarFechas (@RequestBody RespFechas jsonFechas) {		
		Jira_Detalle jdet = null;	
		String jira = jsonFechas.getJira();
		Jira jbd = jiraService.buscarPorJira(jira);
		
		if (detalleService.buscarPorJira(jira)!= null) 
			jdet = detalleService.buscarPorJira(jira);
		else {
			jdet = new Jira_Detalle();
			jdet.setJira(jira);
		}
		jdet.setFecha_produccion(jsonFechas.getFecha2());
		jdet.setFecha_pruebas(jsonFechas.getFecha1());
		
		detalleService.guardarNuevo(jdet);
		jbd.setFecha_produccion(jsonFechas.getFecha2());
		jbd.setFecha_pruebas(jsonFechas.getFecha1());
		jiraService.guardar(jbd);
		return "Actualización exitosa";
	}*/
	
// -------------------------------------------------------- ACUERDOS--------------------------------------
	// Obtener respuesta de ajax : NUEVO ACUERDO
	@PostMapping("/acuerdo/nuevo")
	public Acuerdos nuevoAcuerdo (@RequestBody Acuerdos jsonAcuerdo) {	
		jsonAcuerdo = acuerdoService.guardar(jsonAcuerdo);
		return jsonAcuerdo;
	}
	
	// Obtener respuesta de ajax: TERMINAR ACUERDO
		@PostMapping("/acuerdo/terminado")
		public Acuerdos terminarAcuerdo (@RequestBody RespAcuerdoTerminado jsonAcuerdo) {
			Acuerdos acuerdo = acuerdoService.buscarPorId(jsonAcuerdo.getId());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String strDate = dateFormat.format(new Date());
			acuerdo.setTerminado(1);
			if (jsonAcuerdo.getTipo() == 1) {
				acuerdo.setEstado("Terminado");
				acuerdo.setClase("est_pospro");
			}
			else if (jsonAcuerdo.getTipo() == 2) {
				acuerdo.setEstado("Cancelado");
				acuerdo.setClase("est_");
			}
				
			acuerdo.setFecha_cierre(strDate);
			acuerdo.setObservacion(jsonAcuerdo.getObservacion());
			acuerdo = acuerdoService.actualizar(acuerdo);
				
			return acuerdo;
		}

}
