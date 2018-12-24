package com.auth.configuration;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class MyErrorHandler implements ResponseErrorHandler{

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode()
		          .series() == HttpStatus.Series.SERVER_ERROR) {
			System.out.println("LOOOOOOOOOOL3");
		        } else if (httpResponse.getStatusCode()
		          .series() == HttpStatus.Series.CLIENT_ERROR) {
		        	System.out.println("LOOOOOOOOOOL2");
		            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
		                System.out.println("LOOOOOOOOOOL1");
		            }
		        }
		
	}
	

}
