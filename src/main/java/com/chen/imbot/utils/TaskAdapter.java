package com.chen.imbot.utils;

public class TaskAdapter implements Runnable {
	private Runnable exact;
	public TaskAdapter(Runnable exact) {
		this.exact = exact;
	}
	@Override
	public void run() {
		exact.run();
	}
}
