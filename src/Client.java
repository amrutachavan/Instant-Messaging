import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
/**
 * Client class has the client functionality
 * */
public class Client extends Thread {
	private int id;
	Socket clientSoc;
	public String pwd,username,verifyPwd;
	private ObjectOutputStream os;
	private ByteArrayOutputStream bs;
	private Message mg;
	String ServerIP;
	int serverPort;
	ServerSocket clSock;
	RegisterPage rp;
	int casP;
	String min="",mout="";
	public String FailureFileList;
	public Client(String IP,int p) throws IOException{
		ServerIP=IP;
		serverPort =p;		
	}
	public void setRegisterPage(RegisterPage rgp){
		rp=rgp;
		rp.frame.setTitle(username);
		startListen();		
	}
	public void setFailureArrayList(String fail){
		FailureFileList= fail;
	}
	public void clientOptions(){
		System.out.println("\n1. Register\n2. Login\n3. Communicate\n4. View History \n5. Exit");
		System.out.println("Enter id of operation to be performed");
		Scanner sc = new Scanner(System.in);
		int ch = sc.nextInt();
	}
	/*
	 * sendServerRequest is to send messages to the server
	 * @attribute m is the message to be sent to the server
	 *  */
	public int sendServerRequest(Message m){
		try{
			clientSoc= new Socket(ServerIP,serverPort);
			m.port=clientSoc.getLocalPort()+1000;
			os = new ObjectOutputStream(clientSoc.getOutputStream());
			os.writeObject(m);
			os.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		return (m.port);
	}
	/*
	 * getServerResponse is to get the messages from the server
	 * 
	 * */
	public Message getServerResponse(){
		Message m=new Message();
		try{
			ObjectInputStream ipO = new ObjectInputStream(clientSoc.getInputStream());
			m=(Message) ipO.readObject();
			System.out.println("List here========"+m.FailureList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	} 
	/*
	 * Register function is to register client with the server
	 * @attribute un username
	 * @attribute pw password
	 * */
	public int Register(String un,String pw) {
		username=un;	
		pwd=pw;
		int res=0;
		mg=new Message();
		mg.msgId=01;
		mg.userName=username;
		mg.passWord=pwd;
		try{
			String ip=InetAddress.getLocalHost().getHostAddress();
			mg.IP=ip;			
		}catch(Exception e){}
		casP=sendServerRequest(mg);
		Message mres=getServerResponse();
		if(mres.msgId==010){
			System.out.println(""+mres.msgId+" : "+mres.ErrMsg);
			res=1;
		}else{
			System.out.println(""+mres.msgId+" : "+"Usename Already Exists");
			//Register();
			res=-1;			
		}
		return res;
	}
	
/*
 * startListen function is to start the listen server of each client
 * */
	public void startListen(){
		ClientAsServer cas = new ClientAsServer(casP,rp,username);
		Thread t1 = new Thread(cas);
		t1.start();
	}

/*
 * Login function is for a client to login
 * @attribute un username
 * @attribute pw password
 * */	
	public Message Login(String un,String pw) {
		int r=0;
		username=un;
		pwd=pw;
		mg = new Message();
		mg.msgId=02;
		mg.userName=username;
		mg.passWord=pwd;
		try{
			String ip=InetAddress.getLocalHost().getHostAddress();
			mg.IP=ip;
		}catch(Exception e){}
		casP=sendServerRequest(mg);
		Message mres = getServerResponse();
		if(mres.msgId==020){
			System.out.println(""+mres.msgId+" : "+mres.ErrMsg);
			mres.msgId=1;
		}else if(mres.msgId==022){
			System.out.println(""+mres.msgId+" : "+mres.ErrMsg);
			mres.msgId=0;
		}else{
			System.out.println(""+mres.msgId+" : "+mres.ErrMsg);
			mres.msgId=-1;
		}
		return mres;
	}

	public Message Communicate(String uname){
		String un = uname;
		Message m=new Message();
		m.msgId=03;
		m.userName=un;		
		sendServerRequest(m);
		Message mres = getServerResponse();
		System.out.println("Username"+mres.userName+"\tIP:"+mres.IP+"\tport:"+mres.port);
		return mres;
	}	

	public void CommNow(Message m){	
		try{
			Socket chatSoc= new Socket(m.IP,m.port);
			DataInputStream din = new DataInputStream(chatSoc.getInputStream());
			DataOutputStream dop = new DataOutputStream(chatSoc.getOutputStream());
			BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Chat Soc started");
			System.out.println("------------>communicating to::"+m.userName);
			mout=username+":::"+rp.textField_3.getText();
			System.out.println(mout);
			File f = new File(m.userName);
			FileOutputStream fo = new FileOutputStream(f);
			OutputStreamWriter ow = new OutputStreamWriter(fo);    
			Writer w = new BufferedWriter(ow);
			w.write(mout+"\n");
			dop.writeUTF(mout);
			dop.flush();
			rp.textArea.append(mout);
			rp.textArea.update(rp.textArea.getGraphics());
			rp.textField_3.setText("");
			rp.textField_3.update(rp.textField_3.getGraphics());
			min = din.readUTF();
			rp.textArea.append(min);
			rp.textArea.update(rp.textArea.getGraphics());
			w.write(mout+"\n");
			System.out.println(min);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException{
		System.out.println("Enter IP and port of server to connect to");
		Scanner sc = new Scanner(System.in);
		String IP= sc.next();
		int port =sc.nextInt();
		Client c = new Client(IP,port);
		c.clientOptions();
	}
}
