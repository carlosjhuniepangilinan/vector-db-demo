package com.telus.ai.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VectorDbDemoApplication {

	public static void main(String[] args) {
		var app = SpringApplication.run(VectorDbDemoApplication.class, args);
		Arrays.asList(app.getBeanDefinitionNames()).stream()
			.forEach(System.out::println);
	}

}
