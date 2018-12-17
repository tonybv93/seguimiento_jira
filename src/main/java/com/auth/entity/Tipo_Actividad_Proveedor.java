package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Tipo_Actividad_Proveedor {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Tipo_Actividad_Proveedor_seq")
	@SequenceGenerator(sequenceName="Tipo_Actividad_Proveedor_seq", allocationSize=1, name="Tipo_Actividad_Proveedor_seq")
	private Integer id;
	
	private String descripcion;		
	private String clase;
	
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
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}

	public Tipo_Actividad_Proveedor() {
		super();
	}
	
}
