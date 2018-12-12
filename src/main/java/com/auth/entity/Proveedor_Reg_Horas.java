package com.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
public class Proveedor_Reg_Horas {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acta_seq")
	@SequenceGenerator(sequenceName="acta_seq", allocationSize=1, name="acta_seq")
	private Integer id;

	private String jira;
	private String tipo;
	private String resumen;
	
	private Date fecha_registro;	
	private Date fecha_real_trabajo;
	private long nro_horas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_desarrollador")
	private Desarrollador desarrollador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado_reg_horas")
	private Estado_Reg_Horas estado;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Desarrollador getDesarrollador() {
		return desarrollador;
	}

	public void setDesarrollador(Desarrollador desarrollador) {
		this.desarrollador = desarrollador;
	}

	public String getJira() {
		return jira;
	}

	public void setJira(String jira) {
		this.jira = jira;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Date getFecha_real_trabajo() {
		return fecha_real_trabajo;
	}

	public void setFecha_real_trabajo(Date fecha_real_trabajo) {
		this.fecha_real_trabajo = fecha_real_trabajo;
	}

	public long getNro_horas() {
		return nro_horas;
	}

	public void setNro_horas(long nro_horas) {
		this.nro_horas = nro_horas;
	}

	public Estado_Reg_Horas getEstado() {
		return estado;
	}

	public void setEstado(Estado_Reg_Horas estado) {
		this.estado = estado;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Proveedor_Reg_Horas() {
		super();
	}
	
	
	
	
}
