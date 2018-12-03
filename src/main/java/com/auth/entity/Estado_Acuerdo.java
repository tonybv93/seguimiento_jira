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
	private String css_clase;

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
	public String getCss_clase() {
		return css_clase;
	}
	public void setCss_clase(String css_clase) {
		this.css_clase = css_clase;
	}
	
}
