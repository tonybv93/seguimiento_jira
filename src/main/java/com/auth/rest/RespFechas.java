package com.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespFechas {
	private String jira;
	private String fecha1;
	private String fecha2;
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public String getFecha1() {
		return fecha1;
	}
	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}
	public String getFecha2() {
		return fecha2;
	}
	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public RespFechas() {
		super();
	}
	
}
