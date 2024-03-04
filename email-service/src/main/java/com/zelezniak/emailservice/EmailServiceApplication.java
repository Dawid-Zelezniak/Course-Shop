package com.zelezniak.emailservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}


	@Bean
	public MessageConverter messageConverter(){
		ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
		return new Jackson2JsonMessageConverter(mapper);
	}
}
