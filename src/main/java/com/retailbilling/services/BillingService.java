package com.retailbilling.services;

import org.springframework.stereotype.Service;

import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.BillingResponse;

@Service
public class BillingService implements IBillingService {
	
	@Override
	public BillingResponse generateBill(BillingRequest billingRequest) {
		final String orderId = billingRequest.getOrderId();
		final String userId = billingRequest.getUserId();
		
		return null;
	}

}
