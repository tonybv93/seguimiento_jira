package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Etiqueta {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETIQUETA_seq")
	@SequenceGenerator(sequenceName="ETIQUETA_seq", allocationSize=1, name="ETIQUETA_seq")
	private int id;
	private String nombre;
	private String css_class;
	
	public Etiqueta() {
		super();
	}
	
	public String getCss_class() {
		return css_class;
	}

	public void setCss_class(String css_class) {
		this.css_class = css_class;
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
}
