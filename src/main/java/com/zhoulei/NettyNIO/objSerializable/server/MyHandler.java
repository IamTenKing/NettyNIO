package com.zhoulei.NettyNIO.objSerializable.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyHandler extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel arg0) throws Exception {
		arg0.pipeline().addLast(new ObjectDecoder(1024*1024,
								ClassResolvers.weakCachingConcurrentResolver(this.getClass()
																			.getClassLoader())))
						.addLast(new ObjectEncoder())
						.addLast(new MyServerHandler());
						
		
	}
	

}
