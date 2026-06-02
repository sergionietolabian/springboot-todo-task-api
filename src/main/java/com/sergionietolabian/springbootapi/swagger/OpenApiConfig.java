package com.sergionietolabian.springbootapi.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task API")
                        .version("1.0")
                        .description("API de gestión de tareas con Spring Boot, DTOs y validaciones")
                        .contact(new Contact()
                                .name("Sergio Nieto")
                                .email("email@email.com")));
    }
}