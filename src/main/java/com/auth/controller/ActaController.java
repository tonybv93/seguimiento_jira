package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth.entity.Desarrollador;
import com.auth.entity.Usuario;
import com.auth.service.IDesarrolladorService;
import com.auth.service.IJiraService;
import com.auth.service.IRegistroHorasService;
import com.auth.service.IUsuarioService;
@Controller
@RequestMapping("/actas")
public class ActaController {
	@Autowired
	IJiraService jiraService;
	@Autowired
	IRegistroHorasService regHorasService;
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	IDesarrolladorService desarrolladorService;
	
	
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
		Desarrollador desarrollador = desarrolladorService.buscarPorUsuario(usuario);				
		model.addAttribute("listaJiras",jiraService.listarJiras());	
		model.addAttribute("listaJiras",jiraService.listarJiras());	
		model.addAttribute("listaPeriodos",regHorasService.listarPeriodos());
		model.addAttribute("desarrollador",desarrollador);
		model.addAttribute("listaRegistros",regHorasService.listarRegistrosEnviadosPorDesarrollador(desarrollador));
		return "actas/registro_horas";
	}
}
