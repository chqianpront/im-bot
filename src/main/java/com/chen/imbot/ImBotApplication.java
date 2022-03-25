package com.chen.imbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chen.imbot.*.dao")
public class ImBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImBotApplication.class, args);
	}

}
