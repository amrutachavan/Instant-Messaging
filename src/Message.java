import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Message implements Serializable{

	int msgId;//to determine type of message
	String userName;
	String passWord;
	String IP;
	int port;
	String ErrMsg;	
	String FailureList;
	public Message(){}

	public void setMessage(int i,String user,String pwd,String Ip,int pt,String EMsg){
		msgId=i;
		userName=user;
		passWord=pwd;
		IP=Ip;
		port=pt;			
		ErrMsg = EMsg;
	}
	int getmsgId(){
		return msgId;
	}
	String getuserName(){
		return userName;
	}		
	
	public void setFailureList(String list){
		FailureList=list;
	}
}