package com.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu {
	@Id
	private Integer id;
	private String texto;	
	private String url;	
	private int nivel;
	
	@JsonIgnore()
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="raiz")
	private Menu raiz;
	
	
	@OneToMany(mappedBy="raiz")
	private List<Menu> submenus = new ArrayList<>();
	
	
	public List<Menu> getSubmenus() {
		return submenus;
	}
	public void setSubmenus(List<Menu> submenus) {
		this.submenus = submenus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public Menu getRaiz() {
		return raiz;
	}
	public void setRaiz(Menu raiz) {
		this.raiz = raiz;
	}
	public Menu() {
		super();
	}

}
