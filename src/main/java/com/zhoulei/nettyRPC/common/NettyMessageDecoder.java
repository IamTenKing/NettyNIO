package com.zhoulei.nettyRPC.common;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{

	private MyObjectDecoder myObjectDecoder;
	public NettyMessageDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
		myObjectDecoder = new MyObjectDecoder();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if(frame == null)
			return null;
		NettyMessage nettyMessage = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(in.readInt());
		header.setLength(in.readInt());
		header.setSessionId(in.readLong());
		header.setType(in.readByte());
		header.setPriority(in.readByte());
		
		int size = in.readInt();
		if(size > 0){
			Map<String, Object> accth = new HashMap<String, Object>(size);
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for (int i = 0;i<size;i++) {
				keySize = in.readInt();
				keyArray = new byte[keySize];
				in.readBytes(keyArray);
				key = new String(keyArray,"utf-8");
				accth.put(key, myObjectDecoder.decode(ctx, in));
			}
			key = null;
			keyArray = null;
			header.setAttachment(accth);
			
		}
		if(in.readableBytes() > 4){
			nettyMessage.setBody(myObjectDecoder.decode(ctx, in));
		}
		nettyMessage.setHeader(header);
		return nettyMessage;
	}

	
	
}
