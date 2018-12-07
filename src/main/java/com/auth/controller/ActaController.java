package com.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/actas")
public class ActaController {
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
}
