package com.zhoulei.NettyNIO.objSerializable.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyHandler extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel arg0) throws Exception {

		arg0.pipeline().addLast(new ObjectDecoder(1024,ClassResolvers.
																	weakCachingConcurrentResolver(this.getClass().getClassLoader())));
		arg0.pipeline().addLast(new ObjectEncoder());
		arg0.pipeline().addLast(new MyClientHandler());
	}

}
