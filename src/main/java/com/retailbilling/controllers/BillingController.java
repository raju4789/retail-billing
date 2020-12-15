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

import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.BillingResponse;
import com.retailbilling.services.IBillingService;

@RestController
@RequestMapping("/api/v1")
@Validated
public class BillingController {
	
	@Autowired
	private IBillingService billingService;
	
	@PostMapping("/billing")
	public ResponseEntity<BillingResponse> generateBill(@RequestBody @Valid BillingRequest billingRequest){
		
		BillingResponse billingResponse = billingService.generateBill(billingRequest);
		
		return new ResponseEntity<>(billingResponse, HttpStatus.OK);
		
	}

}
