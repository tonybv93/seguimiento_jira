package com.auth.entity;

import java.util.Date;

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
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comentario_seq")
	@SequenceGenerator(sequenceName="comentario_seq", allocationSize=1, name="comentario_seq")
	private Integer id;

	private Date fecha_registro;
	private String comentario;
	private boolean flagactivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acta")
	private Acta acta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Comentario() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public boolean isFlagactivo() {
		return flagactivo;
	}

	public void setFlagactivo(boolean flagactivo) {
		this.flagactivo = flagactivo;
	}

	public Acta getActa() {
		return acta;
	}

	public void setActa(Acta acta) {
		this.acta = acta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
