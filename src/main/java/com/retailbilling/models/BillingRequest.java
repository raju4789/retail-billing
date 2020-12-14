package com.retailbilling.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class BillingRequest {
	
	@NotNull
	@NotEmpty
	private String orderId;
	
	@NotNull
	@NotEmpty
	private String userId;
	
	@NotNull
	private List<Item> items;
	
	

	public BillingRequest() {
		super();
	}

	public BillingRequest(@NotNull @NotEmpty String orderId, @NotNull @NotEmpty String userId,
			@NotNull List<Item> items) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", userId=" + userId + ", items=" + items + "]";
	}
		
}
