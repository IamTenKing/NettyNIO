package com.zhoulei.NettyNIO.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyClient{

	public static void main(String[] args) throws InterruptedException {

		Bootstrap client = new Bootstrap();
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		try {
			client.group(loopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel arg0) throws Exception {
//							arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
							arg0.pipeline().addLast(new StringDecoder());
							arg0.pipeline().addLast(new MyClientHandler());
						}
					});
			ChannelFuture sync = client.connect("127.0.0.1",9000).sync();
			System.out.println("连接服务器成功");
			sync.channel().closeFuture().sync();
		} finally{
			loopGroup.shutdownGracefully();
		}
		
	}

}
