package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
					title = "Blog App",
					description = "Spring Boot Blog App with REST API",
					version = "v1.0",
					contact = @Contact(
							name = "Suresh",
							email = "asureshlingam1999@gmail.com"
							),
					license = @License(
							name = "Apache 2.0"
							)
				),
		externalDocs =@ExternalDocumentation(
					description = "Spring Boot Blog App Documentation",
					url = ""
				)
		)
public class SpringbootBlogRestApiApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		//SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
		SpringApplication app = new SpringApplication(SpringbootBlogRestApiApplication.class);
		app.setAdditionalProfiles("debug");
		app.run(args);
	}

}
