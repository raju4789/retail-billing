package com.retailbilling.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "product_id")
	private String productId;

	@NotNull
	@NotEmpty
	private String name;

	private String description;

	@NotNull
	@NotEmpty
	private String category;

	@Min(1)
	private double price;

	public Product() {
		super();
	}

	public Product(String productId, @NotNull @NotEmpty String name, String description,
			@NotNull @NotEmpty String category, @Min(1) double price) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", description=" + description + ", category="
				+ category + ", price=" + price + "]";
	}

}
