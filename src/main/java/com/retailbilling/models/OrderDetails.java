package com.retailbilling.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
	
	@Id
	@Column(name= "order_id")
	private String orderId;
	
	
	@NotNull
	@NotEmpty
	@Column(name= "user_id")
	private String userId;
	
	@NotNull
	@NotEmpty
	private List<Item> items;
	
}
