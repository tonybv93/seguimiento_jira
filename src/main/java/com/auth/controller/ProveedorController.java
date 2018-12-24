package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.service.IJiraService;
import com.auth.service.IRegistroHorasService;
import com.auth.service.IUsuarioService;
@Controller
@RequestMapping("/proveedor")
public class ProveedorController {
	@Autowired
	IJiraService jiraService;
	@Autowired
	IRegistroHorasService regHorasService;
	@Autowired
	IUsuarioService usuarioService;	

// -------------- DESARROLLADOR	
	@GetMapping("/desarrollo/registrohoras")
	public String registrarHoras(Model model) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaRegistros",regHorasService.listarRegistrosEnviadosPorDesarrollador(usuario));
		model.addAttribute("listaRegistrosConfirmados",regHorasService.listarRegistrosConfirmadosPorDesarrollador(usuario));
		model.addAttribute("listaRegistrosAprobados",regHorasService.listarRegistrosAprobadosPorDesarrollador(usuario));
		model.addAttribute("listaTiposRegistro",regHorasService.listarTiposActividad());
		return "proveedor/registro_horas";
	}
	@PostMapping("/desarrollo/registrohoras")
	public String registrarHorasPost(@RequestParam(name="id_hora") int id) {
		//VALIDACIÓN
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			
		Proveedor_Reg_Horas registro = regHorasService.buscarRegPorID(id);
				
		if (registro.getUsuario() == usuario) {				 
			regHorasService.cambiarEstadoRegistro(registro, 2);			
		}
		return "redirect:/proveedor/desarrollo/registrohoras";
	}
// ------------ GESTION PROVEEDOR
	@GetMapping("/gestion/aprobacionhoras")
	public String aprobacionHoras(Model model) {
		model.addAttribute("listaDesarrolladores",usuarioService.listarUsuarioPorRol(3));
		model.addAttribute("listaRegistros","");
		return "proveedor/control_horas";
	}
}
