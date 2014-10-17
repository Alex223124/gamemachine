package com.game_machine.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import GameMachine.Messages.ClientMessage;
import akka.actor.ActorSelection;

import com.game_machine.core.NetMessage;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private ActorSelection inbound;
	public ChannelHandlerContext context = null;
	private static final Logger logger = LoggerFactory.getLogger(UdpClientHandler.class);
	
	public UdpClientHandler(String actorName) {
		this.inbound = Api.getActorByName(actorName);
	}
	
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
    	byte[] bytes = new byte[msg.content().readableBytes()];
		msg.content().readBytes(bytes);

		ClientMessage clientMessage = ClientMessage.parseFrom(bytes);
		this.inbound.tell(clientMessage,null);
    }

    @Override
	public void channelActive(final ChannelHandlerContext ctx) {
    	this.context = ctx;
		logger.info("UDP channel active");
	}
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.warn("UDP exception caught");
        cause.printStackTrace();
        ctx.close();
    }
}