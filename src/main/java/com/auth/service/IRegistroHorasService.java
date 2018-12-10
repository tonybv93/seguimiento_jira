package com.auth.service;

import java.util.List;

import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;

public interface IRegistroHorasService {
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros);
	public Proveedor_Reg_Horas guardarRegHoras (Proveedor_Reg_Horas registro);
	public void eliminarRegistro (Proveedor_Reg_Horas registro);
	public List<Proveedor_Reg_Horas> listarRegistrosPorUsuario(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira);
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado);
	//----------------- PERIODO
	public List<Periodo> listarPeriodos();

}
