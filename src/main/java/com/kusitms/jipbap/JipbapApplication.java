package com.kusitms.jipbap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EnableAsync
//@EnableWebSocketMessageBroker
//@EnableRedisRepositories
@SpringBootApplication
public class JipbapApplication {

	public static void main(String[] args) {
		SpringApplication.run(JipbapApplication.class, args);
	}

}
