package com.auth.controller;

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
	
	//NUEVO ACUERDO (PANEL ADMIN)
	@GetMapping("/nuevoacuerdo")
	public String acuerdosCavali(Model model) {	
		model.addAttribute("acuerdonuevo",new Acuerdos());
		model.addAttribute("listaJiras",jiraService.listarJiras());
		model.addAttribute("listaTipos",acuerdoService.listarTiposAcuerdo());
		model.addAttribute("listaAreas",acuerdoService.listarAreaSolicitante());
		model.addAttribute("listaResponsables",acuerdoService.listarUsuarios());
		model.addAttribute("listaAcuerdos", acuerdoService.listarTodos());
		return "acuerdos";
	}
	
	//LISTAR ACUERDOS
	@GetMapping("/cavaliacuerdos")
	public String listaAcuerdosCavali(Model model) {	
		model.addAttribute("listaAcuerdos", acuerdoService.listarActivos());
		return "acuerdoslista";
	}
		
	//ELIMINAR 
	@PostMapping("/eliminar")
	public String eliminar(Model model, @RequestParam(name="id") Integer id) {
		acuerdoService.eliminar(id);			
		return "redirect:/acuerdos/cavali";
	}
}
