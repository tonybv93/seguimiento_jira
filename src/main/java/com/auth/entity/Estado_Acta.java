package com.auth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
public class Estado_Acta {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_acta_seq")
	@SequenceGenerator(sequenceName="estado_acta_seq", allocationSize=1, name="estado_acta_seq")
	private Integer id;
	
	private String estado;		
	private String css_clase;
	private boolean flagactivo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean isFlagactivo() {
		return flagactivo;
	}
	public void setFlagactivo(boolean flagactivo) {
		this.flagactivo = flagactivo;
	}
	public String getCss_clase() {
		return css_clase;
	}
	public void setCss_clase(String css_clase) {
		this.css_clase = css_clase;
	}
	public Estado_Acta() {

	}
}
