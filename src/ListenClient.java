import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JTextArea;
/*
 * ListenClient class is to start the listening server socket of each client 
 * This socket is for communication between two clients
 * */

public class ListenClient extends Thread {
	Socket Lsoc;
	RegisterPage rpLis;
	DataInputStream din;
	DataOutputStream dop;
	String min="",mout="";
	String Uname;
	File f;
	FileOutputStream fo ;
	OutputStreamWriter ow;    
	Writer w;
	String fname;
	int cnt;
	public ListenClient(Socket s,RegisterPage rp,String Un,int count){
		Lsoc =s;
		rpLis=rp;
		Uname=Un;
		cnt=count;
	}
	public void run(){

		startChat();
	}
	public void startChat(){

		try{
			din = new DataInputStream(Lsoc.getInputStream());
			dop = new DataOutputStream(Lsoc.getOutputStream());
			BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
			min="";mout="";
			System.out.println("Chat Soc started");
			System.out.println(rpLis);
			
			//the communication will end only when the clients enter the text !End
			while(!(min.equals("!End"))){
				//System.out.println("Enter text::");
				min = din.readUTF();
				System.out.println(min);
				rpLis.textArea_1.append(min);
				rpLis.textArea_1.update(rpLis.textArea_1.getGraphics());//System.out.println(min);
				System.out.println("-------->Communicating to::"+min.substring(0,min.indexOf(':')));
				fname=min.substring(0,min.indexOf(':'));
				fname=Uname+"_"+fname+".txt"; 
				//write the chat to the file
				w = new BufferedWriter(new FileWriter(fname,true));
				w.write(min+"\n");
				w.flush();
				w.close();
				rpLis.btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if(!(rpLis.textField_4.getText().equals(""))){
								System.out.println("Button Pressed!!!");
								mout=Uname+":::"+rpLis.textField_4.getText()+"\n";
								dop.flush();
								dop.writeUTF(mout);
								dop.flush();
								w = new BufferedWriter(new FileWriter(fname,true));
								w.write(mout+"\n");
								w.flush();
								w.close();
								System.out.println("I am sending this----"+mout);
								rpLis.textArea_1.append(mout);
								//write the chat to the textArea
								rpLis.textArea_1.update(rpLis.textArea_1.getGraphics());
								rpLis.textField_4.setText("");
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				rpLis.btnChatHistory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						File fil = new File(fname);
						Desktop desktop = Desktop.getDesktop();
						try {
							desktop.open(fil);
						} catch (IOException e){ }
					}
				});
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void StoreMessages(){
		Message m= new Message();
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String file = m.getuserName();

	}
}
