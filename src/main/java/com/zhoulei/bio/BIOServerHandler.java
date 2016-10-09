package com.zhoulei.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class BIOServerHandler implements Runnable {

	
	private Socket socket;
	
	public BIOServerHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(),true);
			String nowTime = "";
			String body = "";
			while(true){
				body = in.readLine();
				if(body == null)
					break;
				System.out.println("服务器收到指定："+body);
				nowTime = "queryTime".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString()
						:"error";
				out.println(nowTime);
			}
		} catch (IOException e) {
			if(in != null){
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(out != null){
				out.close();
				out = null;
			}
			if(this.socket!=null){
				try {
					this.socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.socket=null;
			e.printStackTrace();
		}
		
		
	}

}
