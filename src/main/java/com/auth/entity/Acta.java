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
	
	//subtotales
	private BigDecimal total_horas_desarrollo;
	private BigDecimal total_horas_concepto1;
	private BigDecimal total_horas_concepto2;	
	//totales
	private BigDecimal total_horas;
	private BigDecimal tarifa;
	private BigDecimal monto_bruto;
	private BigDecimal monto_neto;
	private Date fecha_registro;	
	private Date fecha_aprobacion;	
	private boolean flagactivo;
	private boolean flagaprobado;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_acta_estado")
	private Acta_Estado estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_periodo")
	private Periodo periodo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador_contable")
	private Indicador_Contable indicador;
	
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
	
	public BigDecimal getTotal_horas_desarrollo() {
		return total_horas_desarrollo;
	}

	public void setTotal_horas_desarrollo(BigDecimal total_horas_desarrollo) {
		this.total_horas_desarrollo = total_horas_desarrollo;
	}

	public BigDecimal getTotal_horas_concepto1() {
		return total_horas_concepto1;
	}

	public void setTotal_horas_concepto1(BigDecimal total_horas_concepto1) {
		this.total_horas_concepto1 = total_horas_concepto1;
	}

	public BigDecimal getTotal_horas_concepto2() {
		return total_horas_concepto2;
	}

	public void setTotal_horas_concepto2(BigDecimal total_horas_concepto2) {
		this.total_horas_concepto2 = total_horas_concepto2;
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
	
	

	public BigDecimal getTotal_horas() {
		return total_horas;
	}

	public void setTotal_horas(BigDecimal total_horas) {
		this.total_horas = total_horas;
	}

	public BigDecimal getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public BigDecimal getMonto_bruto() {
		return monto_bruto;
	}

	public void setMonto_bruto(BigDecimal monto_bruto) {
		this.monto_bruto = monto_bruto;
	}

	public BigDecimal getMonto_neto() {
		return monto_neto;
	}

	public void setMonto_neto(BigDecimal monto_neto) {
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

	public Fabrica getFabrica() {
		return fabrica;
	}

	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	
	
}
