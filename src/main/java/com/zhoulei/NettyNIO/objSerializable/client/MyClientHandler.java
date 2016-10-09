package com.zhoulei.NettyNIO.objSerializable.client;

import java.util.Date;

import com.zhoulei.NettyNIO.objSerializable.model.Person;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyClientHandler extends ChannelHandlerAdapter {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		Person person = new Person();
//		person.setName("aa");
//		person.setAge(20);
//		person.setHir(new Date());
//		ctx.writeAndFlush(person);
		System.out.println("收到服务器的消息："+msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			Person person = new Person();
			person.setName("aa");
			person.setAge(20);
			person.setHir(new Date());
			ctx.writeAndFlush(person);
		}
		
	}

}
