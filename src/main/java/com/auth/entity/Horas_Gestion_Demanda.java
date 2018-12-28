package com.auth.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Horas_Gestion_Demanda {
	@Id
	@Column(name="ID")
	private Integer id;
	private BigDecimal total_horas;
	private BigDecimal horas_consumidas;
	private BigDecimal factor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_periodo")
	private Periodo periodo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getTotal_horas() {
		return total_horas;
	}

	public void setTotal_horas(BigDecimal total_horas) {
		this.total_horas = total_horas;
	}

	public BigDecimal getHoras_consumidas() {
		return horas_consumidas;
	}

	public void setHoras_consumidas(BigDecimal horas_consumidas) {
		this.horas_consumidas = horas_consumidas;
	}

	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Horas_Gestion_Demanda() {

	}
	
}

