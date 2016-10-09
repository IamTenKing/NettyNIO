package com.zhoulei.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

	public static void main(String[] args) {

		int port = 8000;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务器启动");
			//创建线程池执行业务任务
			BIOServerHandlerThreadPoolExcutor bioServerHandlerThreadPoolExcutor = new BIOServerHandlerThreadPoolExcutor(50, 10000);
			Socket accept = null;
			while(true){
				accept = serverSocket.accept();
				//利用线程池执行业务任务
				bioServerHandlerThreadPoolExcutor.execute(new BIOServerHandler(accept));
//				new Thread(new BIOServerHandler(accept)).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocket != null){
				try {
					System.out.println("服务器关闭");
					serverSocket.close();
					serverSocket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}

}
