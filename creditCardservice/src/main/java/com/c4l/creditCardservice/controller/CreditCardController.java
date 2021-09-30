package com.c4l.creditCardservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4l.creditCardservice.common.ResponseUtil;
import com.c4l.creditCardservice.model.VerificationResponse;
import com.c4l.creditCardservice.service.CreditCardService;


@RequestMapping("/creditCard")
@RestController
public class CreditCardController {

	@Autowired
	CreditCardService creditCardService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@CircuitBreaker(name = "default", fallbackMethod = "checkCardFallBack")
	@GetMapping( "/checkCard/{pseudoCard}")
	public ResponseEntity<VerificationResponse> checkCard(@PathVariable String pseudoCard) {
		logger.info("Entered checkCard input: {}", pseudoCard);
		
		//Verify the given pseudo card against the data base
		VerificationResponse reponse =creditCardService.checkCard(pseudoCard);
		
		return ResponseUtil.response200(reponse);
	}	
  	
	/*
	 * public ResponseEntity<VerificationResponse> checkCardFallBack(Exception ex){
	 * 
	 * }
	 */
	
}
