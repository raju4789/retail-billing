package com.retailbilling.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailbilling.exceptions.ResourceNotFoundException;
import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.BillingResponse;
import com.retailbilling.models.Item;
import com.retailbilling.models.Product;
import com.retailbilling.models.User;
import com.retailbilling.repos.ProductRepository;
import com.retailbilling.repos.UserRepository;
import com.retailbilling.utils.ApplicationConstants;

@Service
public class BillingService {

	private UserRepository userRepository;

	private ProductRepository productRepository;
	

	@Autowired
	public BillingService(UserRepository userRepository, ProductRepository productRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	public BillingResponse generateBill(BillingRequest billingRequest) throws ResourceNotFoundException {

		final String orderId = billingRequest.getOrderId();
		final String userId = billingRequest.getUserId();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user don't exist with given userId"));

		List<Item> orderItems = billingRequest.getItems();

		double billingAmount = calculateBillingAmount(orderItems, user);

		BillingResponse billingResponse = new BillingResponse();
		billingResponse.setOrderId(orderId);
		billingResponse.setUserId(userId);
		billingResponse.setBillingAmount(billingAmount);
		billingResponse.setStatusMessage("final bill generated successfully");

		return billingResponse;
	}

	private double calculateBillingAmount(List<Item> orderItems, User user) throws ResourceNotFoundException {

		double groceriesAmount = 0d;
		double nonGroceriesAmount = 0d;
		double totalBill;

		final String userRole = user.getRole();

		for (Item item : orderItems) {

			final String productId = item.getProductId();

			try {
				Product product = productRepository.findById(productId)
						.orElseThrow(() -> new ResourceNotFoundException("product don't exist with given productId"));

				if (!ApplicationConstants.GROCERIES.equalsIgnoreCase(product.getCategory())) {
					nonGroceriesAmount += product.getPrice() * item.getQuantity();
				} else {
					groceriesAmount += product.getPrice() * item.getQuantity();
				}

			} catch (ResourceNotFoundException e) {
				throw new ResourceNotFoundException(e.getMessage());
			}
		}

		switch (userRole) {
		
			case ApplicationConstants.EMPLOYEE:
				totalBill = calculateTotalBill(groceriesAmount, nonGroceriesAmount, userRole, user.getAccountCreatedOn());
				break;
	
			case ApplicationConstants.AFFILIATE:
				totalBill = calculateTotalBill(groceriesAmount, nonGroceriesAmount, userRole, user.getAccountCreatedOn());
				break;
	
			case ApplicationConstants.CUSTOMER:
				totalBill = calculateTotalBill(groceriesAmount, nonGroceriesAmount, userRole, user.getAccountCreatedOn());
				break;
	
			default:
				totalBill = groceriesAmount + nonGroceriesAmount;
				break;
		}

		return totalBill;

	}

	private static double calculateTotalBill(double groceriesAmount, double nonGroceriesAmount, final String userRole,
			String accountActiveFrom) {
		double totalBill = 0;

		if (ApplicationConstants.CUSTOMER.equalsIgnoreCase(userRole)) {
			final LocalDate activeFrom = LocalDate.parse(accountActiveFrom);
			final long yearsActive = activeFrom.until(LocalDateTime.now(), ChronoUnit.YEARS);

			if (yearsActive > 2) {
				totalBill = calculatePercentageDiscountedValue(userRole, nonGroceriesAmount, groceriesAmount);
			} else {
				totalBill = groceriesAmount + nonGroceriesAmount;
			}

			totalBill = calculateFixedDiscountedBill(totalBill);
		} else {
			totalBill = calculatePercentageDiscountedValue(userRole, nonGroceriesAmount, groceriesAmount);
			totalBill = calculateFixedDiscountedBill(totalBill);
		}

		return totalBill;
	}

	private static double calculatePercentageDiscountedValue(String userRole, double nonGroceriesAmount,
			double groceriesAmount) {

		double discount = ApplicationConstants.USER_PERCENTAGE_DISCOUNT.get(userRole.toUpperCase()) / 100;

		nonGroceriesAmount -= nonGroceriesAmount * discount;

		return nonGroceriesAmount + groceriesAmount;

	}

	private static double calculateFixedDiscountedBill(double totalBill) {
		double discount = 0.0;
		double tempBill = totalBill;

		while (tempBill >= 100) {
			discount += ApplicationConstants.USER_FIXED_DISCOUNT;
			tempBill -= ApplicationConstants.FIXED_DISCOUNT_APPLICABLE_BILL;
		}

		return totalBill - discount;
	}

}
