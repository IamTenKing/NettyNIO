package com.zhoulei.nettyRPC.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

public class MyObjectDecoder extends ObjectDecoder {

	public MyObjectDecoder() {
		super(1024*1024, ClassResolvers.weakCachingConcurrentResolver(NettyMessage.class
				.getClassLoader()));
	}


	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		int objectSize = in.readInt();
		ByteBuf buf = in.slice(in.readInt(),objectSize);
		Object decode = super.decode(ctx, in);
		in.readerIndex(in.readInt()+objectSize);
		return decode;
	}
	
	

	
	

}
