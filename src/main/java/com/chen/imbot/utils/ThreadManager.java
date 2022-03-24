package com.chen.imbot.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadManager {
	
	public static boolean offer(Runnable task) {
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("eee");
			}
		});
		return true;
	}
}
