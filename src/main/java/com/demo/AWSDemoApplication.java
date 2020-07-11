package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class AwsDemoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(AwsDemoApplication.class);
	
	public static void main(String[] args) {
		LOG.debug("Starting the Main Application");
		SpringApplication.run(AwsDemoApplication.class, args);
	}
	
}
