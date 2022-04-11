package com.chen.imbot.utils;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskManager {
	public static TaskManager INSTANCE = null;
	private Queue<TaskAdapter> queue;
	private boolean running = true;
	public TaskManager() {
		running = true;
		queue = new PriorityBlockingQueue<TaskAdapter>();
	}
	public synchronized static TaskManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TaskManager();
		}
		return INSTANCE;
	}
	public static boolean offer(TaskAdapter task) {
		TaskManager manager = getInstance();
		return manager.addTask(task);
	}
	public synchronized boolean addTask(TaskAdapter task) {
		int queueSize = queue.size();
		log.info("queue length: {}", queueSize);
		return queue.add(task);
	}
	public void process() {
		while (running) {
			synchronized (this) {
				if (!queue.isEmpty()) {
					log.info("start process");
					TaskAdapter first = queue.peek();
					try {
						new Thread(first).start();
					} catch (Exception e) {
						log.error("excuete task error: {}", e.getMessage());
					}
				}
			}
		}
	}
	public void stopProcess() {
		running = false;
	}
}
