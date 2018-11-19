package com.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.auth.entity.Jira;
import com.auth.service.IAcuerdosService;
import com.auth.service.IJiraService;

@Controller
@RequestMapping("/seguimiento")
public class JiraController {

	@Autowired
	private IJiraService jiraService;
	@Autowired
	private IAcuerdosService acuerdoService;
	
	// ACTUALIZAR BD
	@GetMapping("/actualizar")
	public String  actualizar(Model model) {	
		jiraService.actualizarBD();
		return "redirect:/gso/cavali/lista";
	}
	
	//LISTAR JIRAS
	@GetMapping("/cavali/lista")
	public String  detalleCavali(Model model) {		
		List<Jira> lstJiras = jiraService.listarJiras();		
		model.addAttribute("count",lstJiras.size());
		model.addAttribute("lstJiras",lstJiras);
		return "jiralista";
	}	
	
	//LISTAR ACUERDOS
	@GetMapping("/cavali/acuerdos")
	public String listaAcuerdosCavali(Model model) {	
		model.addAttribute("listaAcuerdos", acuerdoService.listarActivos());
		return "acuerdoslista";
	}
}
