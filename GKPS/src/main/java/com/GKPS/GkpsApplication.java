package com.GKPS;

import com.GKPS.Service.InfoGerejaService;
import com.GKPS.Service.JadwalKonselingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.GKPS.Repository")
@ComponentScan(basePackages = "com.GKPS")
@Import({InfoGerejaService.class, JadwalKonselingService.class})
public class GkpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GkpsApplication.class, args);
	}

}
