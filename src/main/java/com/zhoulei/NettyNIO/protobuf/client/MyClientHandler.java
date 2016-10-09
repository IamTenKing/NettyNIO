package com.zhoulei.NettyNIO.protobuf.client;

import java.util.ArrayList;
import java.util.List;

import com.zhoulei.NettyNIO.protobuf.model.PersonPro;
import com.zhoulei.NettyNIO.protobuf.model.PersonPro.Preson;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyClientHandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//发送服务器消息
		PersonPro.Preson.Builder person= PersonPro.Preson.newBuilder();
		person.setAge(20);
		person.setName("aa");
		person.setHir("2015");
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		person.addAllList(list);
//		person.addList(1);
		person.setId(100);
		ctx.writeAndFlush(person);
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//读取服务器消息
		PersonPro.Preson person = (Preson) msg;
		System.out.println("id:"+person.getId());
		
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	

}
