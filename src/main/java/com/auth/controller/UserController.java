package com.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.auth.entity.Usuario;
import com.auth.service.IUsuarioService;

@Controller
public class UserController {
	@Autowired
	IUsuarioService usuarioService;
	
	@GetMapping("/home")
	public String raiz(Model model) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
		model.addAttribute("usurio",usuario);
		return "vista";
	}
	
	@GetMapping("/login")
	public String login() {		
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("nuevousuario",new Usuario());
		return "registro";
	}
	
	@PostMapping("/registro")
	public String guardarUsuario(@Valid Usuario user, BindingResult bindingResult, Model model) {
		
		Usuario existe = usuarioService.buscarPorUsername(user.getUsername());
		if (existe != null) {
			bindingResult.rejectValue("username", "error.user", "Este nombre está en uso!");
		}
		if(bindingResult.hasErrors()) {			
			return "registro";
		}else {
			usuarioService.guardarUsuario(user);
			System.out.println("Agregado correctamente");
			return "redirect:/home";
		}
		
	}
	
	@GetMapping("/acceso_denegado")
	public String accesoDenegado(Model model) {
		model.addAttribute("mensaje","Esta es una zona prohibida");
		return "/errores/acceso_denegado";
	}
}
