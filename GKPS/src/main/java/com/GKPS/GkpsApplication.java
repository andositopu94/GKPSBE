package com.GKPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataRedisAutoConfiguration.class, DataRedisRepositoriesAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.GKPS.Repository")
public class GkpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GkpsApplication.class, args);
	}

}
