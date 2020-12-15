package com.retailbilling.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailbilling.exceptions.ResourceNotFoundException;
import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.BillingResponse;
import com.retailbilling.services.BillingService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
@Validated
public class BillingController {
	
	@Autowired
	private BillingService billingService;
	
	@PostMapping("/billing")
	@ApiOperation(value = "generate bill from order details", response = BillingResponse.class)
	public ResponseEntity<BillingResponse> generateBill(@RequestBody @Valid BillingRequest billingRequest) throws ResourceNotFoundException{
		
		BillingResponse billingResponse = billingService.generateBill(billingRequest);
		
		return new ResponseEntity<>(billingResponse, HttpStatus.OK);
		
	}

}
