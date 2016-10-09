package com.zhoulei.NettyNIO.protobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProtoBufServer {

	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup boosEventGroup = new NioEventLoopGroup();
		EventLoopGroup workEventGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boosEventGroup, workEventGroup)
						.channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, 1024)
						.childHandler(new MyHandler());
		ChannelFuture sync = serverBootstrap.bind(9000).sync();
		System.out.println("服务器启动");
		sync.channel().closeFuture().sync();
		boosEventGroup.shutdownGracefully();
		workEventGroup.shutdownGracefully();

	}

}
