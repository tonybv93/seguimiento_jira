package com.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespGenerica {
	private String jira;
	private String fecha1;
	private String fecha2;
}
