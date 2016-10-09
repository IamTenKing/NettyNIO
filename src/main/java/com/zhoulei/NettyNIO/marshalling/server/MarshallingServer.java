package com.zhoulei.NettyNIO.marshalling.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MarshallingServer {

	public static void main(String[] args) throws InterruptedException {

		new MarshallingServer().start();
	}
	
	public  void start() throws InterruptedException{
		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup)
					   .channel(NioServerSocketChannel.class)
					   .option(ChannelOption.SO_BACKLOG, 1024)
					   .handler(new LoggingHandler(LogLevel.INFO))
					   .childHandler(new MyHandler());
		ChannelFuture sync = serverBootstrap.bind(9000).sync();
		System.out.println("服务器启动");
		sync.channel().closeFuture().sync();
		bossEventLoopGroup.shutdownGracefully();
		workEventLoopGroup.shutdownGracefully();
	}

}
