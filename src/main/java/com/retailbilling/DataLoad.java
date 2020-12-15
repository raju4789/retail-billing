package com.retailbilling;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.retailbilling.models.Product;
import com.retailbilling.models.User;
import com.retailbilling.repos.ProductRepository;
import com.retailbilling.repos.UserRepository;
import com.retailbilling.utils.ApplicationConstants;

@Component
class DataLoadListener implements ApplicationListener<ApplicationReadyEvent> {

	private static Logger log = Logger.getLogger(DataLoadListener.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		try {
			log.info("Loading user data...");
			
			String accountCreatedOn = "2020-12-14";

			User user = new User("ab1d4f67-3b65-4222-a3f7-cfe603f1859a", "Raju", "EMPLOYEE", accountCreatedOn);
			userRepository.save(user);

			new User("08b9009d-9669-4e8b-85fe-4be50f5b6ec9", "Ram", "AFFILIATE", accountCreatedOn);
			userRepository.save(user);

			new User("9f66fb0f-9fae-41ab-9727-f6e683ed94a8", "Rahul", "CUSTOMER", accountCreatedOn);
			userRepository.save(user);

			new User("6e1869a2-0340-4d3c-a17d-b54c41fd2b7c", "Rahim", "CUSTOMER", "2015-12-14");
			userRepository.save(user);

			log.info("Loading product data...");
			
			String productCategory = ApplicationConstants.GROCERIES;

			Product product = new Product("e8811e36-231b-4dcd-bcfa-701c09938f64", "urad dal", "", productCategory, 120.04);
			productRepository.save(product);
			
			product = new Product("7802d606-5749-4d75-b6c9-7e2586ea0e86", "ITC wheat flour", "", productCategory, 54);
			productRepository.save(product);
			
			productCategory = "ELECTRONICS";
			
			product = new Product("6ad0a278-8cc9-460d-bea5-7607ea9f0399", "iPhone", "", productCategory, 9800000);
			productRepository.save(product);
			
			product = new Product("97dde3e7-9437-4410-a121-53ba186f0112", "dell laptop", "", productCategory, 6800000);
			productRepository.save(product);
			
			product = new Product("17deb204-f989-4b4c-b4af-4dc337dee228", "MI smart watch", "", productCategory, 6800);
			productRepository.save(product);
			
			productCategory = "CLOTHING";
			
			product = new Product("06788f63-3a25-4708-87b8-9a5586da8e0e", "red t shirt", "", productCategory, 6300);
			productRepository.save(product);
			
			product = new Product("701b1b95-93b1-4049-a278-38eebae4c4fc", "black shoes", "", productCategory, 8800);
			productRepository.save(product);
			
		} catch (Exception e) {
			log.error("Exception loading initial data...", e);
		}
	}

}
