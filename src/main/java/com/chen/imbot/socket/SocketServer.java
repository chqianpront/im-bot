package com.chen.imbot.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketServer implements ApplicationRunner {
	public static int PACKAGE_MAX_LENGTH = 1024 * 1024 * 1024;
	@Value("${taskflow.tcp.port}")
	private int tcpPort;
	
	ServerBootstrap bootstrap;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("starting socket server");
		startServer();
	}
	public void startServer() throws Exception {
		bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(PACKAGE_MAX_LENGTH, 0, 4, 0, 4));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new StringEncoder());
				ch.pipeline().addLast(new ChannelHandler());
			}
		})
		.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.bind(tcpPort).sync();
	}
}
