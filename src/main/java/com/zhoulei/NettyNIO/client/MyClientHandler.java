package com.zhoulei.NettyNIO.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyClientHandler extends ChannelHandlerAdapter {

	private  byte[] bytes;
//	private ByteBuf byteBuf;
	public MyClientHandler() {
		bytes = "queryTime".getBytes();
//		byteBuf = Unpooled.buffer(bytes.length);
//		byteBuf.writeBytes(bytes);
//		msg = Unpooled.buffer(bytes.length);
//		msg.writeBytes(bytes);
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf buf = Unpooled.buffer(bytes.length);
		buf.writeBytes(bytes);
		ctx.writeAndFlush(buf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("等待服务器返回消息");
		String str = (String) msg;
//		ByteBuf byteBuf = (ByteBuf) msg;
//		byte[] bytes = new byte[byteBuf.readableBytes()];
//		byteBuf.readBytes(bytes);
//		String str = new String(bytes,"utf-8");
		System.out.println("服务器返回的消息："+str);
	}
	

}
