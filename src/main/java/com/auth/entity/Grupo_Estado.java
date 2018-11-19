package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grupo_Estado {
	@Id
	@Column(name="ID")
	private int id;
	private String grupoEstado;
	private String css_class;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrupoEstado() {
		return grupoEstado;
	}
	public void setGrupoEstado(String grupoEstado) {
		this.grupoEstado = grupoEstado;
	}
	public String getCss_class() {
		return css_class;
	}
	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}
	public Grupo_Estado() {
		super();
	}
}
