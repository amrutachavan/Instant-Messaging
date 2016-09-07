import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.HashMap;
import java.io.*;
/*
 * This is the main server class
 * This should be run first before starting the client
 *  */
public class Server extends Thread {
	private ServerSocket ss;
	private Socket s;
	public int ServerPort;

	public Server() throws IOException{
		ServerPort = 3000;
	}
	
	public void run(){
		try {
			ss=new ServerSocket(ServerPort);
			while(true){
				s=ss.accept();
				new ServerRepository(s).start();
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void main(String args[]) throws IOException{
		System.out.println("Starting server");
		Server s=new Server();
		try{
			System.out.println("Server started on ::"+ Inet4Address.getLocalHost().getHostAddress()+" at port::" +s.ServerPort);
		}catch(Exception e){
			e.printStackTrace();
		}
		s.run();
	}
}
