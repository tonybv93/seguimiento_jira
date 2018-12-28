package com.auth.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
public class Acta_detalle {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fabrica_factor_acta_seq")
	@SequenceGenerator(sequenceName="fabrica_factor_acta_seq", allocationSize=1, name="fabrica_factor_acta_seq")
	private Integer id;

	private BigDecimal nro_horas_jira;
	private BigDecimal nro_horas_concepto1;
	private BigDecimal nro_horas_concepto2;
	private BigDecimal nro_total_horas;
	private BigDecimal tarifa;
	private BigDecimal costo_total;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_acta")
	private Acta acta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_hjira")
	private HJira jira;
	
	public Acta_detalle() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getNro_horas_jira() {
		return nro_horas_jira;
	}

	public void setNro_horas_jira(BigDecimal nro_horas_jira) {
		this.nro_horas_jira = nro_horas_jira;
	}

	public BigDecimal getNro_horas_concepto1() {
		return nro_horas_concepto1;
	}

	public void setNro_horas_concepto1(BigDecimal nro_horas_concepto1) {
		this.nro_horas_concepto1 = nro_horas_concepto1;
	}

	public BigDecimal getNro_horas_concepto2() {
		return nro_horas_concepto2;
	}

	public void setNro_horas_concepto2(BigDecimal nro_horas_concepto2) {
		this.nro_horas_concepto2 = nro_horas_concepto2;
	}

	public BigDecimal getNro_total_horas() {
		return nro_total_horas;
	}

	public void setNro_total_horas(BigDecimal nro_total_horas) {
		this.nro_total_horas = nro_total_horas;
	}

	public BigDecimal getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public BigDecimal getCosto_total() {
		return costo_total;
	}

	public void setCosto_total(BigDecimal costo_total) {
		this.costo_total = costo_total;
	}

	public Acta getActa() {
		return acta;
	}

	public void setActa(Acta acta) {
		this.acta = acta;
	}

	public HJira getJira() {
		return jira;
	}

	public void setJira(HJira jira) {
		this.jira = jira;
	}
}
