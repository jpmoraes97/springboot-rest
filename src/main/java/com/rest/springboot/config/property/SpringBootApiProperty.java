package com.rest.springboot.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties("springboot")
public class SpringBootApiProperty {
	
	private final Seguranca seguranca = new Seguranca();
	private final Mail mail = new Mail();
	
	@Getter
	@Setter
	public static class Seguranca{
		
		private boolean enableHttps;
	}
	
	@Getter
	@Setter
	public static class Mail {
		
		private String host;
		private int port;
		private String username;
		private String password;
	}
	

}
