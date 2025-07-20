package com.example.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableFeignClients
@SpringBootApplication
public class   EcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);


	}
	@Bean
	public PlatformTransactionManager Manager(MongoDatabaseFactory dbFcatory){
		return new MongoTransactionManager(dbFcatory);
	}

}
