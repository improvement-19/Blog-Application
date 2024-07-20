package com.suvankar.blogapis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(new Info().title("Blogging Application").description("This is blogging Application developed").version("1.0").contact(new Contact().name("suvankar").email("suvankarbanerjee815@gmail.com")).license(new License().name("Apache"))).externalDocs(new ExternalDocumentation().description("Learn building blogging Application"));
	}
}
