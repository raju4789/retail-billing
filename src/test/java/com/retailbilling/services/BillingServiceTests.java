
package com.retailbilling.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailbilling.exceptions.ResourceNotFoundException;
import com.retailbilling.models.BillingRequest;
import com.retailbilling.models.Item;
import com.retailbilling.models.Product;
import com.retailbilling.models.User;
import com.retailbilling.repos.ProductRepository;
import com.retailbilling.repos.UserRepository;
import com.retailbilling.utils.ApplicationConstants;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BillingServiceTests {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UserRepository userRepository;

	private BillingService billingService;

	private BillingRequest billingRequest;

	@BeforeEach
	void setup() {
		billingService = new BillingService(userRepository, productRepository);

		billingRequest = new BillingRequest();

		billingRequest.setOrderId("ab1d4f67-3b65-4222-a3f7-cfe603f18545");
		billingRequest.setUserId("ab1d4f67-3b65-4222-a3f7-cfe603f1859a");

		List<Item> orderItems = new ArrayList<>();

		Item item = new Item("e8811e36-231b-4dcd-bcfa-701c09938f64", 2);
		orderItems.add(item);

		item = new Item("6ad0a278-8cc9-460d-bea5-7607ea9f0399", 1);
		orderItems.add(item);

		billingRequest.setItems(orderItems);

	}

	@Test
	@DisplayName(value = "should throw an error, if user doesn't exist with en user id")
	void generateBillTestOne() {

		Mockito.when(userRepository.findById("ab1d4f67-3b65-4222-a3f7-cfe603f1859a")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			billingService.generateBill(billingRequest);
		}).isInstanceOf(ResourceNotFoundException.class).hasMessage("user don't exist with given userId");

	}

	@Test

	@DisplayName(value = "should throw an error, if one of productId of item is empty ot null")
	void generateBillTestTwo() {
		String accountCreatedOn = "2020-12-14";
		User user = new User("ab1d4f67-3b65-4222-a3f7-cfe603f1859a", "Raju", "EMPLOYEE", accountCreatedOn);

		Mockito.when(userRepository.findById("ab1d4f67-3b65-4222-a3f7-cfe603f1859a")).thenReturn(Optional.of(user));

		Mockito.when(productRepository.findById("e8811e36-231b-4dcd-bcfa-701c09938f64")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			billingService.generateBill(billingRequest);
		}).isInstanceOf(ResourceNotFoundException.class).hasMessage("product don't exist with given productId");

	}

	@Test
	@DisplayName(value = "should return bill amount with only fixed discount applied, if user is new customer")
	void generateBillTestThree() {

		String userId = "9f66fb0f-9fae-41ab-9727-f6e683ed94a8";
		User user = new User(userId, "Rahul", "CUSTOMER", "2020-12-14");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 120.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 180d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("400.08");
		} catch (ResourceNotFoundException e) {

		}

	}

	@Test
	@DisplayName(value = "should return bill amount without any discount applied, if user is new customer and order values is less than 100")
	void generateBillTestFour() {

		String userId = "9f66fb0f-9fae-41ab-9727-f6e683ed94a8";
		User user = new User(userId, "Rahul", "CUSTOMER", "2020-12-14");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 20.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();

			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("90.08");

		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with only percentage discount, if user is old customer and order values is less than 100")
	void generateBillTestFive() {

		String userId = "9f66fb0f-9fae-41ab-9727-f6e683ed94a8";
		User user = new User(userId, "Rahul", "CUSTOMER", "2015-12-14");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 20.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("87.58");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with both percentage discount and fixed discount applied, if user is old customer and order values is greater than 100")
	void generateBillTestSix() {

		String userId = "9f66fb0f-9fae-41ab-9727-f6e683ed94a8";
		User user = new User(userId, "Rahul", "CUSTOMER", "2015-12-14");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 120.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("277.58");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with both percentage discount and fixed discount applied, if user is affiliate and order values is greater than 100")
	void generateBillTestSeven() {

		String userId = "08b9009d-9669-4e8b-85fe-4be50f5b6ec9";
		User user = new User(userId, "Ram", "AFFILIATE", "2015-12-14");


		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 120.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("275.08");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with only percentage discount applied, if user is affiliate and order values is less than 100")
	void generateBillTestEight() {

		String userId = "08b9009d-9669-4e8b-85fe-4be50f5b6ec9";
		User user = new User(userId, "Ram", "AFFILIATE", "2015-12-14");


		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 20.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("85.08");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with both percentage discount and fixed discount applied, if user is employee and order values is greater than 100")
	void generateBillTestNine() {

		String userId = "ab1d4f67-3b65-4222-a3f7-cfe603f1859a";
		User user = new User(userId, "Raju", "EMPLOYEE", "2015-12-14");


		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 120.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue).toString()).hasToString("265.08");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill amount with only percentage discount applied, if user is employee and order values is less than 100")
	void generateBillTestTen() {

		String userId = "ab1d4f67-3b65-4222-a3f7-cfe603f1859a";
		User user = new User(userId, "Raju", "EMPLOYEE", "2015-12-14");


		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 20.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue)).hasToString("75.08");
		} catch (ResourceNotFoundException e) {

		}

	}
	
	@Test
	@DisplayName(value = "should return bill without discount applied, if user is unknown")
	void generateBillTestEleven() {

		String userId = "ab1d4f67-3b65-4222-a3f7-cfe603f1859a";
		User user = new User(userId, "Raju", "", "2015-12-14");


		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String productId1 = "e8811e36-231b-4dcd-bcfa-701c09938f64";
		Product product = new Product(productId1, "urad dal", "", ApplicationConstants.GROCERIES, 20.04d);
		Mockito.when(productRepository.findById(productId1)).thenReturn(Optional.of(product));

		String productId2 = "6ad0a278-8cc9-460d-bea5-7607ea9f0399";
		product = new Product(productId2, "iPhone", "", "Electronics", 50d);
		Mockito.when(productRepository.findById(productId2)).thenReturn(Optional.of(product));

		try {
			billingRequest.setUserId(userId);
			double expedtedValue = billingService.generateBill(billingRequest).getBillingAmount();
			Assertions.assertThat(df2.format(expedtedValue)).hasToString("90.08");
		} catch (ResourceNotFoundException e) {

		}

	}











}
