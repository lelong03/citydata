package com.sap.citydata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class CitydataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitydataApplication.class, args);
	}

}
