package com.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsoJira {

    private String id;
    private String key;    
    private JsoFields fields;
    private String colorClass;
    
	public String getColorClass() {
		return colorClass;
	}
	public void setColorClass(String colorClass) {
		this.colorClass = colorClass;
	}
	public JsoFields getFields() {
		return fields;
	}
	public void setFields(JsoFields fields) {
		this.fields = fields;
	}
	public JsoJira() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
    
}
