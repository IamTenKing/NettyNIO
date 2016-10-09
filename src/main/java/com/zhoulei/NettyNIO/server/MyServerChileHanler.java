package com.zhoulei.NettyNIO.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;

public class MyServerChileHanler extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel arg0) throws Exception {
//		arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
		arg0.pipeline().addLast(new StringDecoder());
		arg0.pipeline().addLast(new NettyServerHandler());
	}

	

	

}
