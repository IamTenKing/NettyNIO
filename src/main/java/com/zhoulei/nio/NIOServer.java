package com.zhoulei.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

public class NIOServer implements Runnable{
	
	public static void main(String[] args) {
		NIOServer server = new NIOServer(9000);
		new Thread(server,"NIOSerer").start();
	}
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	
	private volatile boolean sotp;
	

	public NIOServer(int port) {
		try {
			//打开选择器
			selector = Selector.open();
			//打开serverSocketChannel
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
			System.out.println("服务器启动");
			serverSocketChannel.configureBlocking(false);
			//注册选择器连接事件
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			System.exit(1);
		}
	}
	
	public void stop(){
		this.sotp=true;
		
	}


	public void run() {
		while(!sotp){
			try {
				//选择器阻塞1000毫秒没有事件
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					} catch (Exception e2) {
						if(key != null){
							key.cancel();
							if(key.channel() != null){
								key.channel().close();
							}
						}
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(selector !=null){
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		//如果该key是有效的
		if(key.isValid()){
			//测试该通道是否已经连接好了
			if(key.isAcceptable()){
				//得到该key对应的ServerSocketChannel
				ServerSocketChannel channel = (ServerSocketChannel) key.channel();
				//得到连接
				SocketChannel accept = channel.accept();
				accept.configureBlocking(false);
				//处理读事件
				accept.register(selector, SelectionKey.OP_READ);
			}
			//如果准备好可读
			if(key.isReadable()){
				//得到客户端的channel
				SocketChannel channel = (SocketChannel) key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int read = channel.read(buffer);
				if(read>0){
					buffer.flip();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					String str = new String(bytes,"utf-8");
					System.out.println("服务器接收到的指令："+str);
					String nowTime = str.equalsIgnoreCase("queryTime")?
									  new Date(System.nanoTime()).toString() :
										  "error";
					//返回客户端信息
					sendClient(channel,nowTime);
				}else if(read<0){
					key.cancel();
					channel.close();
				}
			}
		}
		
	}

	private void sendClient(SocketChannel channel, String nowTime) throws IOException {
		if(nowTime != null && nowTime.trim().length()>0){
			byte[] bytes = nowTime.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			channel.write(buffer);
		}
	}

}
