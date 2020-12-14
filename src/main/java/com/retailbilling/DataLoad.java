package com.retailbilling;

import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
class DataLoadListener implements ApplicationListener<ApplicationReadyEvent> {

	private static Logger log = Logger.getLogger(DataLoadListener.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Loading initial stocks data...");
		try {
			
		} catch (Exception e) {
			log.error("Exception loading initial stocks data...", e);
			e.printStackTrace();
		}
	}

}
