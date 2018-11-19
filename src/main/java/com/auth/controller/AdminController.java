package com.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Jira;
import com.auth.entity.Jira_Detalle;
import com.auth.service.IJiraService;
import com.auth.service.IJiraDetalleService;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private IJiraService jiraService;
	@Autowired
	private IJiraDetalleService detalleService;
	
	@GetMapping("/fechas")
	public String  administracionCavali(Model model) {

		model.addAttribute("titulo","JIRAS TOTALES");
		
		List<Jira> lstjbd = jiraService.listarJiras();
		
		model.addAttribute("count",lstjbd.size());
		model.addAttribute("lstJiras",lstjbd);
		return "admintable";
	}
	
	@PostMapping("/fechas")
	public String  administracionCavaliGuardar(Model model,@RequestParam(name="id_jirabd") Integer id_jira,@RequestParam(name="fecha1") String fecha1,
			@RequestParam(name="fecha2") String fecha2) {
	
		/*Jira_Detalle jdet = null;		
		Jira jbd = jiraService.buscarPorId(id_jira);
		
		
		if (detalleService.buscarPorJira(jbd.getJira())!= null) {
			jdet = detalleService.buscarPorJira(jbd.getJira());
		}else {
			jdet = new Jira_Detalle();
			jdet.setJira(jbd.getJira());
		}	
		
		jdet.setFecha_produccion(fecha2);
		jdet.setFecha_pruebas(fecha1);
			
		detalleService.guardarNuevo(jdet);
		
		/*
		jbd.setFecha_produccion(fecha2);
		jbd.setFecha_pruebas(fecha1);/
		jiraService.guardar(jbd);*/

		return "redirect:/admin/fechas";
	}
}
