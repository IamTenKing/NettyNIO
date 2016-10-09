package com.zhoulei.NettyNIO.protobuf.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhoulei.NettyNIO.protobuf.model.PersonPro;
import com.zhoulei.NettyNIO.protobuf.model.PersonPro.Preson;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyServerHandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//读取客户端的数据
		PersonPro.Preson preson = (Preson) msg; 
		System.out.println("name:"+preson.getName()+",list:"+preson.getList(0)
				+",id:"+preson.getId());
		
		//回写给客户端数据
		PersonPro.Preson.Builder preson2 = PersonPro.Preson.newBuilder();
		preson2.setAge(25);
		preson2.setName("bb");
		preson2.setContext("bbb");
		preson2.setHir("2016");
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		preson2.addAllList(list);
//		preson2.addList(2);
		ctx.writeAndFlush(preson2);
		
		
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
