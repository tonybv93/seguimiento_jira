package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth.service.IJiraService;
import com.auth.service.IRegistroHorasService;

@Controller
@RequestMapping("/actas")
public class ActaController {
	@Autowired
	IJiraService jiraService;
	@Autowired
	IRegistroHorasService regHorasService;
	
	
	@GetMapping("nueva")
	public String  actualizar(Model model) {	
		return "actas/registro_acta";
	}
	
	@GetMapping("/{id}")
	public String  detalle() {	
		return "actas/detalle_acta";
	}

	@GetMapping("/lista")
	public String listarActas() {
		return "actas/lista_acta";
	}
	
	@GetMapping("/registro/horas")
	public String registrarHoras(Model model) {
		model.addAttribute("listaJiras",jiraService.listarJiras());		
		model.addAttribute("listaPeriodos",regHorasService.listarPeriodos());
		return "actas/registro_horas";
	}
}
