package com.auth.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.entity.Acta;
import com.auth.entity.HJira;
import com.auth.entity.Horas_Gestion_Demanda;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.service.IActaService;
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
	@Autowired
	IActaService actaService;


// -------------- DESARROLLADOR	
	@GetMapping("/desarrollo/historialregistros")
	public String historialRegistros(Model model) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());			
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaRegistrosConfirmados",regHorasService.listarRegistrosConfirmadosPorDesarrollador(usuario));
		model.addAttribute("listaRegistrosAprobados",regHorasService.listarRegistrosAprobadosPorDesarrollador(usuario));
		return "proveedor/historial_registros";
	}
	@GetMapping("/desarrollo/registrohoras")
	public String registrarHoras(Model model) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());			
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaRegistros",regHorasService.listarRegistrosEnviadosPorDesarrollador(usuario));		
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
			regHorasService.cambiarEstadoRegistro(registro, 2, usuario);	
			
		}		
		return "redirect:/proveedor/desarrollo/registrohoras";
	}
	
// ------------ GESTION PROVEEDOR
	@GetMapping("/gestion/aprobacionhoras")
	public String aprobacionHoras(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
		Horas_Gestion_Demanda hgd = regHorasService.buscarHGDemandaPorUsuario(usuario.getId(),regHorasService.periodoActual().getId());
		
		model.addAttribute("fabrica",usuario.getFabrica());
		model.addAttribute("listaDesarrolladores",usuarioService.listarUsuarioPorRolYEmpresa(3,usuario.getFabrica().getId()));// 3: DESARROLLADOR
		model.addAttribute("listaPeriodos",regHorasService.listarPeriodos());
		
		if (usuario.getFabrica().getId() == 21) {// 21: GMD 
			model.addAttribute("gestdem",hgd);
			model.addAttribute("porcentaje",(hgd.getHoras_consumidas().divide(hgd.getTotal_horas(),4,RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)).toString().substring(0, 4) + "%");
		}
		
		return "proveedor/control_horas";
	}
	@GetMapping("/gestion/listaregistros")
	public String listarRegistros(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
		model.addAttribute("titulo","Registros aprobados");
		model.addAttribute("listaRegistrosAprobados",regHorasService.listarRegistrosPorFabricayEstado(1, usuario.getFabrica().getId())); // 1: Aprobados
		return "proveedor/registros_gestion";
	}
	
	@GetMapping("/gestion/registropordesarrollador")
	public String listarRegistrosPorDesarrollador(Model model) {
		model.addAttribute("titulo","Registros por desarrollador");
		return "proveedor/registros_desarrollador";
	}
	
	@PostMapping("/gestion/detalleacta")
	public String detalleActa(Model model, @RequestParam(name="id_acta") int id) {
		Acta acta = actaService.buscarActaPorID(id);
		model.addAttribute("acta",acta);
		model.addAttribute("listaDetalles",actaService.listarDetalle(acta));
		return "proveedor/detalle_acta";
	}
	
	@PostMapping("/gestion/eliminaracta")
	public String eliminarActa(Model model, @RequestParam(name="id_acta") int id) {
		actaService.eliminarActa(id);
		return "redirect:/proveedor/gestion/listaactas";
	}
	
	@GetMapping("/gestion/listaactas")
	public String listarActasEnviadas(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());		
		
		model.addAttribute("listaActas",actaService.listaActasPorFabrica(usuario.getFabrica()));
		return "proveedor/historial_actas";
	}
	
	
	@GetMapping("/gestion/nuevaacta")
	public String nuevaActa(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
		
		model.addAttribute("listaPeriodos",regHorasService.listarPeriodos());
		model.addAttribute("listaEmpresas",actaService.listarEmpresas());
		model.addAttribute("fabrica",usuario.getFabrica());
		return "proveedor/nueva_acta";
	}
	//--------------- FECHAS
	//LISTAR JIRAS POR FABRICA
	@GetMapping("/gestion/fechajiras")
	public String  fechasCavali(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Usuario usuario = usuarioService.buscarPorUsername(auth.getName());	
		List<HJira> lstJiras = jiraService.buscarHjiraPorFabrica(usuario.getFabrica());
		int fla_fab = 0;
		if (usuario.getFabrica().getId() == 22) {//22 = pandora
			fla_fab = 1;
		}else {
			fla_fab = -1;
		}		
		model.addAttribute("flag_pandora",fla_fab);
		model.addAttribute("lstJiras",lstJiras);
		return "proveedor/prov_jiras_update";
	}
}
