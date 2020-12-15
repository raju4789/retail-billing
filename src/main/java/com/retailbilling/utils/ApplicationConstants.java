package com.retailbilling.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApplicationConstants {

	public static final String EMPLOYEE = "EMPLOYEE";
	public static final String AFFILIATE = "AFFILIATE";
	public static final String CUSTOMER = "CUSTOMER";

	public static final String GROCERIES = "GROCERIES";

	private static Map<String, Double> mutableMap = new HashMap<>();
	static {

		mutableMap.put(EMPLOYEE, 30.0);
		mutableMap.put(AFFILIATE, 10.0);
		mutableMap.put(CUSTOMER, 5.0);

	}

	public static final Map<String, Double> USER_PERCENTAGE_DISCOUNT = Collections.unmodifiableMap(mutableMap);
	public static final double USER_FIXED_DISCOUNT = 5.0;
	public static final double FIXED_DISCOUNT_APPLICABLE_BILL = 100.0;

}
