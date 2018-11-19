package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Area_Solicitante {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_solicitante_seq")
	@SequenceGenerator(sequenceName="area_solicitante_seq", allocationSize=1, name="area_solicitante_seq")
	private Integer id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="nombre_visual")
	private String nombre_visual;
	@Column(name="css_class")
	private String css_class;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre_visual() {
		return nombre_visual;
	}
	public void setNombre_visual(String nombre_visual) {
		this.nombre_visual = nombre_visual;
	}
	public String getCss_class() {
		return css_class;
	}
	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}
	public Area_Solicitante() {
		super();
	}
}
