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

	private String codigo;	
	private String version;		
	private String razon_social_proveedor;
	private String descripcion_servicio;
	private long tatal_horas;
	private long tarifa;
	private long monto_bruto;
	private long monto_neto;
	private Date fecha_registro;	
	private Date fecha_aprobacion;	
	private boolean flagactivo;
	private boolean flagaprobado;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estado")
	private Acta_Estado estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_periodo")
	private Periodo periodo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_acta")
	private Acta_Tipo tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_fabrica")
	private Fabrica fabrica;

	public Acta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRazon_social_proveedor() {
		return razon_social_proveedor;
	}

	public void setRazon_social_proveedor(String razon_social_proveedor) {
		this.razon_social_proveedor = razon_social_proveedor;
	}

	public String getDescripcion_servicio() {
		return descripcion_servicio;
	}

	public void setDescripcion_servicio(String descripcion_servicio) {
		this.descripcion_servicio = descripcion_servicio;
	}

	public long getTatal_horas() {
		return tatal_horas;
	}

	public void setTatal_horas(long tatal_horas) {
		this.tatal_horas = tatal_horas;
	}

	public long getTarifa() {
		return tarifa;
	}

	public void setTarifa(long tarifa) {
		this.tarifa = tarifa;
	}

	public long getMonto_bruto() {
		return monto_bruto;
	}

	public void setMonto_bruto(long monto_bruto) {
		this.monto_bruto = monto_bruto;
	}

	public long getMonto_neto() {
		return monto_neto;
	}

	public void setMonto_neto(long monto_neto) {
		this.monto_neto = monto_neto;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Date getFecha_aprobacion() {
		return fecha_aprobacion;
	}

	public void setFecha_aprobacion(Date fecha_aprobacion) {
		this.fecha_aprobacion = fecha_aprobacion;
	}

	public boolean isFlagactivo() {
		return flagactivo;
	}

	public void setFlagactivo(boolean flagactivo) {
		this.flagactivo = flagactivo;
	}

	public boolean isFlagaprobado() {
		return flagaprobado;
	}

	public void setFlagaprobado(boolean flagaprobado) {
		this.flagaprobado = flagaprobado;
	}

	public Acta_Estado getEstado() {
		return estado;
	}

	public void setEstado(Acta_Estado estado) {
		this.estado = estado;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Acta_Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Acta_Tipo tipo) {
		this.tipo = tipo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Fabrica getFabrica() {
		return fabrica;
	}

	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	
	
}
