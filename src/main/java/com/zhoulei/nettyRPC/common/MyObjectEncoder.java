package com.zhoulei.nettyRPC.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyObjectEncoder extends ObjectEncoder{
	private static final byte[] bytes= new byte[4];

	public void encode(ChannelHandlerContext ctx, NettyMessage message, ByteBuf sendBuf) throws Exception {
		int lengthPos = sendBuf.writerIndex();
		sendBuf.writeBytes(bytes);
		super.encode(ctx, message, sendBuf);
		sendBuf.setIndex(lengthPos,sendBuf.writerIndex()-lengthPos-4);
	}

}
