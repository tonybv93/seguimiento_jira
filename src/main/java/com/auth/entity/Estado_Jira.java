package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Estado_Jira {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADO_JIRA_seq")
	@SequenceGenerator(sequenceName="ESTADO_JIRA_seq", allocationSize=1, name="ESTADO_JIRA_seq")
	private int id;
	@Column(name="estado")
	private String estado;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_grupo_estado")
	private Grupo_Estado grupoEstado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Grupo_Estado getGrupoEstado() {
		return grupoEstado;
	}
	public void setGrupoEstado(Grupo_Estado grupoEstado) {
		this.grupoEstado = grupoEstado;
	}
	public Estado_Jira() {
	}
}
