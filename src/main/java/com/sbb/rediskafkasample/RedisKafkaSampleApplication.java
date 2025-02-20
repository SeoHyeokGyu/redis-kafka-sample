package com.sbb.rediskafkasample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisKafkaSampleApplication {

	private static final Logger log = LoggerFactory.getLogger(RedisKafkaSampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedisKafkaSampleApplication.class, args);
		log.debug("test");
		log.debug("test2");
	}

}
