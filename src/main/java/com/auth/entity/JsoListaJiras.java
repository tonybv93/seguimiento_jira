package com.auth.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsoListaJiras {
	private int total;
	private List<JsoJira> issues;
	public JsoListaJiras() {
	}
	public List<JsoJira> getIssues() {
		return issues;
	}
	public void setIssues(List<JsoJira> issues) {
		this.issues = issues;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
