import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientAsServer extends Thread {
	ServerSocket sc;
	Socket s;
	RegisterPage rCASp;
	String UserN;
	static int count=0;
	public ClientAsServer(int casP,RegisterPage rp,String User) {
		try {
			sc=new ServerSocket(casP);
			rCASp=rp;
			UserN=User;
			System.out.println("Client as server started "+InetAddress.getLocalHost().getHostName()+"\tPort:"+casP);			
		}catch(Exception e){e.printStackTrace();}
	}
	public void run(){
		while(true){
			System.out.println("...");
			try {
				s=sc.accept();
				count++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				ListenClient lc= new ListenClient(s,rCASp,UserN,count);
				lc.start();
				//lc.join();
			}catch(Exception e){}
		}
	}
}
