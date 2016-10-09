package com.zhoulei.NettyNIO.marshalling.client;

import com.zhoulei.NettyNIO.marshalling.serializable.MarshallingCodeCFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		
		arg0.pipeline().addLast(new MarshallingCodeCFactory().bulidMarshallingDecoder());
		arg0.pipeline().addLast(new MarshallingCodeCFactory().bulidMarshallingEncoder());
		arg0.pipeline().addLast(new MyClientHandler());

	}

}
