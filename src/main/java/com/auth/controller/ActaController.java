package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Acta;
import com.auth.service.IActaService;

@Controller
@RequestMapping("/acta")
public class ActaController {
	@Autowired
	IActaService actaService;
	
	@GetMapping("/actasporabrobar")
	public String listarActas(Model model) {
		model.addAttribute("titulo","Actas pendientes de aprobación");
		model.addAttribute("listaActas",actaService.listaActasPorEstado(2));
		return "/actaslista";
	}
	@PostMapping("/actadetalle")
	public String detalleActa(Model model, @RequestParam(name="id_acta") int id_acta) {
		Acta acta = actaService.buscarActaPorID(id_acta);
		model.addAttribute("acta",acta);
		model.addAttribute("listaDetalles",actaService.listarDetalle(acta));
		return "/actadetalle";
	}
	
}
