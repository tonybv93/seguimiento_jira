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

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	private Date fecha_registro;	
	private Date fecha_real_trabajo;
	private Date fecha_facturacion; 
	private double nro_horas;
	private boolean flagfacturar;
	private String comentario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_tipo_actividad_prov")
	private Tipo_Actividad_Proveedor tipoActividad;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_hjira")
	private HJira hjira;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado_reg_horas")
	private Estado_Reg_Horas estado;

	
	public Date getFecha_facturacion() {
		return fecha_facturacion;
	}

	public void setFecha_facturacion(Date fecha_facturacion) {
		this.fecha_facturacion = fecha_facturacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public HJira getHjira() {
		return hjira;
	}

	public void setHjira(HJira hjira) {
		this.hjira = hjira;
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

	public double getNro_horas() {
		return nro_horas;
	}

	public void setNro_horas(double nro_horas) {
		this.nro_horas = nro_horas;
	}

	public boolean isFlagfacturar() {
		return flagfacturar;
	}

	public void setFlagfacturar(boolean flagfacturar) {
		this.flagfacturar = flagfacturar;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Tipo_Actividad_Proveedor getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(Tipo_Actividad_Proveedor tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estado_Reg_Horas getEstado() {
		return estado;
	}

	public void setEstado(Estado_Reg_Horas estado) {
		this.estado = estado;
	}

	public Proveedor_Reg_Horas() {
		super();
	}
	

	
	
}
