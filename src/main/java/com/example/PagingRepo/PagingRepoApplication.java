package com.example.PagingRepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.example", "com.example.Controller","com.example.DAO"})
@EnableJpaRepositories("com.example.DAO")
@EntityScan("com.example.Entity")
public class PagingRepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagingRepoApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
	    return builder.build();
	}


}
