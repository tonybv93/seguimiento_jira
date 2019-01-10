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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicInsert
@DynamicUpdate
public class HJira {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HORAS_X_JIRA_SEQ")
	@SequenceGenerator(sequenceName="HORAS_X_JIRA_SEQ", allocationSize=1, name="HORAS_X_JIRA_SEQ")
	private Integer id;
	private String jira;
	private String descripcion;
	private String tipo;
	private String centro_costo;
	private BigDecimal horas_desarrollo;
	private BigDecimal horas_prueba;
	private BigDecimal consumido_prueba;
	private BigDecimal consumido_desarrollo;
	private BigDecimal reserva_desarrollo;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_fabrica")
	private Fabrica fabrica;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_indicador_contable")
	private Indicador_Contable indicador;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	
	public BigDecimal getReserva_desarrollo() {
		return reserva_desarrollo;
	}
	public void setReserva_desarrollo(BigDecimal reserva_desarrollo) {
		this.reserva_desarrollo = reserva_desarrollo;
	}
	public String getCentro_costo() {
		return centro_costo;
	}
	public void setCentro_costo(String centro_costo) {
		this.centro_costo = centro_costo;
	}
	public Fabrica getFabrica() {
		return fabrica;
	}
	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	public Indicador_Contable getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador_Contable indicador) {
		this.indicador = indicador;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public HJira() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getHoras_desarrollo() {
		return horas_desarrollo;
	}
	public void setHoras_desarrollo(BigDecimal horas_desarrollo) {
		this.horas_desarrollo = horas_desarrollo;
	}
	public BigDecimal getHoras_prueba() {
		return horas_prueba;
	}
	public void setHoras_prueba(BigDecimal horas_prueba) {
		this.horas_prueba = horas_prueba;
	}
	public BigDecimal getConsumido_prueba() {
		return consumido_prueba;
	}
	public void setConsumido_prueba(BigDecimal consumido_prueba) {
		this.consumido_prueba = consumido_prueba;
	}
	public BigDecimal getConsumido_desarrollo() {
		return consumido_desarrollo;
	}
	public void setConsumido_desarrollo(BigDecimal consumido_desarrollo) {
		this.consumido_desarrollo = consumido_desarrollo;
	}
	
}
