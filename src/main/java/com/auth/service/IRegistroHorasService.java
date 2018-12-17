package com.auth.service;

import java.util.List;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Desarrollador;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Horas_X_Jira;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;

public interface IRegistroHorasService {
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros);
	public Proveedor_Reg_Horas guardarRegHoras (Proveedor_Reg_Horas registro);
	public Proveedor_Reg_Horas buscarRegPorID(int id);
	public void eliminarRegistro (int id);
	public List<Proveedor_Reg_Horas> listarRegistrosEnviadosPorDesarrollador(Desarrollador usuario);
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrollador(Desarrollador usuario);
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
	public String confirmarRegistro(Proveedor_Reg_Horas registro);
	public List<HorasPorSemana> listarDiasPorSemana(int id);
	public double horasTrabajadas(String jira);
	//------------------ HX JIRA
	public Horas_X_Jira buscarHXJira(String jira);
}
