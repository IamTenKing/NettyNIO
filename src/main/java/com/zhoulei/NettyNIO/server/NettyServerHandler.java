package com.zhoulei.NettyNIO.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyServerHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("接收客户端指令");
		String str = (String) msg;
		
//		ByteBuf byteBuf = (ByteBuf) msg;
//		int readableBytes = byteBuf.readableBytes();
//		byte[] bytes = new byte[readableBytes];
//		byteBuf.readBytes(bytes);
//		String str = new String(bytes,"utf-8");
		System.out.println("服务器接收："+str);
		
		String nowTime = str.equalsIgnoreCase("queryTime")? new Date(System.nanoTime()).toString():"error";
		ByteBuf writeByf = Unpooled.copiedBuffer(nowTime.getBytes());
		ctx.writeAndFlush(writeByf);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	

}
