package com.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Horas_X_Jira;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;
import com.auth.service.IJiraService;
import com.auth.service.IRegistroHorasService;
import com.auth.service.IUsuarioService;

@RestController
@RequestMapping("/provrest")
public class ProvRestController {
	@Autowired
	private IUsuarioService usuarioService;	
	@Autowired
	private IRegistroHorasService registroService;
	@Autowired
	private IJiraService jiraService;
// -------------------------------------------------- REGISTRO DE HORAS ----------------------------

		@PostMapping("/registro/nuevo")
		public String registrarHoras (@RequestBody RespGenerica jsonAcuerdo) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());			
			return registroService.registrarHoras(usuario, jsonAcuerdo);
		}
		
		@PostMapping("/registro/eliminar")
		public String eliminarRegistro(@RequestBody RespGenerica respuesta) {			
			//VALIDACIÓN
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
			return registroService.eliminarHoras(usuario, respuesta);
		}
		
		@PostMapping("/registro/cambiarestado")
		public String cambiarEstadoRegistro(@RequestBody RespGenerica respuesta) {				
			//VALIDACIÓN
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			//Encontrar registro
			Proveedor_Reg_Horas registro = registroService.buscarRegPorID((int)respuesta.getNumero1());		
			if (registro.getUsuario() == usuario) {				 
				return registroService.cambiarEstadoRegistro(registro,(int) respuesta.getNumero2());
			}else {
				return "Error, no puede confirmar registros de otras personas";
			}	
		}	
		
		
		// Lista de actividad diaria por Desarrollador [GRÁFICO DE BARRAS]
		@PostMapping("/horas/semana")
		@ResponseBody
		public List<HorasPorSemana> listaHorasDiarias(){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			return registroService.listarDiasPorSemana(usuario.getId());
		}
		
		// Lista de actividad diaria por Desarrollador [GRÁFICO DE BARRAS]
		@PostMapping("/horas/semana/desarrollador")
		@ResponseBody
		public List<HorasPorSemana> listarHorasDiariasPorDesarrollador(@RequestBody RespGenerica respuesta){
			return registroService.listarDiasPorSemana((int) respuesta.getNumero1());
		}
		
		// 
		@GetMapping("/horastrabajadas/{jira}")
		@ResponseBody
		public double horasTrabajadasPorJira(@PathVariable(name="jira") String jira) {
			return registroService.horasTrabajadas(jira);
		}
		
		// Buscar HORAS X JIRAS
		@GetMapping("/hxjira/{jira}")
		@ResponseBody
		public Horas_X_Jira buscarHXJira(@PathVariable(name="jira") String jira){
			return registroService.buscarHXJira(jira);
		}
		
		//BUSCAR JIRAS PARA ACTA
		@GetMapping("/buscar/jira/{str}")
		@ResponseBody
		public List<Horas_X_Jira> buscarJirasPersonalizado(@PathVariable(name="str") String str){
			List<Horas_X_Jira> respuesta =  jiraService.BuscadorPersonalizado(str);
			return respuesta;
		}


}
