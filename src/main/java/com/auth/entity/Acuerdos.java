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
public class Acuerdos {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACUERDOS_SEQ")
	@SequenceGenerator(sequenceName="acuerdos_seq", allocationSize=1, name="ACUERDOS_SEQ")
	private Integer id;
	
	@Column(name="id_jira")
	private String id_jira;	
	
	@Column(name="acuerdo")
	private String acuerdo;	
	
	@Column(name="observacion")
	private String observacion;	
	
	@Column(name="flagterminado")
	private boolean flagterminado;

	@Column(name="fecha_creacion")
	private Date fecha_creacion;
	
	@Column(name="fecha_limite")
	private Date fecha_limite;	
	
	@Column(name="fecha_cierre")
	private Date fecha_cierre;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_tipo_acuerdo")
	private Tipo_Acuerdo tipo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario_responsable")
	private Usuario responsable;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_estado_acuerdo")
	private Estado_Acuerdo estado;
	

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_area_solicitante")
	private Area_Solicitante areaSolicitante;

	/**
	 * 
	 */
	public Acuerdos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getId_jira() {
		return id_jira;
	}

	public void setId_jira(String id_jira) {
		this.id_jira = id_jira;
	}

	public String getAcuerdo() {
		return acuerdo;
	}

	public void setAcuerdo(String acuerdo) {
		this.acuerdo = acuerdo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean isFlagterminado() {
		return flagterminado;
	}

	public void setFlagterminado(boolean flagterminado) {
		this.flagterminado = flagterminado;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public Date getFecha_limite() {
		return fecha_limite;
	}

	public void setFecha_limite(Date fecha_limite) {
		this.fecha_limite = fecha_limite;
	}

	public Date getFecha_cierre() {
		return fecha_cierre;
	}

	public void setFecha_cierre(Date fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
	}

	public Tipo_Acuerdo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_Acuerdo tipo) {
		this.tipo = tipo;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Estado_Acuerdo getEstado() {
		return estado;
	}

	public void setEstado(Estado_Acuerdo estado) {
		this.estado = estado;
	}

	public Area_Solicitante getAreaSolicitante() {
		return areaSolicitante;
	}

	public void setAreaSolicitante(Area_Solicitante areaSolicitante) {
		this.areaSolicitante = areaSolicitante;
	}
	
}
