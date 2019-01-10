package com.auth.auxiliar;

import java.math.BigDecimal;

public class DetalleActaPre {
	private String jira;
	private String tipoJira;
	private String resumen;
	private BigDecimal totalHoras;
	private BigDecimal totalHorasGesDem;
	private String centrocosto; 
	
	public DetalleActaPre(String jira, String tipoJira, String resumen, BigDecimal totalHoras, BigDecimal totalHorasGesDem, String cc) {
		this.jira = jira;
		this.tipoJira = tipoJira;
		this.resumen = resumen;
		this.totalHoras = totalHoras;
		this.totalHorasGesDem = totalHorasGesDem;
		this.centrocosto = cc;
	}
	
	public String getCentrocosto() {
		return centrocosto;
	}

	public void setCentrocosto(String centrocosto) {
		this.centrocosto = centrocosto;
	}

	public DetalleActaPre() {
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public String getTipoJira() {
		return tipoJira;
	}
	public void setTipoJira(String tipoJira) {
		this.tipoJira = tipoJira;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public BigDecimal getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(BigDecimal totalHoras) {
		this.totalHoras = totalHoras;
	}
	public BigDecimal getTotalHorasGesDem() {
		return totalHorasGesDem;
	}
	public void setTotalHorasGesDem(BigDecimal totalHorasGesDem) {
		this.totalHorasGesDem = totalHorasGesDem;
	}
	
	
}
