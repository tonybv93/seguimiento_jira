package com.auth.service;

import java.util.List;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.HJira;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Tipo_Actividad_Proveedor;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;

public interface IRegistroHorasService {
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros);
	public Proveedor_Reg_Horas guardarRegHoras (Proveedor_Reg_Horas registro);
	public Proveedor_Reg_Horas buscarRegPorID(int id);
	public void eliminarRegistro (int id);
	public List<Proveedor_Reg_Horas> listarRegistrosEnviadosPorDesarrollador(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrollador(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrosAprobadosPorDesarrollador(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira);
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado);
	//----------------- PERIODO
	public List<Periodo> listarPeriodos();
	//----------------- DESARROLLADOR
	
	//----------------- ESTADOS
	public Estado_Reg_Horas buscarEstadoPorId(int id);
	//----------------- ESPECIALES
	public String registrarHoras(Usuario u, RespGenerica objeto);
	public String eliminarHoras(Usuario u, RespGenerica objeto);
	public String cambiarEstadoRegistro(Proveedor_Reg_Horas registro,int id_estado);
	public List<HorasPorSemana> listarDiasPorSemana(int id_usuario);
	public double horasTrabajadas(String jira);
	//------------------ HX JIRA	
	public HJira buscarHXJiraXFab(String jira, String fabrica);
	//----------------- TIPO ACTIVIDAD
	public List<Tipo_Actividad_Proveedor> listarTiposActividad();
	public Tipo_Actividad_Proveedor buscarTipoActividadPorID(int id);
}
