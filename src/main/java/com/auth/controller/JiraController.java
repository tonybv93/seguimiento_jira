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
	private IAcuerdosService acuerdoService;
	@Autowired
	private IJiraService jiraService;
	
	// ACTUALIZAR BD
	@GetMapping("/actualizar")
	public String  actualizar(Model model) {	
		jiraService.actualizarBD();
		return "redirect:/seguimiento/cavalilista";
	}
	
	//LISTAR JIRAS CAVALI
	@GetMapping("/cavalilista")
	public String  detalleCavali(Model model) {		
		List<Jira> lstJiras = jiraService.listarJirasPorEmpresa(1);		
		model.addAttribute("listaAreas",acuerdoService.listarAreaSolicitante());
		model.addAttribute("count",lstJiras.size());
		model.addAttribute("lstJiras",lstJiras);
		model.addAttribute("numtotal",jiraService.jirasTotales()[5]);
		return "user_seguimiento";
	}	
	//CHART 
	@GetMapping("/cavalichart")
	public String cavaliChart() {
		return "chart";
	}
	//LISTAR JIRAS BOLSA
		@GetMapping("/bolsalista")
		public String  detalleBVL(Model model) {		
			List<Jira> lstJiras = jiraService.listarJirasPorEmpresa(2);	
			model.addAttribute("listaAreas",acuerdoService.listarAreaSolicitante());
			model.addAttribute("count",lstJiras.size());
			model.addAttribute("lstJiras",lstJiras);
			return "user_seguimiento";
		}
	//LISTAR JIRAS CAVALI
	@GetMapping("/fechas")
	public String  fechasCavali(Model model) {		
		List<Jira> lstJiras = jiraService.listarJiras();
		model.addAttribute("listaAreas",acuerdoService.listarAreaSolicitante());
		model.addAttribute("count",lstJiras.size());
		model.addAttribute("lstJiras",lstJiras);
		return "adm_jirasUpdate";
	}
}
