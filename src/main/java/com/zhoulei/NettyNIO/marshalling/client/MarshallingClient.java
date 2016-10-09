package com.zhoulei.NettyNIO.marshalling.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MarshallingClient {
	
	
	public static void main(String[] args) throws InterruptedException {
		start();
	}

	
	public static void start() throws InterruptedException{
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(eventLoopGroup)
			     .channel(NioSocketChannel.class)
			     .option(ChannelOption.TCP_NODELAY, true)
			     .handler(new MyHandler());
		ChannelFuture sync = bootstrap.connect("127.0.0.1",9000).sync();
		sync.channel().closeFuture().sync();
		eventLoopGroup.shutdownGracefully();
	}
}
