import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;

/*This class is to show popup menu to the client
 * */
class PopUp
{

	public static void infoBox(String msg, String titleBar,JFrame f)
	{
		JOptionPane.showMessageDialog(f, msg, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}

/*
 * This is the main client page where register and login options are provided
 * */
public class ClientApp {

	private JFrame frame;
	private JFrame frame1;
	private JTextField textField;
	Client c;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApp window = new ClientApp();
					window.frame.setVisible(true);
					window.c = new Client("localhost",3000);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 228, 181));
		frame.setBounds(100, 100, 614, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textField.setBounds(333, 86, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textField_1.setBounds(333, 170, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblName.setBounds(138, 94, 71, 19);
		frame.getContentPane().add(lblName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblPassword.setBounds(133, 168, 118, 26);
		frame.getContentPane().add(lblPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String un= textField.getText();
					String pw=textField_1.getText();
					int r=c.Register(un, pw);
					if(r==1){
						PopUp.infoBox("Registration successfull", "Register",frame);					
						System.out.println("After Register");
						frame.dispose();
						RegisterPage rp = new RegisterPage();						
						rp.setClient(c);
						rp.setVisible(rp,true,c);						
					}else{
						PopUp.infoBox("UserName already exists", "Register",frame);
						textField.setText("");
						textField_1.setText("");		
					}				 
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});	
		//	frame.setVisible(true);
		btnRegister.setBounds(108, 267, 122, 40);
		frame.getContentPane().add(btnRegister);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String un= textField.getText();
					String pw=textField_1.getText();
					Message gr=c.Login(un, pw);
					if(gr.msgId==1){
						PopUp.infoBox("Login successfull", "Login",frame);
						System.out.println(gr.FailureList);
						if(gr.FailureList!=""){
							c.setFailureArrayList(gr.FailureList);
							System.out.println("Set the failure list for:"+c.username);
						}
						System.out.println("After Login");
						frame.dispose();
						RegisterPage rp = new RegisterPage();
						rp.setClient(c);
						rp.setVisible(rp,true,c);						
					}else if (gr.msgId==022){
						PopUp.infoBox("UserName doesnot exsist Please register", "Login",frame);	
						textField.setText("");
						textField_1.setText("");		
					}else {
						PopUp.infoBox("Password Incorrect", "Login",frame);	
						//textField.setText("");
						textField_1.setText("");		
					}					 
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(344, 264, 123, 40);
		frame.getContentPane().add(btnLogin);

		JLabel lblNewLabel = new JLabel("Messenger!!");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
		lblNewLabel.setBounds(185, 15, 171, 41);
		frame.getContentPane().add(lblNewLabel);		
	}
}
