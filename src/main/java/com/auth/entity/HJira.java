package com.auth.entity;

import java.math.BigDecimal;
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

@Entity
@DynamicInsert
@DynamicUpdate
public class HJira {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HORAS_X_JIRA_SEQ")
	@SequenceGenerator(sequenceName="HORAS_X_JIRA_SEQ", allocationSize=1, name="HORAS_X_JIRA_SEQ")
	private Integer id;
	private String jira;
	private String descripcion;
	private String tipo;
	private String informador;
	private String asignado;
	private String responsable;	
	private BigDecimal horas_desarrollo;
	private BigDecimal horas_prueba;
	private BigDecimal consumido_prueba;
	private BigDecimal consumido_desarrollo;
	private BigDecimal reserva_desarrollo;
	private Date fecha_entrega;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_fabrica")
	private Fabrica fabrica;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado")
	private Estado_Jira estado;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador_contable")
	private Indicador_Contable indicador;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_centro_costo")
	private Centro_Costo centro_costo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_etiqueta")
	private Etiqueta etiqueta;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_solicitante")
	private Area_Solicitante areaSolicitante;
	
	
	
	
	public String getInformador() {
		return informador;
	}
	public void setInformador(String informador) {
		this.informador = informador;
	}
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public Etiqueta getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}
	public Area_Solicitante getAreaSolicitante() {
		return areaSolicitante;
	}
	public void setAreaSolicitante(Area_Solicitante areaSolicitante) {
		this.areaSolicitante = areaSolicitante;
	}
	public Estado_Jira getEstado() {
		return estado;
	}
	public void setEstado(Estado_Jira estado) {
		this.estado = estado;
	}
	public Date getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(Date fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public BigDecimal getReserva_desarrollo() {
		return reserva_desarrollo;
	}
	public void setReserva_desarrollo(BigDecimal reserva_desarrollo) {
		this.reserva_desarrollo = reserva_desarrollo;
	}
	
	public Centro_Costo getCentro_costo() {
		return centro_costo;
	}
	public void setCentro_costo(Centro_Costo centro_costo) {
		this.centro_costo = centro_costo;
	}
	public Fabrica getFabrica() {
		return fabrica;
	}
	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	public Indicador_Contable getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador_Contable indicador) {
		this.indicador = indicador;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public HJira() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getHoras_desarrollo() {
		return horas_desarrollo;
	}
	public void setHoras_desarrollo(BigDecimal horas_desarrollo) {
		this.horas_desarrollo = horas_desarrollo;
	}
	public BigDecimal getHoras_prueba() {
		return horas_prueba;
	}
	public void setHoras_prueba(BigDecimal horas_prueba) {
		this.horas_prueba = horas_prueba;
	}
	public BigDecimal getConsumido_prueba() {
		return consumido_prueba;
	}
	public void setConsumido_prueba(BigDecimal consumido_prueba) {
		this.consumido_prueba = consumido_prueba;
	}
	public BigDecimal getConsumido_desarrollo() {
		return consumido_desarrollo;
	}
	public void setConsumido_desarrollo(BigDecimal consumido_desarrollo) {
		this.consumido_desarrollo = consumido_desarrollo;
	}
	
}
