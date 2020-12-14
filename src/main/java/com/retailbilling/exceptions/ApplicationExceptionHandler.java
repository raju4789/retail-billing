package com.retailbilling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retailbilling.models.BillingResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<BillingResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		
		BillingResponse billingResponse = new BillingResponse();
		billingResponse.setStatusMessage(e.getMessage());
		return new ResponseEntity<BillingResponse>(billingResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<BillingResponse> handleInvalidRequestException(InvalidRequestException e) {
		BillingResponse billingResponse = new BillingResponse();
		billingResponse.setStatusMessage(e.getMessage());
		return new ResponseEntity<BillingResponse>(billingResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<BillingResponse> handleGenericException(Exception e) {
		BillingResponse billingResponse = new BillingResponse();
		billingResponse.setStatusMessage(e.getMessage());
		return new ResponseEntity<BillingResponse>(billingResponse, HttpStatus.BAD_REQUEST);
	}

}
