package com.auth.service;

import java.util.List;

import com.auth.auxiliar.HorasPorSemana;
import com.auth.entity.Estado_Reg_Horas;
import com.auth.entity.Horas_Gestion_Demanda;
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
	public List<Proveedor_Reg_Horas> listarRegistrosConfirmadosPorDesarrolladorMes(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrosAprobadosPorDesarrollador(Usuario usuario);
	public List<Proveedor_Reg_Horas> listarRegistrisPorJira(String jira);
	public List<Proveedor_Reg_Horas> listarRegistrosPorEstado(Estado_Reg_Horas estado);
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabrica(int id_fabrica);
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabricaEntrePeriodos(int id_fabrica, String fecha1, String fecha2);
	
	public List<Proveedor_Reg_Horas> listarRegistrosPorFabricayEstado(int id_estado, int id_fabrica);
	//----------------- PERIODO
	public List<Periodo> listarPeriodos();
	public Periodo periodoActual();
	//----------------- DESARROLLADOR
	
	//----------------- ESTADOS
	public Estado_Reg_Horas buscarEstadoPorId(int id);
	//----------------- ESPECIALES DESARROLLO
	public String registrarHoras(Usuario u, RespGenerica objeto);	
	public String eliminarHoras(Usuario u, RespGenerica objeto);	
	public String cambiarEstadoRegistro(Proveedor_Reg_Horas registro,int id_estado, Usuario u);
	public List<HorasPorSemana> listarDiasPorSemana(int id_usuario);
	public List<HorasPorSemana> listarDiasdelMes(int id_usuario, String periodo);
	public double horasTrabajadas(String jira);
	
	//----------------- ESPECIALES PRUEBAS
	public String registrarHorasCertificacion(Usuario u, RespGenerica objeto);	
	public String eliminarHorasCertificacion(Usuario u, RespGenerica objeto);	
	
	// -----------------FECHAS
	public String actualizarFechaEntrega(Usuario u, RespGenerica objeto);	
		
	//----------------- TIPO ACTIVIDAD
	public List<Tipo_Actividad_Proveedor> listarTiposActividad();
	public Tipo_Actividad_Proveedor buscarTipoActividadPorID(int id);
	//------------------ HORAS GESTION DEMANDA
	public Horas_Gestion_Demanda buscarHGDemandaPorUsuario(int u, int p);
	
}
