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
public class Acta {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acta_seq")
	@SequenceGenerator(sequenceName="acta_seq", allocationSize=1, name="acta_seq")
	private Integer id;

	@Column(name="codigo")
	private String codigo;	
	
	@Column(name="version")
	private String version;	
	
	@Column(name="fecha_creación")
	private Date fecha_creación;	

	@Column(name="fecha_entrega")
	private Date fecha_entrega;
	
	@Column(name="fecha_aprobacion")
	private Date fecha_aprobacion;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="contrato")
	private String contrato;
	
	@Column(name="lugar_aprobacion")
	private String lugar_aprobacion;
	
	@Column(name="flagactivo")
	private boolean flagactivo;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado")
	private Estado_Acta estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proyecto")
	private Proyecto proyecto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_acta")
	private Tipo_Acta tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_fabrica")
	private Fabrica fabrica;

	
}
