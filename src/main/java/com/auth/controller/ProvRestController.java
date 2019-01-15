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

import com.auth.auxiliar.DetalleActaPre;
import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.HJira;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Rol;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;
import com.auth.service.IActaService;
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
	@Autowired
	private IActaService actaService;
//--------------------------------------------------- REGISTRO DE ACTA ----------------------------
		//CREAR UNA NUEVA ACTA
		@PostMapping("/acta/nueva")
		@ResponseBody
		public Integer registrarActaNueva(@RequestBody RespGenerica respuesta){			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			return actaService.registrarNuevaActa(respuesta,usuario).getId();
		}
		// Lista pre de jiras por acta
		@PostMapping("/acta/jiraspre")
		@ResponseBody
		public List<DetalleActaPre> listaJirasPreActa(@RequestBody RespGenerica respuesta){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());						
			return actaService.listarDetalleActaPRE(usuario,respuesta);
		}
		// Confirmar envio acta
		@PostMapping("/acta/confirmarenvio")
		@ResponseBody
		public String confirmarEnvioActa(@RequestBody RespGenerica respuesta){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());			
			actaService.cambiarEstadoActa(respuesta, usuario, 2); // ESTADO 2: Enviado
			return "Hecho!";
		}
// -------------------------------------------------- REGISTRO DE HORAS ----------------------------

		@PostMapping("/registro/nuevo")
		public String registrarHoras (@RequestBody RespGenerica jsonAcuerdo) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
			if (usuario.getFabrica().getId() == 22)  { // 22: PANDORA
				return registroService.registrarHorasCertificacion(usuario, jsonAcuerdo);
			}else {
				return registroService.registrarHoras(usuario, jsonAcuerdo);
			}			
		}
		
		@PostMapping("/registro/eliminar")
		public String eliminarRegistro(@RequestBody RespGenerica respuesta) {			
			//VALIDACIÓN
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
			if (usuario.getFabrica().getId() == 22)  { // 22: PANDORA
				return registroService.eliminarHorasCertificacion(usuario, respuesta);
			}else{
				return registroService.eliminarHoras(usuario, respuesta);
			}
		}
		
		@PostMapping("/registro/cambiarestado")
		public String cambiarEstadoRegistro(@RequestBody RespGenerica respuesta) {				
			//VALIDACIÓN
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			//ENCONTRAR REGISTRO
			Proveedor_Reg_Horas registro = registroService.buscarRegPorID((int)respuesta.getNumero1());		
			Rol rol = usuarioService.buscarRolPorId(4); //SI EL ROL ES JEFE DE PROVEEDORES, SI PUEDE CAMBIAR ESTADO DE OTRAS PERSONAS
			if (registro.getUsuario() == usuario || usuario.getRoles().contains(rol)) {				 
				return registroService.cambiarEstadoRegistro(registro,(int) respuesta.getNumero2(), usuario);
			}else {
				return "Error, no puede confirmar registros de otras personas";
			}	
		}	
// -------------------------------------------------- EXCEL DE HORAS ----------------------------		
		@PostMapping("/horasporfabrica/entrefechas")
		@ResponseBody
		public List<Proveedor_Reg_Horas> listarHorasPorFabricaEntreFechas(@RequestBody RespGenerica respuesta){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
			return registroService.listarRegistrosPorFabricaEntrePeriodos(usuario.getFabrica().getId(), respuesta.getTexto1(), respuesta.getTexto2());
		}
// -------------------------------------------------- GRÁFICAS DE HORAS ----------------------------
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
		// Lista de actividad diaria por Desarrollador [GRÁFICO DE BARRAS]
		@PostMapping("/horas/mes/desarrollador")
		@ResponseBody
		public List<HorasPorSemana> listarHorasMesPorDesarrollador(@RequestBody RespGenerica respuesta){
			return registroService.listarDiasdelMes((int) respuesta.getNumero1(), respuesta.getTexto1());
		}
				
		// Lista de actividad diaria por Desarrollador [REGISTROS]
		@PostMapping("/registros/semana/desarrollador")
		@ResponseBody
		public List<Proveedor_Reg_Horas> listarRegistrosDiariosPorDesarrollador(@RequestBody RespGenerica respuesta){				
			Usuario usuario = usuarioService.buscarPorId((int)respuesta.getNumero1());	
			return registroService.listarRegistrosConfirmadosPorDesarrollador(usuario);
		}
		
		// Lista de actividad diaria por Desarrollador [REGISTROS]
		@PostMapping("/registros/aprobados/desarrollador")
		@ResponseBody
		public List<Proveedor_Reg_Horas> listarRegistrosAprobadosPorDesarrollador(@RequestBody RespGenerica respuesta){					
			Usuario usuario = usuarioService.buscarPorId((int)respuesta.getNumero1());	
			return registroService.listarRegistrosAprobadosPorDesarrollador(usuario);
		}
		
		
		// Lista de actividad diaria por Desarrollador [REGISTROS]
		@PostMapping("/registros/mes/desarrollador")
		@ResponseBody
		public List<Proveedor_Reg_Horas> listarRegistrosMesPorDesarrollador(@RequestBody RespGenerica respuesta){					
			Usuario usuario = usuarioService.buscarPorId((int)respuesta.getNumero1());	
			return registroService.listarRegistrosConfirmadosPorDesarrolladorMes(usuario);
		}
		
		// 
		@GetMapping("/horastrabajadas/{jira}")
		@ResponseBody
		public double horasTrabajadasPorJira(@PathVariable(name="jira") String jira) {
			return registroService.horasTrabajadas(jira);
		}
		
		// Buscar HORAS X JIRAS X FABRICA [DONUT]
		@GetMapping("/hxjiraxfab/{jira}")
		@ResponseBody
		public HJira buscarHXJiraXFab(@PathVariable(name="jira") String jira){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());			
			String fabrica = usuario.getFabrica().getNombre(); 
			return jiraService.buscarHXJiraXFab(jira,fabrica);
		}
		
		//BUSCAR JIRAS PARA ACTA
		@GetMapping("/buscarxfab/jira/{str}")
		@ResponseBody
		public List<HJira> buscarJirasPersonalizadoPorFabrica(@PathVariable(name="str") String str){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
			String fabrica = usuario.getFabrica().getNombre();
			List<HJira> respuesta =  jiraService.buscarJiraPorFabrica(str, fabrica);
			return respuesta;
		}
		
		//LISTA DE REGISTROS PARA APROBAR
		@PostMapping("/registros/poraprobar")
		@ResponseBody
		public List<Proveedor_Reg_Horas> listaRegistrosPorAprobar(@RequestBody RespGenerica respuesta){
			Usuario u = usuarioService.buscarPorId((int)respuesta.getNumero1());
			return registroService.listarRegistrosAprobadosPorDesarrollador(u);
		}
		
		// -------------------------------------------------- GRÁFICAS DE HORAS ----------------------------
		@PostMapping("/jiras/fechas")
		@ResponseBody
		public String fechaJirasProveedor(@RequestBody RespGenerica respuesta){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
			return registroService.actualizarFechaEntrega(usuario, respuesta);
		}


}
