package com.zhoulei.NettyNIO.objSerializable.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ObjSerializableNettyServer {
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossLoopgroup = new NioEventLoopGroup();
		EventLoopGroup workLoopgroup = new NioEventLoopGroup();	
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossLoopgroup, workLoopgroup)
							.channel(NioServerSocketChannel.class)
							.option(ChannelOption.SO_BACKLOG, 10)
							.handler(new LoggingHandler(LogLevel.DEBUG))
							.childHandler(new MyHandler());
			ChannelFuture sync = serverBootstrap.bind(9000).sync();
			System.out.println("服务器启动");
			sync.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			bossLoopgroup.shutdownGracefully();
			workLoopgroup.shutdownGracefully();
		}
		
	}

}
