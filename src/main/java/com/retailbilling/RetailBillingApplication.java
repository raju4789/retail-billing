package com.retailbilling;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RetailBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailBillingApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/v1/*"))
				.apis(RequestHandlerSelectors.basePackage("com.retailbilling"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Retail Billing Service", 
				"Project built to apply discounts on user bill.", 
				"1.0", 
				"Free to use", 
				new springfox.documentation.service.Contact("Raju MLN", "https://www.linkedin.com/in/raju-m-l-n/", "narasimha4789@gmail.com"), 
				"API License", 
				"", 
				Collections.emptyList());
		
	}

}
