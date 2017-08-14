package com.example.demo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerController {

	private final String NAME = "Artur Goralczyk";
	private final String URL = "https://github.com/chomzi/Java";
	private final String EMAIL = "arczi130@gmail.com";
	
	@Bean
	public Docket api() {                
    		return new Docket(DocumentationType.SWAGGER_2)          
      			.select()
      			.apis(RequestHandlerSelectors.basePackage("com.example.demo.controllers"))
      			.build()
      			.apiInfo(apiInfo());
	}
	
	private Contact contact = new Contact(NAME, URL, EMAIL);
	
	private ApiInfo apiInfo() {
    		ApiInfo apiInfo = new ApiInfo(
      			"Artur Goralczyk REST API",
      			"",
      			"API TOS",
      			"Terms of service",
      			contact,
      			"License of API",
      			"API license URL");
    		return apiInfo;
	}

}
