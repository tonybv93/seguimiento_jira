package com.auth.service;

import java.util.List;

import com.auth.auxiliar.DetalleActaPre;
import com.auth.entity.Acta;
import com.auth.entity.Empresa;
import com.auth.entity.Fabrica;
import com.auth.entity.Indicador_Contable;
import com.auth.entity.Periodo;
import com.auth.entity.Acta_Estado;
import com.auth.entity.Usuario;
import com.auth.rest.RespGenerica;

public interface IActaService {
	//----- ACTA
	public List<Acta> listarTodoActas();
	public List<Acta> listaActasPorUsuario(Usuario usuario);
	public List<Acta> listaActasPorEmpresa(Empresa empresa);
	public List<Acta> listaActasPorIndicador(Indicador_Contable indicador);
	public List<Acta> listaActasPorEstado(Acta_Estado estado);
	public Acta buscarActaPorID(int id);
	public Acta buscarActaPorCodigo(String codigo);
	public Acta guardarActa(Acta a);
	public void desactivarActa(Acta a);
		
	//----- ESTADO_ACTA
	public List<Acta_Estado> listarEstadoActa();
	//----- indicador acta
	public Indicador_Contable buscarIndicadorPorId(int id);
	public List<Indicador_Contable> listarIndicadorContable();
	//----- COMENTARIO
	//----- PERIODO
	public List<Periodo> listarPeriodos();
	public Periodo buscarPeriodo(int id);
	//----- EMPRESA
	public List<Empresa> listarEmpresas();
	public Empresa buscarEmpresa(int id);
	//----- ESPECIALES
	public List<DetalleActaPre> listarDetalleActaPRE(Usuario u, RespGenerica respuesta);
	public Acta registrarNuevaActa(RespGenerica respuesta);
	//----- FABRICA
	public List<Fabrica> listarFabricas();
	public Fabrica buscarFabricaPorID(int id);
	 
}
