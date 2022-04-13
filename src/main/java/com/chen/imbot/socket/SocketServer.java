package com.chen.imbot.socket;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketServer implements ApplicationRunner, DisposableBean {
	public static int PACKAGE_MAX_LENGTH = 1024 * 1024 * 1024;
	public static SocketServer INSTANCE;
	@Value("${taskflow.tcp.port}")
	private int tcpPort;
	private Map<String, Channel> channelMap;
	ServerBootstrap bootstrap;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("starting socket server");
		channelMap = new ConcurrentHashMap<>();
		startServer();
	}
	public static SocketServer getInstance() {
		return INSTANCE;
	}
	public Channel getChannel(String userCh) {
		return channelMap.get(userCh);
	}
	public void setChannel(Channel channel) {
		channelMap.put(channel.id().toString(), channel);
	}
	public void setChannel(String userCh, Channel channel) {
		channelMap.put(userCh, channel);
	}
	public void removeChannel(Channel channel) {
		channelMap.remove(channel.id().toString());
	}
	public void removeChannel(String userCh) {
		channelMap.remove(userCh);
	}
	public void publicToAllChannel(String msg) {
		for (Entry<String, Channel> entry : channelMap.entrySet()) {
			sendData(entry.getValue(), msg);
		}
	}
	public void sendData(Channel channel, String msg) {
		log.info("send data msg: {}", msg);
		try {
			channel.writeAndFlush(msg);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
		}
	}
	public void startServer() throws Exception {
		SocketServer server = this;
		bootstrap = new ServerBootstrap();
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(2);
		bootstrap.group(bossGroup, workerGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(PACKAGE_MAX_LENGTH, 0, 4, 0, 4));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new StringEncoder());
				ch.pipeline().addLast(new ChannelHandler(server));
			}
		})
		.childOption(ChannelOption.SO_KEEPALIVE, true);
		ChannelFuture f = bootstrap.bind(tcpPort).sync();
		if (f.isSuccess()) {
			log.info("socket init success");
			INSTANCE = server;
		}
	}
	@Override
	public void destroy() throws Exception {
		log.info("application destroy");
		INSTANCE.destroy();
	}
}
