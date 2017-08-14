package com.example.demo;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.utils.PersonDatabase;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		
		//UUID uuid = UUID.randomUUID();
		//System.out.println(uuid);
		SpringApplication.run(RestApplication.class, args);
		
		PersonDatabase.connect();
	}
}
