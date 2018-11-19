package com.auth.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.auth.entity.JsoJira;
import com.auth.entity.JsoListaJiras;

@Repository
public class JiraApiRepository {	
	@Autowired
	private RestTemplateBuilder restTB;

	public List<JsoJira> busquedaJQL(String filtro) {	
		String cadenaJson = "https://jira.cavali.com.pe:8443/rest/api/2/search?jql=" + filtro;		
		RestTemplate restTemplate = restTB.basicAuthorization("antony.becerra","Bolsa2018").build(); 
		JsoListaJiras jiraSS = restTemplate.getForObject(cadenaJson, JsoListaJiras.class);
		
		if (jiraSS.getTotal() == 0)
			return null;
		else
			return jiraSS.getIssues();   
	}
}
