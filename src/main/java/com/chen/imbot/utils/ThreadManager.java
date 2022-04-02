package com.chen.imbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				log.info("thread start");
				File file = new File("/home/chen/work/Minx_20181226_single.tar.gz");
				Long fileLen = file.length();
				byte[] fileByte = new byte[fileLen.intValue()];
				try {
					InputStream fis = new FileInputStream(file);
					fis.read(fileByte);
					fis.close();
					Thread.sleep(10000);
				} catch (Exception e) {
					log.error("error :{}", e.getMessage());
				}
				Thread.interrupted();
				log.info("thread stop");
			}
		}).start();
	}
}
