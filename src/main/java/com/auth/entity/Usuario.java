package com.auth.entity;

import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
@Entity
@DynamicInsert
public class Usuario {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	@SequenceGenerator(sequenceName="usuario_seq", allocationSize=1, name="usuario_seq")
	private Integer id;
	private String username;
	private String password;
	private String descripcion;
	private int enable;
	
	@ManyToMany
	@JoinTable(name = "usuario_rol",
    joinColumns = { @JoinColumn(name = "id_user") },
    inverseJoinColumns = { @JoinColumn(name = "id_rol") })
	private List<Rol> roles;

	public Usuario() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
