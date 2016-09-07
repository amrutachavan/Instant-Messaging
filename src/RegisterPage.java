import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
/**
 * This is the RegisterPage the different client options are provided in this*/

public class RegisterPage {

	public  JFrame frame = new JFrame();
	public JTextField textField;
	public JTextField textField_1;
	public JTextField txtEnterUsername;
	Client cl;
	public JTextField textField_3;
	public JTextArea textArea ;
	Message m1;
	public JTextField textField_4;
	public JTextArea textArea_1;
	public JButton btnNewButton_1;
	public JButton btnNewButton;
	public JList list;
	private JList list_1;
	RegisterPage rpage;
	public JButton btnChatHistory ;
	public int xBoundTextArea,yBoundTextArea,htTextArea,wTextArea;
	public int xBoundTextBox,yBoundTextBox,htTextBox,wTextBox;
	public int xBoundBtn,yBoundbtn,htBtn,wBtn;
	private JList list_2;
	private JList list_3;
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//RegisterPage window = new RegisterPage();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public RegisterPage() {
		initialize();
		xBoundTextArea=578;
		yBoundTextArea=310;
		htTextArea=	221;
		wTextArea=55;
		xBoundTextBox=578;
		yBoundTextBox=310;
		htTextBox=221;
		wTextBox=55;
		xBoundBtn=638;
		yBoundbtn=383;
		htBtn=115;
		wBtn=29;
	}
	public void setClient(Client c){
		cl=c;
		rpage=this;
		cl.setRegisterPage(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 222, 173));
		frame.setBounds(100, 100, 892, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea("");
		textArea.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textArea.setBounds(299, 16, 221, 280);
		frame.getContentPane().add(textArea);
		txtEnterUsername = new JTextField();
		txtEnterUsername.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		txtEnterUsername.setBounds(28, 50, 191, 39);
		frame.getContentPane().add(txtEnterUsername);
		txtEnterUsername.setColumns(10);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblUsername.setBounds(28, 14, 118, 20);
		frame.getContentPane().add(lblUsername);

		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(cl.equals(null))){
					m1=cl.Communicate(txtEnterUsername.getText());
					//textField_2.setText("");
				}else{PopUp.infoBox("User doesnot exists!", "ClientOptions",frame);	}
			}
		});
		btnCommunicate.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnCommunicate.setBounds(28, 105, 191, 39);
		frame.getContentPane().add(btnCommunicate);

		btnChatHistory = new JButton("Chat History");
		btnChatHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(m1!=null){
					String fname=cl.username+"_"+m1.userName+".txt";
					File fil = new File(fname);
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.open(fil);
					} catch (Exception e1){ }
				}
			}
		});


		btnChatHistory.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnChatHistory.setBounds(28, 173, 191, 39);
		frame.getContentPane().add(btnChatHistory);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textField_3.setBounds(299, 310, 228, 55);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		btnNewButton = new JButton("Send");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//cl.CommNow(m1);
				new ClientCommunication(m1,cl.username,rpage).start();
			}
		});

		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnNewButton.setBounds(370, 383, 115, 29);
		frame.getContentPane().add(btnNewButton);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textField_4.setBounds(578, 310, 221, 55);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		textArea_1 = new JTextArea("");
		textArea_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		textArea_1.setBounds(578, 16, 221, 280);
		frame.getContentPane().add(textArea_1);

		btnNewButton_1 = new JButton("Reply");
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnNewButton_1.setBounds(638, 383, 115, 29);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Missed Messages");
		btnNewButton_2.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cl.FailureFileList!=""){
					String fname[]=new String[5];
					try{
					for(int k=0,j=-1;k<cl.FailureFileList.length();k++){
						if(cl.FailureFileList.charAt(k)==','){
							k++;
							j++;
							fname[j]=new String("");
						}
						fname[j]=fname[j]+cl.FailureFileList.charAt(k);
						System.out.println("The failure file i got is ::"+fname[j]);
					}
					list_2 = new JList(fname);
					list_2.setBounds(42, 310, 159, 86);
					frame.getContentPane().add(list_2);
					frame.repaint();	
					list_2.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							JList list = (JList)evt.getSource();
							if (evt.getClickCount() == 2) {
								String filn=(String)list_2.getSelectedValue();
								File fil = new File(filn);
								Desktop desktop = Desktop.getDesktop();
								try {
									desktop.open(fil);
								} catch (Exception e1){ }
							} 			
						}
					});}catch(Exception e2){
						PopUp.infoBox("No Missed Messages Found", "Missed",frame);
					}

				}
			}
		});



		btnNewButton_2.setBounds(28, 238, 191, 39);
		frame.getContentPane().add(btnNewButton_2);
	}

	public void setVisible(RegisterPage win,boolean b,Client c) {
		win.frame.setVisible(true);	
		/*System.out.println(cl);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					RegisterPage window = new RegisterPage();
					window.frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
}
