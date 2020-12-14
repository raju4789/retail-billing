package com.retailbilling.models;

public class BillingResponse {

	private String orderId;
	private String userId;
	private double billingAmount;
	private String statusMessage;
	
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
	public double getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	

}
