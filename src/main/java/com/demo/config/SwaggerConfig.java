package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {//this class is using for customize our Swagger UI

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Blog Application : API Documentation", "This project is developed by Me", "1.0", "Terms of Service", new Contact("API", "http://localhost:8080", "dummymike@gmail.com"), "License of APIs", "API license URL", Collections.emptyList());
    }
}
