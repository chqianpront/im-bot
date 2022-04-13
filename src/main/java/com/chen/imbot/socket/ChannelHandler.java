package com.chen.imbot.socket;

import com.chen.imbot.basemodel.dto.ModelForSocket;
import com.chen.imbot.usercenter.model.User;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelHandler extends SimpleChannelInboundHandler<String> {
	private SocketServer server;
	public ChannelHandler (SocketServer server) {
		this.server = server;
	}
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		log.info("registry channel: {}", ctx.channel().id());
//		server.setChannel(ctx.channel());
//		super.channelActive(ctx);
//	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("unregistry channel: {}", ctx.channel().id());
//		server.removeChannel(ctx.channel());
		super.channelInactive(ctx);
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		log.info("receive message: {}", msg);
		Gson gson = new Gson();
		ModelForSocket m = gson.fromJson(msg, ModelForSocket.class);
		int actionType = m.getActionType();
		if (actionType == ModelForSocket.PUBLISH) {
			
		} else if (actionType == ModelForSocket.SUBSCRIBE) {
			User subUser = User.class.cast(m.getData());
			server.setChannel(subUser.getUserChannel(), ctx.channel());
		} else if (actionType == ModelForSocket.UNSUBSCRIBE) {
			User unsubUser = User.class.cast(m.getData());
			server.removeChannel(unsubUser.getUserChannel());
		} else {
			log.error("not recognize action type");
		}
	}
}
