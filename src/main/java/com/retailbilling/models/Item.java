package com.retailbilling.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Item {
	
	@NotNull
	@NotEmpty
	private String productId;
	
	@Min(1)
	private int quantity;
	
	

	public Item() {
		super();
	}

	public Item(@NotNull @NotEmpty String productId, @Min(1) int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [productId=" + productId + ", quantity=" + quantity + "]";
	}
	

}
