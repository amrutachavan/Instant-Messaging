import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

/*
 * ClientCommunication class is for client to initiate communication with other client
 * */
public class ClientCommunication extends Thread{

	Message main;
	String username;
	String min="",mout="";
	RegisterPage rp;
	File f;
	FileOutputStream fo;
	OutputStreamWriter ow;    
	Writer w ;
	String filen;
	public ClientCommunication(Message m,String s,RegisterPage rpage){
		main = m;
		username=s;
		rp=rpage;
		filen=username+"_"+m.userName+".txt";
		try{

		}catch(Exception e){}		
	}
	public void run(){
		//startChat(main);
		CommNow(main);

	}
	/*
	 *startChat function is to startChat with other client
	 * */
	public void startChat(Message mr){
		try{
			Socket chatSoc= new Socket(mr.IP,mr.port);

			DataInputStream din = new DataInputStream(chatSoc.getInputStream());
			DataOutputStream dop = new DataOutputStream(chatSoc.getOutputStream());
			BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Chat Soc started");
			String min="",mout="";

			while(!(min.equals("!End"))){
				System.out.println("Enter text::");
				mout= br.readLine();
				dop.writeUTF(mout);
				dop.flush();
				min = din.readUTF();
				System.out.println(min);						
			}
			chatSoc.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
/*
 *CommNow class is called when a client cannot communicate with other client so the client will contact server.
 * */	
	public void CommNow(Message m){	
		try{
			Socket chatSoc=new Socket();
			boolean failure=false;
			try{
				chatSoc= new Socket(m.IP,m.port);
			}catch(Exception e){
				System.out.println("Now here");
				PopUp.infoBox("Client is down ...sending Message to Server!!", "Failure",rp.frame);
				FailureCallServer();
				failure=true;
			}
			if(!failure){
				DataInputStream din = new DataInputStream(chatSoc.getInputStream());
				DataOutputStream dop = new DataOutputStream(chatSoc.getOutputStream());
				BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Chat Soc started");
				System.out.println("------------>communicating to::"+m.userName);
				//tring min="",mout="";
				mout=username+":::"+rp.textField_3.getText()+"\n";
							
				System.out.println(mout);
				w = new BufferedWriter(new FileWriter(filen,true));
				w.write(mout+"\n");
				w.flush();
				w.close();
				dop.flush();
				dop.writeUTF(mout);
				dop.flush();
				rp.textArea.append(mout);
				rp.textArea.update(rp.textArea.getGraphics());
				rp.textField_3.setText("");
				rp.textField_3.update(rp.textField_3.getGraphics());
				min = din.readUTF();
				rp.textArea.append(min);
				rp.textArea.update(rp.textArea.getGraphics());
				w = new BufferedWriter(new FileWriter(filen,true));
				w.write(mout+"\n");
				w.flush();
				w.close();
				System.out.println(min);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void FailureCallServer(){
		try{
			Socket sfail= new Socket("localhost",3000);
			Message m5=new Message();
			String msg=username+":::"+rp.textField_3.getText()+"\n";
			rp.textField_3.setText("");
			rp.textField_3.update(rp.textField_3.getGraphics());
			rp.textArea.append(msg);
			rp.textArea.update(rp.textArea.getGraphics());
			
			m5.setMessage(05,username,main.userName,"",0,msg);
			ObjectOutputStream os = new ObjectOutputStream(sfail.getOutputStream());
			os.writeObject(m5);
			os.flush();
		}catch(Exception e){

		}
	}

}
