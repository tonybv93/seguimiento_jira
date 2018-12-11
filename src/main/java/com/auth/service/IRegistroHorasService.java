package com.auth.service;

import java.util.List;

import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Periodo;
import com.auth.entity.Proveedor_Reg_Horas;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;

public interface IRegistroHorasService {
	public void guardarRegistros(List<Proveedor_Reg_Horas> listaRegistros);
	public Proveedor_Reg_Horas guardarRegHoras (Proveedor_Reg_Horas registro);
	public void eliminarRegistro (Proveedor_Reg_Horas registro);
	public List<Proveedor_Reg_Horas> listarRegistrosPorUsuario(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira);
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado);
	//----------------- PERIODO
	public List<Periodo> listarPeriodos();
	//----------------- DESARROLLADOR
	
	//----------------- ESTADOS
	public Estado_Reg_Horas buscarPorId(int id);
	//----------------- ESPECIALES
	public String registrarHoras(Usuario u, RespGenerica objeto);

}
