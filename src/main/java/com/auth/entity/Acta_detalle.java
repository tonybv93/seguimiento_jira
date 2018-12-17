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
	
	private String jira;
	private BigDecimal total_horas;
	private BigDecimal costo_jira;
	private BigDecimal costo_concepto;
	private BigDecimal costo_jira_toral;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_acta")
	private Acta acta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJira() {
		return jira;
	}

	public void setJira(String jira) {
		this.jira = jira;
	}

	public BigDecimal getTotal_horas() {
		return total_horas;
	}

	public void setTotal_horas(BigDecimal total_horas) {
		this.total_horas = total_horas;
	}

	public BigDecimal getCosto_jira() {
		return costo_jira;
	}

	public void setCosto_jira(BigDecimal costo_jira) {
		this.costo_jira = costo_jira;
	}

	public BigDecimal getCosto_concepto() {
		return costo_concepto;
	}

	public void setCosto_concepto(BigDecimal costo_concepto) {
		this.costo_concepto = costo_concepto;
	}

	public BigDecimal getCosto_jira_toral() {
		return costo_jira_toral;
	}

	public void setCosto_jira_toral(BigDecimal costo_jira_toral) {
		this.costo_jira_toral = costo_jira_toral;
	}

	public Acta getActa() {
		return acta;
	}

	public void setActa(Acta acta) {
		this.acta = acta;
	}

	public Acta_detalle() {
		super();
	}
	
	
	
}
