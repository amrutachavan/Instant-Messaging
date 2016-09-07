import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;
/*
 * ServerRepository class is to to provide options to client
 * */
public class ServerRepository extends Thread {
	private Socket s;
	private Message m;
	private ObjectInputStream in;
	OutputStream opS;
	ObjectOutputStream opO;
	PrintStream out;
	static ConcurrentHashMap<String,String> hmIP= new ConcurrentHashMap<String, String>();
	static ConcurrentHashMap<String,String> Users= new ConcurrentHashMap<String, String>();

	public ServerRepository(Socket s){
		this.s=s;
	}
	public void run(){
		try {
			in=new ObjectInputStream(s.getInputStream());
			opS = s.getOutputStream();
			opO = new ObjectOutputStream(opS);		
			out = new PrintStream(s.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acceptRequest();
	}
	public void acceptRequest(){

		try {
			m=(Message)in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		switch(m.msgId){
		case 01:Register();
				break;
		case 02:Login(m.userName,m.passWord);
				break;
		case 03:GetIp(m.userName);
				break;		
		case 05:WriteFailureFile(m);
				break;
		}
	}
	public void WriteFailureFile(Message m){
		try{
		String fname= "Failure"+"-"+m.userName+"_"+m.passWord+".txt";
		BufferedWriter w = new BufferedWriter(new FileWriter(fname,true));
		w.write(m.ErrMsg+"\n");
	    w.flush();
	    w.close();
		}catch(Exception e){			
		}
	}

	public void sendResponse(Message m){
		try{
			opO.flush();
			opO.writeObject(m);
			opO.flush();
		}catch(Exception e){

		}
	}

	public void Register(){
		//hm=new HashMap<String,String>();
		Message mres = new Message();
		if(!(hmIP.containsKey(m.userName))){
			hmIP.put(m.userName, m.IP+":"+m.port);
			Users.put(m.userName, m.passWord);
			mres.setMessage(010,"","","",0,"User Registered Successfully");
			System.out.println("Username:"+m.userName+"\tPassword:"+m.passWord+"\tIP:"+m.IP+":"+m.port);
		}else{
			mres.setMessage(011,"","","",0,"User Not Registered Successfully");
		}
		sendResponse(mres);
	}

	public void Login(String user,String pass){

		boolean flag = false;
		Message mres = new Message();
		//System.out.println("Inside login");
		String paswd=null;
		if((paswd=Users.get(m.userName))!=null){
			if(paswd.equals(m.passWord)){
				mres.setMessage(020,"","","",0,"Login Success");
				changeIP(user);
				System.out.println("Username:"+m.userName+"\tPassword:"+m.passWord+"\tIP:"+m.IP+":"+m.port);
			}else{
				mres.setMessage(021,"","","",0,"Password Incorrect Login Not Successfull");
			}
		}else{
			mres.setMessage(022,"","","",0,"UserName doesnot exsist,Login Not Successfull,Please register");
		}
		
		File curntDir = new File(System.getProperty("user.dir"));
		File[] filesList = curntDir.listFiles();
		String chk;
		//String FailureFileList[]=new String[5];
		ArrayList<String>FailureFileList = new ArrayList<String>();
		String failfilenames="";
		int i=-1;
		for(File f : filesList){
  			if(f.isFile()){
             //   System.out.println(f.getName());
                String fn= f.getName();
                if(fn.contains("Failure")){
                	chk= fn.substring(fn.indexOf('_')+1,fn.indexOf("."));
                	System.out.println("Failure file exists for "+chk);
                	if(chk.equals(m.userName)){
            			i++;
            			System.out.println("This file====adding ::"+fn);
            			FailureFileList.add(fn);
            			failfilenames=failfilenames+","+fn;
            		}
                }                
            }
        }
		if(i>-1){
			mres.setFailureList(failfilenames);
		}
		System.out.println("fail list is"+failfilenames);
		sendResponse(mres);
		
	}

	public void changeIP(String user){
		for(Entry<String, String> entry : hmIP.entrySet()){

			if(entry.getKey().equals(user)){
				hmIP.put(entry.getKey(), m.IP+":"+m.port);
				System.out.println(entry.getValue());
			}
		}
	}
	
	public void GetIp(String uname){

		Message mout= new Message();
		if(hmIP.containsKey(m.userName)){
			String ip = hmIP.get(uname);
			String port=ip.substring(ip.indexOf(':')+1); //get the port number
			ip=ip.substring(0,ip.indexOf(':'));		 //get only the ip
			mout.setMessage(030, uname,"",ip,Integer.parseInt(port),"IP returned successfully"); //construct the message
		}else{
			mout.setMessage(031,"","","",0,"Error: UseName not found"); //construct the message
		}
		sendResponse(mout);
	}
}
