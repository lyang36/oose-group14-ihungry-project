package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


/**
 * SignupGui has methods for displaying the sugnup screen.
 * @author parkavi
 *
 */

public class SignupGui {
	private Container contentPane;
	private JLabel passwordSignUpL;
	private JLabel unameSignUpL;
	private JTextField secSignUpT;
	private JTextField priSignUpT;
	private JTextField zipSignUpT;
	private JComboBox stateSignUpT;
	private JTextField citySignUpT;
	private JTextField streetSignUpT;
	private JTextField emailSignUpT;
	private JLabel secSignUpL;
	private JLabel priSignUpL;
	private JLabel addressSignUpL;
	private JLabel emailSignUpL;
	private JPanel signUpPanel2;
	private JButton nextSignUpL;
	private JTextField lnameSignUpL;
	private JTextField fnameSignUpL;
	private JLabel nameSignUpL;
	private JPanel subPanel2;
	private JPasswordField confirmPasswordSignUpT;
	private JPasswordField passwordSignUpT;
	private JTextField unameSignUpT;
	private JLabel confirmpasswordSignUpL;
	private JPanel signUpPanel;
	private JButton signup2T;
	private JPanel jPanel1;
	
	
	public SignupGui(Container contentPane){
		this.contentPane = contentPane;
		
	}

	
	/*
	 * Displays the signup screen
	 */
	
