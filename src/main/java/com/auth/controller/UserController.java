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
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Usuario;
import com.auth.service.IUsuarioService;

@Controller
public class UserController {
	@Autowired
	IUsuarioService usuarioService;
	
	// CAMBIAR CONTRASEÑA GET
	@GetMapping("/perfil/password")
	public String cambioPass(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
		model.addAttribute("usuario",usuario);
		return "/usuario/cambio_pass";
	}

	// CAMBIAR CONTRASEÑA POST
	@PostMapping("/perfil/password")
	public String cambioPassPOST(Model model,@RequestParam(name="pass_antigua") String pass_1, @RequestParam(name="pass_nueva") String pass_2,
			@RequestParam(name="pass_nueva2") String pass_3) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
		model.addAttribute("usuario",usuario);
		
		String clase="";
		if (pass_3.equals(pass_2)) {	
			String mensaje = "";
			
			if (usuarioService.cambiarPass(pass_1, pass_2,usuario)) {
				mensaje="Se cambió la contraseña con éxito.";
				clase = "alert alert-fill-success";
			}else {
				mensaje="La contraseña ingresada es incorrecta.";
				clase = "alert alert-fill-danger";
			}
			model.addAttribute("mensaje", mensaje);
		}else {
			clase = "alert alert-fill-danger";
			model.addAttribute("mensaje", "Las contraseñas no coinciden");
		}
		model.addAttribute("clase", clase);
		return "/usuario/cambio_pass";
	}
	
	
	@GetMapping("/home")
	public String raiz(Model model) {	
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
