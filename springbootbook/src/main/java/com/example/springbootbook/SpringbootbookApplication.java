package com.example.springbootbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringbootbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootbookApplication.class, args);
	}

}
