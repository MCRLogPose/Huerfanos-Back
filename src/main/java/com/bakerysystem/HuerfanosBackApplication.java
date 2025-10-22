package com.bakerysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HuerfanosBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuerfanosBackApplication.class, args);
	}

}
