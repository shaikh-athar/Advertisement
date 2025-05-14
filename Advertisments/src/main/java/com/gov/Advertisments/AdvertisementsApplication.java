package com.gov.Advertisments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AdvertisementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertisementsApplication.class, args);
	}
}
