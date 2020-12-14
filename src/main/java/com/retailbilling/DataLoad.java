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

			User user = new User("ab1d4f67-3b65-4222-a3f7-cfe603f1859a", "Raju", "EMPLOYEE", "2020-12-14");
			userRepository.save(user);

			new User("08b9009d-9669-4e8b-85fe-4be50f5b6ec9", "Ram", "AFFLIATE", "2020-12-14");
			userRepository.save(user);

			new User("9f66fb0f-9fae-41ab-9727-f6e683ed94a8", "Rahul", "CUSTOMER", "2020-12-14");
			userRepository.save(user);

			new User("6e1869a2-0340-4d3c-a17d-b54c41fd2b7c", "Rahim", "CUSTOMER", "2015-12-14");
			userRepository.save(user);

			log.info("Loading product data...");

			Product product = new Product("e8811e36-231b-4dcd-bcfa-701c09938f64", "urad dal", "", "GROCERIES", 120.04);
			productRepository.save(product);
			
			product = new Product("9cbe56f3-cf7f-4c4a-a3ea-ba8e26f3593b", "cake", "", "GROCERIES", 20.04);
			productRepository.save(product);
			
			product = new Product("5b0efc30-9d99-4352-94b5-0b71e2f85abe", "oil", "", "GROCERIES", 100.04);
			productRepository.save(product);
			
			product = new Product("6ad0a278-8cc9-460d-bea5-7607ea9f0399", "iPhone", "", "ELECTRONICS", 98_00_000);
			productRepository.save(product);
			
			product = new Product("69715815-4775-445e-b7d1-6f86862018c8", "dell laptop", "", "ELECTRONICS", 68_00_000);
			productRepository.save(product);
			
			product = new Product("2802d606-5749-4d75-b6c9-7e2586ea0e86", "ITC wheat flour", "", "GROCERIES", 54);
			productRepository.save(product);


		} catch (Exception e) {
			log.error("Exception loading initial data...", e);
			e.printStackTrace();
		}
	}

}
