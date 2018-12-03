package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tipo_Acuerdo {
	@Id
	@Column(name="ID")
	private int id;
	private String nombre;
	private String css_class;
	private String icon_ind_contable;
	
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
	
	public String getIcon_ind_contable() {
		return icon_ind_contable;
	}
	public void setIcon_ind_contable(String icon_ind_contable) {
		this.icon_ind_contable = icon_ind_contable;
	}
	public Tipo_Acuerdo() {
		super();
	}
}
