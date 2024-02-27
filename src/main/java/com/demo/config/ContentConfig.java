package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ContentConfig implements WebMvcConfigurer {// add only dependency then give xml but if you want both then specify here

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {// coming this method from above class
        configurer.favorParameter(true)
                .parameterName("mediaType")
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
}
// given url in ? query parameter like = ?mediaType=xml or json