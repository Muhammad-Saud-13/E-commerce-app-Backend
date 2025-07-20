package com.example.ecom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.ecom.repository")
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    String mongodbUri;

    @Value("${spring.data.mongodb.database}")
    String dbName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongodbUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), dbName);
    }
}