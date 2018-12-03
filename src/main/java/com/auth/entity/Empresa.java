package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Empresa {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPRESA_SEQ")
	@SequenceGenerator(sequenceName="EMPRESA_SEQ", allocationSize=1, name="EMPRESA_SEQ")
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="nombrecorto")
	private String nombrecorto;
	
	@Column(name="css_class")
	private String css_class;
	
	@Column(name="icon")
	private String icon;
	
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
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
	
	public String getNombrecorto() {
		return nombrecorto;
	}
	public void setNombrecorto(String nombrecorto) {
		this.nombrecorto = nombrecorto;
	}
	public String getCss_class() {
		return css_class;
	}
	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}
	public Empresa() {
	}
}
