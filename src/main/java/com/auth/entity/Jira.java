package com.auth.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="jira")
public class Jira {
	@Id
	@Column(name="ID")
	private int id;
	private String jira;
	private String resumen;
	private String informador;
	private String asignado;
	private String responsable;	
	private String centro_costo;
	private BigDecimal horas_cert;
	private BigDecimal horas_des;
	private BigDecimal monto_cert;
	private BigDecimal monto_des;
	private BigDecimal monto_total;	
	private Date fecha_creacion;
	private Date fecha_actualizacion;	
	private Date fecha_pruebas;
	private Date fecha_produccion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_etiqueta")
	private Etiqueta etiqueta;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_area_solicitante")
	private Area_Solicitante areaSolicitante;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_indicador")
	private Indicador_Contable indicador;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_tipo_requerimiento")
	private Tipo_Requerimiento tipoRequerimiento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_estado_jira")
	private Estado_Jira estadoJira;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_fabrica")
	private Fabrica fabrica;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
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
	public BigDecimal getHoras_cert() {
		return horas_cert;
	}
	public void setHoras_cert(BigDecimal horas_cert) {
		this.horas_cert = horas_cert;
	}
	public BigDecimal getHoras_des() {
		return horas_des;
	}
	public void setHoras_des(BigDecimal horas_des) {
		this.horas_des = horas_des;
	}
	public BigDecimal getMonto_cert() {
		return monto_cert;
	}
	public void setMonto_cert(BigDecimal monto_cert) {
		this.monto_cert = monto_cert;
	}
	public BigDecimal getMonto_des() {
		return monto_des;
	}
	public void setMonto_des(BigDecimal monto_des) {
		this.monto_des = monto_des;
	}
	public BigDecimal getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(BigDecimal monto_total) {
		this.monto_total = monto_total;
	}
	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public Date getFecha_actualizacion() {
		return fecha_actualizacion;
	}
	public void setFecha_actualizacion(Date fecha_actualizacion) {
		this.fecha_actualizacion = fecha_actualizacion;
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
	public Indicador_Contable getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador_Contable indicador) {
		this.indicador = indicador;
	}
	public Tipo_Requerimiento getTipoRequerimiento() {
		return tipoRequerimiento;
	}
	public void setTipoRequerimiento(Tipo_Requerimiento tipoRequerimiento) {
		this.tipoRequerimiento = tipoRequerimiento;
	}
	public Estado_Jira getEstadoJira() {
		return estadoJira;
	}
	public void setEstadoJira(Estado_Jira estadoJira) {
		this.estadoJira = estadoJira;
	}
	public Fabrica getFabrica() {
		return fabrica;
	}
	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
	}	
	public Date getFecha_pruebas() {
		return fecha_pruebas;
	}
	public void setFecha_pruebas(Date fecha_pruebas) {
		this.fecha_pruebas = fecha_pruebas;
	}
	public Date getFecha_produccion() {
		return fecha_produccion;
	}
	public void setFecha_produccion(Date fecha_produccion) {
		this.fecha_produccion = fecha_produccion;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}	
	public String getCentro_costo() {
		return centro_costo;
	}
	public void setCentro_costo(String centro_costo) {
		this.centro_costo = centro_costo;
	}
	public Jira() {
		super();
	}	
}
