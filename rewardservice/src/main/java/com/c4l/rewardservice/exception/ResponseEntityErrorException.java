package com.c4l.rewardservice.exception;

import org.springframework.http.ResponseEntity;

public class ResponseEntityErrorException extends RuntimeException  {
    
    private ResponseEntity<String> serviceErrorResponseResponse;

    public ResponseEntityErrorException(ResponseEntity<String> serviceErrorResponseResponse) {
        this.serviceErrorResponseResponse = serviceErrorResponseResponse;
    }
    
    public ResponseEntity<String> getServiceErrorResponseResponse() {
        return serviceErrorResponseResponse;
    }
}