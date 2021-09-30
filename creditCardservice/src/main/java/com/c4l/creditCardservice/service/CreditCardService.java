package com.c4l.creditCardservice.service;

import com.c4l.creditCardservice.model.VerificationResponse;

public interface CreditCardService {

	VerificationResponse checkCard(String pseudoCard);
}
