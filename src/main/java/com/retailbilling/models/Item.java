package com.retailbilling.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel
public class Item {
	
	@NotBlank(message = "product id is mandatory")
	private String productId;
	
	@Min(value = 1, message = "product quantity should be min of 1")
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
