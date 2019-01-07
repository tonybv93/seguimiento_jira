package com.auth.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.auth.configuration.MyErrorHandler;
import com.auth.entity.JsoJira;
import com.auth.entity.JsoListaJiras;

@Repository
public class JiraApiRepository {	
	
	@Autowired
	private RestTemplateBuilder restTB;
	
	public List<JsoJira> busquedaJQL(String filtro) {	
		JsoListaJiras jiraSS = new JsoListaJiras();
		String cadenaJson = "https://jira.cavali.com.pe:8443/rest/api/2/search?jql=" + filtro;		
		RestTemplate restTemplate = restTB.basicAuthorization("antony.becerra","Bolsa2019").errorHandler(new MyErrorHandler()).build(); 
		System.out.println(cadenaJson);
		jiraSS = restTemplate.getForObject(cadenaJson, JsoListaJiras.class);		
		return jiraSS.getIssues();
	}
}
