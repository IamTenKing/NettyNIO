package com.zhoulei.NettyNIO.marshalling.client;

import java.util.ArrayList;
import java.util.List;

import com.zhoulei.NettyNIO.marshalling.model.Person;
import com.zhoulei.NettyNIO.marshalling.model.Student;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyClientHandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Person person = new Person();
		person.setName("aa");
		person.setAge(25);
		Student student = new Student();
		student.setXuhao(1);
		List<Student> list = new ArrayList<Student>();
		list.add(student);
		person.setList(list);
		ctx.writeAndFlush(person);
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Person person = (Person) msg;
		System.out.println("服务器返回消息："+person.getName()+","+person.getAge());
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
