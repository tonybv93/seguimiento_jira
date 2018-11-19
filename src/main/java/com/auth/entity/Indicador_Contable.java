package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Indicador_Contable {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indicador_contable_seq")
	@SequenceGenerator(sequenceName="indicador_contable_seq", allocationSize=1, name="indicador_contable_seq")
	private int id;
	private String indicador;
	private String css_class;
	@Column(name="ICON_IND_CONTABLE")
	private String icon_ind_contable;

	public Indicador_Contable() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public String getCss_class() {
		return css_class;
	}
	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}
	public String getIcon_ind_contable() {
		return icon_ind_contable;
	}
	public void setIcon_ind_contable(String icon_ind_contable) {
		this.icon_ind_contable = icon_ind_contable;
	}
	

}
