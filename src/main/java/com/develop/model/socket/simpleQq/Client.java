package com.develop.model.socket.simpleQq;

import java.io.*;
import java.net.*;


/**
 * 客户端
 * @author hhc
 *
 */
public class Client {

	
	public static void main(String[] args) {
		try {
			
			//获取服务端连接的管道socket
			Socket s=new Socket("10.1.80.38", 8888);
			
			//通过文件流 发送消息      。。写的流 output
			OutputStreamWriter osw=new OutputStreamWriter(s.getOutputStream());
			BufferedWriter bw=new BufferedWriter(osw);
			PrintWriter pw=new PrintWriter(bw,true);
			
			//发送
			pw.println("aaaaa111");
			
			//读取从服务器回送的消息
			InputStreamReader isr=new InputStreamReader(s.getInputStream());
			BufferedReader r=new BufferedReader(isr);
			String str=r.readLine();
			System.out.println("client access："+str);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