	public void displayFirstPageSignUpGui(){
		{	
			//make the previous components to be not visible 
			for(int i=0;i<contentPane.getComponentCount();i++)
				if (contentPane.getComponentCount()>0)
					contentPane.getComponents()[i].setVisible(false);
			signUpPanel = new JPanel();
			contentPane.add(signUpPanel, BorderLayout.CENTER);
			GroupLayout signUpPanelLayout = new GroupLayout((JComponent)signUpPanel);
			signUpPanel.setLayout(signUpPanelLayout);
			signUpPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			signUpPanel.setPreferredSize(new java.awt.Dimension(687, 324));
			{
				subPanel2 = new JPanel();
				GroupLayout subPanel2Layout = new GroupLayout((JComponent)subPanel2);
				subPanel2.setLayout(subPanel2Layout);
				subPanel2.setBorder(BorderFactory.createTitledBorder("Username and Password Selection"));
				{
					unameSignUpL = new JLabel();
					unameSignUpL.setText("Username:");
				}
				{
					passwordSignUpL = new JLabel();
					passwordSignUpL.setText("Password:");
				}
				{
					confirmpasswordSignUpL = new JLabel();
					confirmpasswordSignUpL.setText("Confirm Password:");
				}
				{
					fnameSignUpL = new JTextField();
					fnameSignUpL.setToolTipText("FirstName");
				}
				{
					lnameSignUpL = new JTextField();
					lnameSignUpL.setToolTipText("LastName");
					
				}
				{
					nameSignUpL = new JLabel();
					nameSignUpL.setText("Name :");
				}
				{
					confirmPasswordSignUpT = new JPasswordField();
				}
				{
					passwordSignUpT = new JPasswordField();
				}
				{
					unameSignUpT = new JTextField();
				}
				subPanel2Layout.setHorizontalGroup(subPanel2Layout.createSequentialGroup()
						.addGroup(subPanel2Layout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.LEADING, subPanel2Layout.createSequentialGroup()
										.addComponent(nameSignUpL, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
										.addGap(45))
										.addGroup(GroupLayout.Alignment.LEADING, subPanel2Layout.createSequentialGroup()
												.addComponent(passwordSignUpL, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
												.addGap(65))
												.addGroup(GroupLayout.Alignment.LEADING, subPanel2Layout.createSequentialGroup()
														.addComponent(unameSignUpL, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
														.addGap(65))
														.addComponent(confirmpasswordSignUpL, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
														.addGap(44)
														.addGroup(subPanel2Layout.createParallelGroup()
																.addGroup(subPanel2Layout.createSequentialGroup()
																		.addComponent(unameSignUpT, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
																		.addGap(0, 0, Short.MAX_VALUE))
																		.addGroup(subPanel2Layout.createSequentialGroup()
																				.addComponent(passwordSignUpT, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
																				.addGap(0, 0, Short.MAX_VALUE))
																				.addGroup(subPanel2Layout.createSequentialGroup()
																						.addComponent(confirmPasswordSignUpT, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
																						.addGap(0, 0, Short.MAX_VALUE))
																						.addComponent(fnameSignUpL, GroupLayout.Alignment.LEADING, 0, 136, Short.MAX_VALUE)
																						.addGroup(subPanel2Layout.createSequentialGroup()
																								.addGap(0, 0, Short.MAX_VALUE)
																								.addComponent(lnameSignUpL, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
																								.addContainerGap(90, 90));
				subPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {unameSignUpT, lnameSignUpL, fnameSignUpL});
				subPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {confirmPasswordSignUpT, passwordSignUpT});
				subPanel2Layout.setVerticalGroup(subPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(fnameSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGap(17)
								.addComponent(lnameSignUpL, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addGap(25)
								.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(unameSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addComponent(unameSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
										.addGap(32)
										.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(passwordSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
												.addComponent(passwordSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
												.addGap(34)
												.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(confirmPasswordSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
														.addComponent(confirmpasswordSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
														.addContainerGap(62, 62));
				subPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {unameSignUpT, lnameSignUpL, fnameSignUpL});
				subPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {confirmPasswordSignUpT, passwordSignUpT});
			}
			{
				nextSignUpL = new JButton();
				nextSignUpL.setText("Next");
			}

			signUpPanelLayout.setHorizontalGroup(signUpPanelLayout.createSequentialGroup()
					.addContainerGap(186, 186)
					.addGroup(signUpPanelLayout.createParallelGroup()
							.addGroup(signUpPanelLayout.createSequentialGroup()
									.addComponent(subPanel2, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
									.addGroup(GroupLayout.Alignment.LEADING, signUpPanelLayout.createSequentialGroup()
											.addGap(187)
											.addComponent(nextSignUpL, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
											.addGap(0, 152, Short.MAX_VALUE)))
											.addContainerGap(177, 177));
			signUpPanelLayout.setVerticalGroup(signUpPanelLayout.createSequentialGroup()
					.addContainerGap(82, 82)
					.addComponent(subPanel2, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
					.addGap(50)
					.addComponent(nextSignUpL, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE));
		}	
	}
	
	
	public void setSignUpPanel(){
		signUpPanel.setVisible(true);
		passwordSignUpT.setText("");
		confirmPasswordSignUpT.setText("");
	}
	
	public JButton getNextSignUp(){
		return nextSignUpL;
	}
	
	public JTextField getFirstName(){
		return fnameSignUpL;
	}
	
	public JTextField getLastName(){
		return lnameSignUpL;
	}
	
	public JTextField getUserName(){
		return unameSignUpT;
	}
	
	public JPasswordField getPassword(){
		return passwordSignUpT;
	}
	
	public JPasswordField getConfirmPassword(){
		return confirmPasswordSignUpT;
	}
	
	
	/*
	 * Displays the second page of signup screen
	 */
	
	public void displaySecondPageSignUpGui(){
		
		Object[] statesList = {"AB ALBERTA","AK ALASKA","AL ALABAMA","AR ARKANSAS","AS AMERICAN SAMOA","AZ ARIZONA",
				               "BC BRITISH COLUMBIA","CA CALIFORNIA","CO COLORADO","CT CONNECTICUT","DC DISTRICT OF COLUMBIA",
				               "DE DELAWARE","FL FLORIDA","FM FEDERATED STATES OF MICRONESIA","GA GEORGIA","GU GUAM",
				               "HI HAWAII","IA IOWA","ID IDAHO","IL ILLINOIS","IN INDIANA","KS KANSAS","KY KENTUCKY",
				               "LA LOUISIANA","MA MASSACHUSETTS","MB MANITOBA","MD MARYLAND","ME MAINE","MH MARSHALL ISLANDS",
				               "MI MICHIGAN","MN MINNESOTA","MO MISSOURI","MP NORTHERN MARIANA ISLANDS","MS MISSISSIPPI",
				               "MT MONTANA","NB NEW BRUNSWICK","NC NORTH CAROLINA","ND NORTH DAKOTA","NE NEBRASKA","NF NEWFOUNDLAND",
				               "NH NEW HAMPSHIRE","NJ NEW JERSEY","NM NEW MEXICO","NS NOVA SCOTIA","NT NORTHWEST TERRITORY","NU NUNAVUT",
				               "NV NEVADA","NY NEW YORK","OH OHIO","OK OKLAHOMA","ON ONTARIO","OR OREGON","PA PENNSYLVANIA","PE PRINCE EDWARD ISLAND",
				               "PQ QUEBEC","PR PUERTO RICO","PW PALAU","RI RHODE ISLAND","SC SOUTH CAROLINA","SD SOUTH DAKOTA","SK SASKATCHEWAN",
				               "TN TENNESSEE","TX TEXAS","UT UTAH","VA VIRGINIA","VI VIRGIN ISLANDS","VT VERMONT","WA WASHINGTON","WI WISCONSIN",
				               "WV WEST VIRGINIA","WY WYOMING"};
		
		signUpPanel.setVisible(false);
		{
			signUpPanel2 = new JPanel();
			contentPane.add(signUpPanel2, BorderLayout.CENTER);
			GroupLayout signUpPanel2Layout = new GroupLayout((JComponent)signUpPanel2);
			signUpPanel2.setLayout(signUpPanel2Layout);
			signUpPanel2.setPreferredSize(new java.awt.Dimension(4, 5));
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout1 = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout1);
				jPanel1.setBorder(BorderFactory.createTitledBorder("Address and Contact Details"));
				{
					
					stateSignUpT = new JComboBox(statesList);
					stateSignUpT.setSelectedIndex(0);
				}
				{
					citySignUpT = new JTextField();
					citySignUpT.setToolTipText("City");
				}
				{
					zipSignUpT = new JTextField();
					zipSignUpT.setToolTipText("Zip");
				}
				{
					streetSignUpT = new JTextField();
					streetSignUpT.setToolTipText("Street");
				}
				{
					emailSignUpT = new JTextField();
				}
				{
					priSignUpT = new JTextField();
				}
				{
					secSignUpT = new JTextField();
				}
				{
					emailSignUpL = new JLabel();
					emailSignUpL.setText("Email:");
				}
				{
					addressSignUpL = new JLabel();
					addressSignUpL.setText("Address:");
				}
				{
					priSignUpL = new JLabel();
					priSignUpL.setText("Primary Phone:");
				}
				{
					secSignUpL = new JLabel();
					secSignUpL.setText("Secondary Phone:");
				}
				jPanel1Layout1.setHorizontalGroup(jPanel1Layout1.createSequentialGroup()
						.addGroup(jPanel1Layout1.createParallelGroup()
								.addComponent(emailSignUpL, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addComponent(addressSignUpL, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addComponent(priSignUpL, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addComponent(secSignUpL, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
								.addGap(64)
								.addGroup(jPanel1Layout1.createParallelGroup()
										.addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout1.createSequentialGroup()
												.addComponent(stateSignUpT, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE))
												.addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout1.createSequentialGroup()
														.addComponent(citySignUpT, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
														.addGap(56)
														.addComponent(zipSignUpT, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
														.addGap(0, 68, Short.MAX_VALUE))
														.addGroup(jPanel1Layout1.createSequentialGroup()
																.addComponent(streetSignUpT, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
																.addGap(0, 0, Short.MAX_VALUE))
																.addGroup(jPanel1Layout1.createSequentialGroup()
																		.addComponent(emailSignUpT, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
																		.addGap(0, 0, Short.MAX_VALUE))
																		.addGroup(jPanel1Layout1.createSequentialGroup()
																				.addComponent(priSignUpT, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
																				.addGap(0, 0, Short.MAX_VALUE))
																				.addGroup(jPanel1Layout1.createSequentialGroup()
																						.addComponent(secSignUpT, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
																						.addGap(0, 0, Short.MAX_VALUE)))
																						.addContainerGap(65, 65));
				jPanel1Layout1.setVerticalGroup(jPanel1Layout1.createSequentialGroup()
						.addContainerGap(22, 22)
						.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(emailSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(emailSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
								.addGap(34)
								.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(addressSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addComponent(streetSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
										.addGap(28)
										.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(citySignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
												.addComponent(zipSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
												.addGap(25)
												.addComponent(stateSignUpT, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
												.addGap(43)
												.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(priSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
														.addComponent(priSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
														.addGap(27)
														.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(secSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
																.addComponent(secSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
																.addContainerGap(32, 32));
			}
			{
				signup2T = new JButton();
				signup2T.setText("Signup");
			}
			
			signUpPanel2Layout.setHorizontalGroup(signUpPanel2Layout.createSequentialGroup()
					.addContainerGap(207, 207)
					.addGroup(signUpPanel2Layout.createParallelGroup()
							.addGroup(signUpPanel2Layout.createSequentialGroup()
									.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
									.addGroup(GroupLayout.Alignment.LEADING, signUpPanel2Layout.createSequentialGroup()
											.addGap(205)
											.addComponent(signup2T, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
											.addGap(0, 174, Short.MAX_VALUE)))
											.addContainerGap(118, 118));
			signUpPanel2Layout.setVerticalGroup(signUpPanel2Layout.createSequentialGroup()
					.addContainerGap(38, 38)
					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addComponent(signup2T, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(60, Short.MAX_VALUE));
		}
	}
	
	public JButton getSignUp2(){
		return signup2T;
	}
	
	public JTextField getEmail(){
		return emailSignUpT;
	}
	
	public JTextField getStreet(){
		return streetSignUpT;
	}
	
	public JTextField getCity(){
		return citySignUpT;
	}
	
	public JComboBox getState(){
		return stateSignUpT;
	}
	
	public JTextField getZip(){
		return zipSignUpT;
	}
	
	public JTextField getPriPhone(){
		return priSignUpT;
	}
	
	public JTextField getSecPhone(){
		return secSignUpT;
	}
	
	public JPanel getSignUpPanel(){
		return signUpPanel;
	}
	
}
