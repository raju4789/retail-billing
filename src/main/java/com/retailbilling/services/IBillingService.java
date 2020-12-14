package com.retailbilling.services;

import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.BillingResponse;


public interface IBillingService {
	
	public BillingResponse generateBill(BillingRequest billingRequest);
	
}
