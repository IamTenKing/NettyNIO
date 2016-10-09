package com.zhoulei.NettyNIO.objSerializable.server;

import java.util.Date;

import com.zhoulei.NettyNIO.objSerializable.model.Person;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyServerHandler extends ChannelHandlerAdapter {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("收到客户的消息："+msg);
		Person person = new Person();
		person.setName("aa");
		person.setAge(20);
		person.setHir(new Date());
		ctx.writeAndFlush(person);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
