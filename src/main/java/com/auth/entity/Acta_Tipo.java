package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Acta_Tipo {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_acta_seq")
	@SequenceGenerator(sequenceName="tipo_acta_seq", allocationSize=1, name="tipo_acta_seq")
	private Integer id;
	private String descripcion;	
	private String css_clase;	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCss_clase() {
		return css_clase;
	}
	public void setCss_clase(String css_clase) {
		this.css_clase = css_clase;
	}
	public Acta_Tipo() {
	}
}
