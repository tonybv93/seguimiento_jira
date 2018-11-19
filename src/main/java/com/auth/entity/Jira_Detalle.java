package com.auth.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Jira_Detalle {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIRA_DETALLE_SEQ")
	@SequenceGenerator(sequenceName="JIRA_DETALLE_SEQ", allocationSize=1, name="JIRA_DETALLE_SEQ")
	private int id;
	
	@OneToOne
	@JoinColumn(name="id_jira",unique=true, updatable=false)
	private Jira jira;
	
	@Column(name="fecha_pr_usuario")
	private Date fecha_pr_usuario;
	
	@Column(name="fecha_produccion")
	private Date fecha_produccion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jira getJira() {
		return jira;
	}

	public void setJira(Jira jira) {
		this.jira = jira;
	}
	

	public Date getFecha_pr_usuario() {
		return fecha_pr_usuario;
	}

	public void setFecha_pr_usuario(Date fecha_pr_usuario) {
		this.fecha_pr_usuario = fecha_pr_usuario;
	}

	public Date getFecha_produccion() {
		return fecha_produccion;
	}

	public void setFecha_produccion(Date fecha_produccion) {
		this.fecha_produccion = fecha_produccion;
	}
	public Jira_Detalle() {
		super();
	}	
}
