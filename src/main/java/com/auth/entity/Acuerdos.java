package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
public class Acuerdos implements Comparable<Acuerdos> {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GSOACUERDOS_SEQ")
	@SequenceGenerator(sequenceName="gsoacuerdos_seq", allocationSize=1, name="GSOACUERDOS_SEQ")
	private Integer id;
	
	@Column(name="jira")
	private String jira;
	
	@Column(name="resumen")
	private String jiraDesc;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="responsable")
	private String responsable;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private String fecha_registro;
	
	@Column(name="fecha_limite")
	private String fecha_entrega;
	
	@Column(name="fecha_cierre")
	private String fecha_cierre;
	
	@Column(name="observaciones")
	private String observaciones;
	
	@Column(name="flag_terminado")
	private Integer terminado;
	
	@Column(name="area")
	private String area;
	
	@Column(name="clase")
	private String clase;
	
	@Column(name="coment_final")
	private String observacion;
	
	
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
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
	public String getJiraDesc() {
		return jiraDesc;
	}
	public void setJiraDesc(String jiraDesc) {
		this.jiraDesc = jiraDesc;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public Integer getTerminado() {
		return terminado;
	}
	public void setTerminado(Integer terminado) {
		this.terminado = terminado;
	}
	public String getFecha_cierre() {
		return fecha_cierre;
	}
	public void setFecha_cierre(String fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
	}
	public Acuerdos() {
		super();
	}
	@Override
	public int compareTo(Acuerdos o) {
		if (id < o.id)
			return -1;
		if (id > o.id)
			return 1;				
		return 0;
	}

	
	
}
