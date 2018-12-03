package com.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespGenerica {
	private int numero1;
	private int numero2;
	private int numero3;
	private int numero4;
	private String texto1;
	private String texto2;
	private String texto3;
	private String texto4;
	
	public RespGenerica() {
	}
	public int getNumero1() {
		return numero1;
	}
	public void setNumero1(int numero1) {
		this.numero1 = numero1;
	}
	public int getNumero2() {
		return numero2;
	}
	public void setNumero2(int numero2) {
		this.numero2 = numero2;
	}
	public int getNumero3() {
		return numero3;
	}
	public void setNumero3(int numero3) {
		this.numero3 = numero3;
	}
	public int getNumero4() {
		return numero4;
	}
	public void setNumero4(int numero4) {
		this.numero4 = numero4;
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
}
