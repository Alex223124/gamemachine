package com.game_machine.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UdpClient implements Runnable, NetworkClient {

	private static final Logger logger = LoggerFactory.getLogger(UdpClient.class);
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	private static final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel = null;
    private UdpClientHandler handler;
    private String host;
    private int port;
    private InetSocketAddress remote;
    
    public UdpClient(String host, int port, String actorName) {
    	this.host = host;
    	this.port = port;
    	handler = new UdpClientHandler(actorName);
    	remote = new InetSocketAddress(host,port);
    }
    
    public void sendMessage(byte[] bytes) {
    	if (channel == null) {
    		logger.info("sendMessage: Channel not active");
    		return;
    	}
    	ByteBuf buf = Unpooled.wrappedBuffer(bytes);
		DatagramPacket packet = new DatagramPacket(buf, remote);
		channel.writeAndFlush(packet);
    }
    
    public void stop() {
    	channel.close();
		try {
			channel.closeFuture().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void start() {
    	executor.execute(this);
    }
    
    public void run() {

        
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, false)
             .handler(handler);

            channel = b.bind(0).sync().channel();
            logger.info("Channel started on "+host+":"+port);
            channel.closeFuture().await();
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            group.shutdownGracefully();
        }
    }

	public Channel getChannel() {
		return channel;
	}

}