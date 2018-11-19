package com.auth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Acuerdos;
import com.auth.service.IAcuerdosService;
import com.auth.service.IJiraService;

@Controller
@RequestMapping("/acuerdos")
public class AcuerdosController {
	
	@Autowired
	private IAcuerdosService acuerdoService;
	@Autowired
	private IJiraService jiraService;
	
	//MOSTRAR PANEL ADMIN DE ACUERDOS CAVALI
	@GetMapping("/cavali/agregar")
	public String acuerdosCavali(Model model) {	
		model.addAttribute("acuerdonuevo",new Acuerdos());
		model.addAttribute("listaJiras",jiraService.listarJiras());
		model.addAttribute("listaAcuerdos", acuerdoService.listarTodos());
		return "acuerdos";
	}
	
	//GUARDAR
	@PostMapping("guardar")
	public String guardarAcuerdo(Model model, Acuerdos acuerdo) {
		acuerdoService.guardar(acuerdo);
		model.addAttribute("acuerdonuevo",new Acuerdos());
		return "redirect:/acuerdos/cavali";
	}
	
	//DETALLE
	@PostMapping("/ver")
	public String editarAcuerdos(Model model, @RequestParam(name="id") Integer id) {
		model.addAttribute("acuerdo",acuerdoService.buscarPorId(id));		
		return "acuerdodetalle";
	}
	// CERRAR
	@PostMapping("/cerrar")
	public String terminado(Model model, @RequestParam(name="id") Integer id) {
		Acuerdos acuerdo = acuerdoService.buscarPorId(id);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = dateFormat.format(new Date());
		acuerdo.setTerminado(1);
		acuerdo.setEstado("Terminado");
		acuerdo.setClase("est_pospro");
		acuerdo.setFecha_cierre(strDate);
		
		acuerdoService.actualizar(acuerdo);
		return "redirect:/acuerdos/cavali";
	}
	//ELIMINAR
	@PostMapping("/eliminar")
	public String eliminar(Model model, @RequestParam(name="id") Integer id) {
		acuerdoService.eliminar(id);		
		return "redirect:/acuerdos/cavali";
	}
	//BUSCAR JIRAS
	@GetMapping("/buscar")
	public String buscador(Model model) {
		model.addAttribute("mensaje","Resultados...");
		return "buscarAcuerdos";
	}
}
