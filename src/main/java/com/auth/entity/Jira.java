package com.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private int horas_cert;
	private int horas_des;
	private double monto_cert;
	private double monto_des;
	private double monto_total;	
	private Date fecha_creacion;
	private Date fecha_actualizacion;		
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_etiqueta")
	private Etiqueta etiqueta;
	
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
	
	@OneToOne(optional=false, mappedBy="jira")
	private Jira_Detalle detalle_fecha;
	
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
	public int getHoras_cert() {
		return horas_cert;
	}
	public void setHoras_cert(int horas_cert) {
		this.horas_cert = horas_cert;
	}
	public int getHoras_des() {
		return horas_des;
	}
	public void setHoras_des(int horas_des) {
		this.horas_des = horas_des;
	}
	public double getMonto_cert() {
		return monto_cert;
	}
	public void setMonto_cert(double monto_cert) {
		this.monto_cert = monto_cert;
	}
	public double getMonto_des() {
		return monto_des;
	}
	public void setMonto_des(double monto_des) {
		this.monto_des = monto_des;
	}
	public double getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(double monto_total) {
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
	public Jira() {
		super();
	}
	public Jira_Detalle getDetalle_fecha() {
		return detalle_fecha;
	}
	public void setDetalle_fecha(Jira_Detalle detalle_fecha) {
		this.detalle_fecha = detalle_fecha;
	}	
	
}
