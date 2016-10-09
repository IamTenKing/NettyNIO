package com.zhoulei.nettyRPC.common;

import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


public class NettyMessageEncodeer extends MessageToMessageEncoder<NettyMessage> {

	private MyObjectEncoder encoder;
	public NettyMessageEncodeer(ObjectEncoder encoder) {
		this.encoder = new MyObjectEncoder();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage message, List<Object> out) throws Exception {
		
		if(message == null || message.getHeader()==null)
			throw new Exception("数据是 null");
		ByteBuf sendBuf = Unpooled.buffer();
		sendBuf.writeInt(message.getHeader().getCrcCode());
		sendBuf.writeInt(message.getHeader().getLength());
		sendBuf.writeLong(message.getHeader().getSessionId());
		sendBuf.writeByte(message.getHeader().getType());
		sendBuf.writeByte(message.getHeader().getPriority());
		sendBuf.writeInt(message.getHeader().getAttachment().size());
		String key = null;
		byte[] keyArray = null;
		message = null;
		for (Map.Entry<String, Object> param : message.getHeader().getAttachment().entrySet()) {
			key = param.getKey();
			keyArray = key.getBytes("utf-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			message = (NettyMessage) param.getValue();
			encoder.encode(ctx, message,sendBuf);
//			marshallingEncoder.encode(value,sendBuf);
			
		}
		key = null;
		keyArray = null;
		message = null;
		if(message.getBody() != null){
			encoder.encode(ctx, message,sendBuf);
		}else{
			sendBuf.writeInt(0);
			sendBuf.setInt(4, sendBuf.readableBytes());
		}
	}

}
