package com.zhoulei.NettyNIO.protobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProtoBufClient {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(loopGroup)
				  .channel(NioSocketChannel.class)
				  .option(ChannelOption.TCP_NODELAY, true)
				  .handler(new Myhandler());
		ChannelFuture sync = bootstrap.connect("127.0.0.1",9000).sync();
		sync.channel().closeFuture().sync();
		loopGroup.shutdownGracefully();

	}

}
