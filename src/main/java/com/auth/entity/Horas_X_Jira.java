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
public class Horas_X_Jira {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HORAS_X_JIRA_SEQ")
	@SequenceGenerator(sequenceName="HORAS_X_JIRA_SEQ", allocationSize=1, name="HORAS_X_JIRA_SEQ")
	private Integer id;
	private String jira;
	private String descripcion;
	private String tipo;
	private double horas_desarrollo;
	private double horas_prueba;
	private double consumido_prueba;
	private double consumido_desarrollo;
	
	
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
	public Horas_X_Jira() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getHoras_desarrollo() {
		return horas_desarrollo;
	}
	public void setHoras_desarrollo(double horas_desarrollo) {
		this.horas_desarrollo = horas_desarrollo;
	}
	public double getHoras_prueba() {
		return horas_prueba;
	}
	public void setHoras_prueba(double horas_prueba) {
		this.horas_prueba = horas_prueba;
	}
	public double getConsumido_prueba() {
		return consumido_prueba;
	}
	public void setConsumido_prueba(double consumido_prueba) {
		this.consumido_prueba = consumido_prueba;
	}
	public double getConsumido_desarrollo() {
		return consumido_desarrollo;
	}
	public void setConsumido_desarrollo(double consumido_desarrollo) {
		this.consumido_desarrollo = consumido_desarrollo;
	}
}
