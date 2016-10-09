package com.zhoulei.NettyNIO.marshalling.server;

import com.zhoulei.NettyNIO.marshalling.model.Person;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyServerHandle extends ChannelHandlerAdapter {

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//接收客户端数据
		Person person = (Person) msg;
		System.out.println("客户端发送的数据：name："+person.getName()+",age:"+
							person.getAge()+"student:"+person.getList().get(0));
		//回写个客户端数据
		person.setAge(25);
		person.setName("bb");
		ctx.writeAndFlush(person);
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.getStackTrace();
		ctx.close();
	}
	
	

}
