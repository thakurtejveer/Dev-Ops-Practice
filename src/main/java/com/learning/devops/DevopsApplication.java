package com.learning.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevopsApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevopsApplication.class, args);
		System.out.println("Hello, World from Dockerized Container! ");
	}
}
