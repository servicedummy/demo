package com.demo;

import com.demo.controller.PostController;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

//	@GetMapping("/{name}")
//	public String getName(@PathVariable String name){
//		logger.error("fail");
//		if (name.equalsIgnoreCase("test")){
//			throw new RuntimeException("failed");
//		}
//		String response = "hello" + name+ "friend";
//		return response;
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
