package com.chen.imbot.starter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chen.imbot.utils.TaskManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Starter implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("start process");
		TaskManager.getInstance().process();
	}
}
