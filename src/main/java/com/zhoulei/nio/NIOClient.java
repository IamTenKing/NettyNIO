package com.zhoulei.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient implements Runnable{
	
	public static void main(String[] args) {
		NIOClient client = new NIOClient("127.0.0.1", 9000);
		new Thread(client,"NIOClient").start();
	}
	
	private String ip;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	
	public NIOClient(String ip,int port){
		this.ip = ip;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	private void handleInput(SelectionKey key) throws IOException {

		if(key.isValid()){
			SocketChannel channel = (SocketChannel) key.channel();
			if(channel.isConnected()){
				if(channel.finishConnect()){
					channel.register(selector, SelectionKey.OP_READ);
					sendServer(channel);
				}else{
					System.exit(1);
				}
				
			}
			if(key.isReadable()){
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int read = channel.read(byteBuffer);
				if(read>0){
					byteBuffer.flip();
					byte[] bytes = new byte[byteBuffer.remaining()];
					String str = new String(bytes, "utf-8");
					System.out.println("收到服务器的消息："+str);
					this.stop = true;
				}else if(read < 0){
					key.cancel();
					channel.close();
				}
			}
		}
		
		
	}
	public void stop(){
		this.stop = true;
	}

	public void run() {
		System.out.println("开始连接");
		try {
			doConnetion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					handleInput(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doConnetion() throws IOException {
		boolean connect = socketChannel.connect(new InetSocketAddress(ip, port));
		if(connect){
			socketChannel.register(selector, SelectionKey.OP_READ);
			//向服务端发送指令
			sendServer(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void sendServer(SocketChannel socketChannel) throws IOException {

		byte[] bytes = "queryTime".getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
		//如果没有有没发送完的数据
		if(!byteBuffer.hasRemaining())
			System.out.println("指令发送完成");
	}

}
