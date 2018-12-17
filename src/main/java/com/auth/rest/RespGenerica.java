package com.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespGenerica {
	private double numero1;
	private double numero2;
	private double numero3;
	private double numero4;
	private String texto1;
	private String texto2;
	private String texto3;
	private String texto4;
	
	public RespGenerica() {
		
	}	
	
	public String getTexto3() {
		return texto3;
	}
	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}
	public String getTexto4() {
		return texto4;
	}
	public void setTexto4(String texto4) {
		this.texto4 = texto4;
	}
	public String getTexto1() {
		return texto1;
	}
	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}
	public String getTexto2() {
		return texto2;
	}
	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public double getNumero1() {
		return numero1;
	}

	public void setNumero1(double numero1) {
		this.numero1 = numero1;
	}

	public double getNumero2() {
		return numero2;
	}

	public void setNumero2(double numero2) {
		this.numero2 = numero2;
	}

	public double getNumero3() {
		return numero3;
	}

	public void setNumero3(double numero3) {
		this.numero3 = numero3;
	}

	public double getNumero4() {
		return numero4;
	}

	public void setNumero4(double numero4) {
		this.numero4 = numero4;
	}
		
}
