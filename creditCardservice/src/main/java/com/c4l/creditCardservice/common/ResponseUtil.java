package com.c4l.creditCardservice.common;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseUtil {
	
	
	
	public static <T> ResponseEntity<T> response201(T object,String pathParam){
		HttpHeaders responseHeaders=generateResponseHeader();
		
		try {
			URI uri=new URI(ServletUriComponentsBuilder.fromCurrentRequest().path(null).buildAndExpand(object).toUri().getPath());
			responseHeaders.set(pathParam, pathParam);
		}
		catch (URISyntaxException e) {
			log.debug("Exception"+e.getMessage());
		}
		return new ResponseEntity<T>(responseHeaders,HttpStatus.CREATED);
	}
	

	public static <T> ResponseEntity<T> response200(T object){
		HttpHeaders responseHeaders=generateResponseHeader();
		
		
		return new ResponseEntity<T>(object,responseHeaders,HttpStatus.OK);
	}
	
	

	public static HttpHeaders generateResponseHeader() {
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.set(ApplicationConstant.HEADER_PARAM_RESPONSE_TIME, String.valueOf(System.currentTimeMillis()));
		
		return responseHeaders;
	}
	
	
	
	
	
}
