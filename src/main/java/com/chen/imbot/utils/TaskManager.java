package com.chen.imbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskManager {
	public static TaskManager INSTANCE = null;
	private Queue<Task> queue;
	private boolean running = true;
	public TaskManager() {
		running = true;
		queue = new PriorityBlockingQueue<Task>();
	}
	public synchronized static TaskManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TaskManager();
		}
		return INSTANCE;
	}
	public static boolean offer(Task task) {
		TaskManager manager = getInstance();
		return manager.addTask(task);
	}
	public synchronized boolean addTask(Task task) {
		int queueSize = queue.size();
		log.info("queue length: {}", queueSize);
		return queue.add(task);
	}
	public void process() {
		while (running) {
			synchronized (this) {
				if (!queue.isEmpty()) {
					log.info("start process");
					Task first = queue.peek();
					try {
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
	}
	public void stopProcess() {
		running = false;
	}
}
