package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="fabrica")
public class Fabrica {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FABRICA_seq")
	@SequenceGenerator(sequenceName="FABRICA_seq", allocationSize=1, name="FABRICA_seq")
	private int id;
	private String nombre;
	private Double tarifa;
	
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
	public Double getTarifa() {
		return tarifa;
	}
	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}
	public Fabrica() {
		super();
	}
}
