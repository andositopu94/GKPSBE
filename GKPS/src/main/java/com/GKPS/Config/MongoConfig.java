package com.GKPS.Config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient, @Value("${spring.data.mongodb.database:gkps}") String databaseName) {
        return new MongoTemplate(mongoClient, databaseName);
    }
}
