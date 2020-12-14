package com.retailbilling.services;

import org.springframework.stereotype.Service;

import com.retailbilling.models.OrderDetails;

@Service
public interface IBillingService {
	
	public double generateBill(OrderDetails orderDetails);
	
}
