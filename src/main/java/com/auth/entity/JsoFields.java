package com.auth.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsoFields {	

	private Integer id;
	private String summary;							//Descripcion
	
	private JsoUsuario customfield_11236; 			//Analista responsable
	private JsoUsuario assignee;					//Reponsable
	private JsoUsuario reporter;					//Informador
	
	private JsoPersonalizado customfield_10800; 	//Area Solicitante
	private JsoPersonalizado customfield_11483; 	//Indicador
	private JsoPersonalizado customfield_11016; 	//Fabrica
	private JsoPersonalizado customfield_17050;		//Centro de costo
	private JsoPersonalizado customfield_11640;		//EMPRESA
	
	private JsoAtributoG issuetype;					//Tipo de incidencia	
	private JsoAtributoG status;					//Estado
	private JsoAtributoG priority;					//Prioridad
	
    private Date created;							//Creacion
    private Date updated;							//Actualizaciòn    
	
	private int customfield_14851; 					//horas certificacion
	private int customfield_14850; 					//horas pruebas	
    
	private JsoParent parent;    
    private List<String> labels;					//Etiquetas	
    
    // CAMPOS PERSONALIZADOS (NO SALEN DE JSON)
    private String etiqueta;						//Etiqueta para filtro
    private String empresa;							// Cavali o Bolsa
    private String nuevoEstado;						//Etiqueta para el nuevo estados
    private String nuevoResponsable;				//Nuevo responsable
    private String grupoEstado;						//Etiqueta para agrupar los estados
    private String gestado_class;					//Etiqueta para agrupar los estados
    private List<JsoJira> subTareas = new ArrayList<>();	//Jiras hijo
	
    
    public JsoPersonalizado getCustomfield_11640() {
		return customfield_11640;
	}
	public void setCustomfield_11640(JsoPersonalizado customfield_11640) {
		this.customfield_11640 = customfield_11640;
	}
	public JsoPersonalizado getCustomfield_17050() {
		return customfield_17050;
	}
	public void setCustomfield_17050(JsoPersonalizado customfield_17050) {
		this.customfield_17050 = customfield_17050;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public JsoUsuario getCustomfield_11236() {
		return customfield_11236;
	}
	public void setCustomfield_11236(JsoUsuario customfield_11236) {
		this.customfield_11236 = customfield_11236;
	}
	public JsoUsuario getAssignee() {
		return assignee;
	}
	public void setAssignee(JsoUsuario assignee) {
		this.assignee = assignee;
	}
	public JsoUsuario getReporter() {
		return reporter;
	}
	public void setReporter(JsoUsuario reporter) {
		this.reporter = reporter;
	}
	public JsoPersonalizado getCustomfield_10800() {
		return customfield_10800;
	}
	public void setCustomfield_10800(JsoPersonalizado customfield_10800) {
		this.customfield_10800 = customfield_10800;
	}
	public JsoPersonalizado getCustomfield_11483() {
		return customfield_11483;
	}
	public void setCustomfield_11483(JsoPersonalizado customfield_11483) {
		this.customfield_11483 = customfield_11483;
	}
	public JsoPersonalizado getCustomfield_11016() {
		return customfield_11016;
	}
	public void setCustomfield_11016(JsoPersonalizado customfield_11016) {
		this.customfield_11016 = customfield_11016;
	}
	public JsoAtributoG getIssuetype() {
		return issuetype;
	}
	public void setIssuetype(JsoAtributoG issuetype) {
		this.issuetype = issuetype;
	}
	public JsoAtributoG getStatus() {
		return status;
	}
	public void setStatus(JsoAtributoG status) {
		this.status = status;
	}
	public JsoAtributoG getPriority() {
		return priority;
	}
	public void setPriority(JsoAtributoG priority) {
		this.priority = priority;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public int getCustomfield_14851() {
		return customfield_14851;
	}
	public void setCustomfield_14851(int customfield_14851) {
		this.customfield_14851 = customfield_14851;
	}
	public int getCustomfield_14850() {
		return customfield_14850;
	}
	public void setCustomfield_14850(int customfield_14850) {
		this.customfield_14850 = customfield_14850;
	}
	public JsoParent getParent() {
		return parent;
	}
	public void setParent(JsoParent parent) {
		this.parent = parent;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getNuevoEstado() {
		return nuevoEstado;
	}
	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}
	public String getNuevoResponsable() {
		return nuevoResponsable;
	}
	public void setNuevoResponsable(String nuevoResponsable) {
		this.nuevoResponsable = nuevoResponsable;
	}
	public String getGrupoEstado() {
		return grupoEstado;
	}
	public void setGrupoEstado(String grupoEstado) {
		this.grupoEstado = grupoEstado;
	}
	public String getGestado_class() {
		return gestado_class;
	}
	public void setGestado_class(String gestado_class) {
		this.gestado_class = gestado_class;
	}
	public List<JsoJira> getSubTareas() {
		return subTareas;
	}
	public void setSubTareas(List<JsoJira> subTareas) {
		this.subTareas = subTareas;
	}
	public JsoFields() {
		super();
	}    
}
