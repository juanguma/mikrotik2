package com.gestion.mikrotik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MikrotikApplication {

	public static void main(String[] args) {
		SpringApplication.run(MikrotikApplication.class, args);

		System.out.println("Running OK");
	}



}
