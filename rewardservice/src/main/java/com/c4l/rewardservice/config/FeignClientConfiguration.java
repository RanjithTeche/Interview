package com.c4l.rewardservice.config;

import org.springframework.context.annotation.Bean;

import com.c4l.rewardservice.exception.FeignErrorDecoder;

import feign.codec.ErrorDecoder;

public class FeignClientConfiguration {

	
	 @Bean
	    public ErrorDecoder errorDecoder() {
	        return new FeignErrorDecoder();
	    }
}
