package edu.jhu.cs.oose.group14.restaurant.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.BorderLayout;
import java.awt.Container;

/**
 * LoginGui class has the methods to display the Gui for login page and other helper methods.
 * 
 * @author Parkavi
 *
 */

public class LoginGui {
	
	private Container contentPane;
	private JPanel panel1;
	private JLabel username;
	private JPasswordField passwordLogin;
	private JButton signup;
	private JLabel signupText;
	private JPanel subpanel1;
	private JButton login;
	private JLabel password;
	private JTextField usernameLogin;
	
	
	public LoginGui(Container contentPane){
		this.contentPane = contentPane;	
	}
	
	
	/*
	 * Displays the Gui for login screen 
	 */
	
	public void displayLoginScreen(){
		
		panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		GroupLayout panel1Layout = new GroupLayout((JComponent)panel1);
		panel1.setLayout(panel1Layout);
		panel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		panel1.setPreferredSize(new java.awt.Dimension(687, 324));
		{
			subpanel1 = new JPanel();
			GroupLayout jPanel1Layout = new GroupLayout((JComponent)subpanel1);
			subpanel1.setLayout(jPanel1Layout);
			subpanel1.setBorder(BorderFactory.createTitledBorder("Login"));
			{
				login = new JButton();
				login.setText("Login");
			}
			{
				usernameLogin = new JTextField();
				usernameLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				//usernameLogin.setText("No1Res");
			}
			{
				passwordLogin = new JPasswordField();
				//passwordLogin.setRequestFocusEnabled(false);
				passwordLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				//passwordLogin.setText("abc");
			}
			{
				username = new JLabel();
				username.setText("Enter Username");
			}
			{
				password = new JLabel();
				password.setText("Enter Password");
			}
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
				.addGap(8)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				        .addGap(0, 0, Short.MAX_VALUE)
				        .addComponent(password, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(jPanel1Layout.createParallelGroup()
				            .addComponent(passwordLogin, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
				            .addComponent(usernameLogin, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
				    .addGroup(jPanel1Layout.createSequentialGroup()
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(jPanel1Layout.createParallelGroup()
				            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				                .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 64, Short.MAX_VALUE))
				            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				                .addGap(83)
				                .addComponent(login, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 0, Short.MAX_VALUE)))
				        .addGap(119)))
				.addContainerGap());
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addGap(7)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(usernameLogin, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
				    .addComponent(username, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(25)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(password, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				    .addComponent(passwordLogin, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
				.addGap(0, 18, Short.MAX_VALUE)
				.addComponent(login, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
		}
		{
			signupText = new JLabel();
			signupText.setText("New to iHungry?");
			signupText.setBounds(150,150,120,29);
		}
		{
			signup = new JButton();
			signup.setText("Signup");
		}
			panel1Layout.setVerticalGroup(panel1Layout.createSequentialGroup()
				.addContainerGap(23, 23)
				.addComponent(subpanel1, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(signupText, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
				    .addComponent(signup, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(181, 181));
			panel1Layout.setHorizontalGroup(panel1Layout.createSequentialGroup()
				.addContainerGap(144, 144)
				.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(signup, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
				    .addComponent(subpanel1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
				    .addComponent(signupText, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(93, 93));
	
	}
	
	/**
	 * getLogin method returns a reference of the Login Button to the model 
	 * so that listeners can be setup for the button.
	 * 
	 * @return a reference to Login button
	 */
	
	public JButton getLogin(){
		return login;
	}
	
	public JTextField getUsernameLogin(){
		return usernameLogin;
	}
	
	public JPasswordField getPasswordLogin(){
		return passwordLogin;
	}
	
	public JButton getSignUp(){
		return signup;
	}
	

}
