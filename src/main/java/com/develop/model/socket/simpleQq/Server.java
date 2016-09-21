package com.develop.model.socket.simpleQq;

import java.io.*;
import java.net.*;


/**
 * 服务器端操作
 * @author hhc
 *
 */
public class Server {

	public static void main(String[] args) {
		try{
			//创建ServerSocket，监听8888端口
			ServerSocket server=new ServerSocket(8888);
			System.out.println("i am listening!!");
			
			while(true){
				//监听，知道某个客户端连接，一旦连接成功，就会得到
				//Socket好像 一根客户端与服务端的管道
				Socket s=server.accept();
				
				/**
				 * 可以读取客户端发送的信息
				 * 这个用io流
				 */
				InputStreamReader isr=new InputStreamReader(s.getInputStream());
				BufferedReader r=new BufferedReader(isr);
				
				String str=r.readLine();
				System.out.println("client send："+str);
				
				//返回消息给客户端，让他知道消息发送
				OutputStreamWriter osw=new OutputStreamWriter(s.getOutputStream());
				BufferedWriter bw=new BufferedWriter(osw);
				PrintWriter pw=new PrintWriter(bw,true);
				
				//发送
				pw.println("i get  you message!!");
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
