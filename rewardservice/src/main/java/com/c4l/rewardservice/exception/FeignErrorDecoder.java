package com.c4l.rewardservice.exception;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder{

	@Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

      //  if (responseStatus.is5xxServerError()) {
        	
        	  
		/*
		 * return new ResponseEntityErrorException(
		 * ResponseEntity.status(responseStatus) .body(responseBody.toString()));
		 */
            
            return new Exception(responseBody.toString());
  /* );
        } else if (responseStatus.is4xxClientError()) {
            return new RestApiClientException(requestUrl, responseBody);
        } else {
            return new Exception("Generic exception");
        }*/
    }	
	
}
