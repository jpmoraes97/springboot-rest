package com.rest.springboot;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rest.springboot.config.property.SpringBootApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(SpringBootApiProperty.class)
public class SpringbootApplication {
	
   
	
	@PostConstruct
	  public void init(){
	    // Setting Spring Boot SetTimeZone
	    TimeZone.setDefault(TimeZone.getTimeZone("America/Brasilia"));
	  }
	
	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
