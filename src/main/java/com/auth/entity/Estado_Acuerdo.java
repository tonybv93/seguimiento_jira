package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Estado_Acuerdo {
	@Id
	@Column(name="ID")
	private int id;
	private String nombre;
	private String css_class;

	public Estado_Acuerdo() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCss_class() {
		return css_class;
	}
	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}
}
