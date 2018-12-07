package com.auth.service;

import java.util.List;

import com.auth.entity.Acta;
import com.auth.entity.Empresa;
import com.auth.entity.Acta_Estado;
import com.auth.entity.Acta_Tipo;
import com.auth.entity.Usuario;

public interface IActaService {
	//----- ACTA
	public List<Acta> listarTodoActas();
	public List<Acta> listaActasPorUsuario(Usuario usuario);
	public List<Acta> listaActasPorUsuario(Empresa usuario);
	public List<Acta> listaActasPorUsuario(Acta_Tipo usuario);
	public List<Acta> listaActasPorUsuario(Acta_Estado usuario);
	public Acta buscarActaPorID(int id);
	public Acta buscarActaPorCodigo(String codigo);
	public Acta guardarActa();
	public void desactivarActa();
		
	//----- ESTADO_ACTA
	public List<Acta_Estado> listarEstadoActa();
	//----- TIPO_ACTA
	//----- COMENTARIO
	//----- PERIODO

}
