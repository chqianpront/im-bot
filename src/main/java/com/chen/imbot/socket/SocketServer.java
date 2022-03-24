package com.chen.imbot.socket;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;

@Component
public class SocketServer implements ApplicationRunner {
	ServerBootstrap bootstrap;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	}
	public void startServer() throws Exception {
		bootstrap = new ServerBootstrap();
		bootstrap.bind().sync();
	}
}
