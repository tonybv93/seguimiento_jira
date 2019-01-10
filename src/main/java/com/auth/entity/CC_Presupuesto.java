package com.auth.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class CC_Presupuesto implements Serializable {
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_periodo_anual")	
	private Periodo_anual periodoanual;
	
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_centro_costo")	
	private Centro_Costo centrocosto ;
	
	private BigDecimal presupuesto;
	private BigDecimal consumido;
	private boolean flag_activo;

	public CC_Presupuesto() {

	}
	public Periodo_anual getPeriodoanual() {
		return periodoanual;
	}
	public void setPeriodoanual(Periodo_anual periodoanual) {
		this.periodoanual = periodoanual;
	}
	public Centro_Costo getCentrocosto() {
		return centrocosto;
	}
	public void setCentrocosto(Centro_Costo centrocosto) {
		this.centrocosto = centrocosto;
	}
	public BigDecimal getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(BigDecimal presupuesto) {
		this.presupuesto = presupuesto;
	}
	public BigDecimal getConsumido() {
		return consumido;
	}
	public void setConsumido(BigDecimal consumido) {
		this.consumido = consumido;
	}
	public boolean isFlag_activo() {
		return flag_activo;
	}
	public void setFlag_activo(boolean flag_activo) {
		this.flag_activo = flag_activo;
	}	
	
}
