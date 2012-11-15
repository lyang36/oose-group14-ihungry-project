package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.group14.restaurant.controller.ihungryRestaurantController;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

import java.util.*;

/*
 * ihungryRestaurantGui class is responsible for displaying the vendor-Gui screens
 *  of the ihungry application.It holds methods to display various Gui screens 
 *  for ihungry vendor application and few methods to return the swing 
 *  components in the current screen to the model class.
 */








/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ihungryRestaurantGui extends javax.swing.JFrame {
	
	private JPanel panel1;
	private JLabel username;
	private JPasswordField passwordLogin;
	private JButton jButton1;
	private JPanel declinedOrders;
	private JTabbedPane jTabbedPane1;
	private JPanel currentOrders;
	private JButton signup;
	private JLabel signupText;
	private JPanel subpanel1;
	private JButton login;
	private JLabel password;
	private JTextField usernameLogin;
	private JPanel viewMenu;

	private JButton decline;
	private JButton accept;
	private JLabel passwordSignUpL;
	private JLabel unameSignUpL;
	private JList list1;
	
	
	private JTextField orderNoT;
	private JTextField custNoT;
	private JPanel subPanel10;
	
	private JLabel orderNoL1;
	private JLabel custNoL1;

	private JScrollBar scrollBar1;
	private JLabel timeL;
	private JLabel statusL;
	private JLabel listL;
	private JLabel orderNoL;

	private JPanel panel4;
	private JButton prev;
	private JButton next;
	private JPanel itemSubPanel;
	private JLabel price;
	private JLabel decription;
	private JLabel name;
	private JLabel menuFrontMessage;
	private JPanel orderHistory;
	private JPanel toBeDelivered;
	private ArrayList<JTextField> listOfItemNames = new ArrayList<JTextField>();
	private ArrayList<JTextField> listOfDescription = new ArrayList<JTextField>();
	private ArrayList<JTextField> listOfPrice = new ArrayList<JTextField>();
	private ArrayList<JButton> listOfEdit = new ArrayList<JButton>();
	private ArrayList<JButton> listOfDelete = new ArrayList<JButton>();

	private String[] columnNames = {"Order No", "List", "Status", "Order Placed Time"};
	private Object[][] data = new Object[1000][5];
	private int pointer = 0;
	private JTable table = new JTable(data, columnNames);
	private String[] orderDetails = new String[50];
	private int orderDetailsPointer = 0;
	private JPanel subPanel2;
	private JPasswordField confirmPasswordSignUpT;
	private JPasswordField passwordSignUpT;
	private JTextField secSignUpT;
	private JTextField primSignUpT;
	private JTextField addressSignUpT;
	private JTextField emailSignUpT;
	private JTextField unameSignUpT;
	private JLabel secSignUpL;
	private JLabel primSignUpL;
	private JLabel addressSignUpL;
	private JLabel emailSignUpL;
	private JLabel confirmpasswordSignUpL;
	private JPanel signUpPanel;
	private JButton signUpFinal;

	
	
	/*
	 * Main method creates an object of ihungryVendorGui class and an object 
	 * of model class and passes the Gui class object to model object thus 
	 * creating a connection between them.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ihungryRestaurantGui gui = new ihungryRestaurantGui();
				ihungryRestaurantController control = new ihungryRestaurantController(gui);
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);
			}
		});
	}
	
	
	/*
	 * Constructor of ihungryVendorGui just sets up the components and containers
	 * ready for displaying the login screen.
	 */
	
	public ihungryRestaurantGui() {
		super();
		initGUI();
	}
	
	
	/*
	 * initGUI method displays the login screen of the vendor application. 
	 * Registered users can login by typing their credentials.New users 
	 * can signup by giving a username and password.
	 */
	
	public void initGUI() {
		try {
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("iHungry Vendor");
			{
				panel1 = new JPanel();
				getContentPane().add(panel1, BorderLayout.CENTER);
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
						usernameLogin.setText("group14");
					}
					{
						passwordLogin = new JPasswordField();
						passwordLogin.setRequestFocusEnabled(false);
						passwordLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						passwordLogin.setText("group14");
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
			
			pack();
			setSize(800, 600);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	
	public void displaySignUpGui(){
		{	
			panel1.setVisible(false);
			
			signUpPanel = new JPanel();
			getContentPane().add(signUpPanel, BorderLayout.CENTER);
			GroupLayout signUpPanelLayout = new GroupLayout((JComponent)signUpPanel);
			signUpPanel.setLayout(signUpPanelLayout);
			signUpPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			signUpPanel.setPreferredSize(new java.awt.Dimension(687, 324));
			{
				signUpFinal = new JButton();
				signUpFinal.setText("SignUp");
			}
			{
				subPanel2 = new JPanel();
				GroupLayout subPanel2Layout = new GroupLayout((JComponent)subPanel2);
				subPanel2.setLayout(subPanel2Layout);
				//subPanel2.setTabTitle("SignUp");
				subPanel2.setBorder(BorderFactory.createTitledBorder("SignUp"));
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
					emailSignUpL = new JLabel();
					emailSignUpL.setText("Email:");
				}
				{
					addressSignUpL = new JLabel();
					addressSignUpL.setText("Address:");
				}
				{
					primSignUpL = new JLabel();
					primSignUpL.setText("Primary Phone:");
				}
				{
					secSignUpL = new JLabel();
					secSignUpL.setText("Secondary Phone:");
				}
				{
					secSignUpT = new JTextField();
				}
				{
					primSignUpT = new JTextField();
				}
				{
					addressSignUpT = new JTextField();
					addressSignUpT.setScrollOffset(1);
				}
				{
					emailSignUpT = new JTextField();
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
				subPanel2Layout.setVerticalGroup(subPanel2Layout.createSequentialGroup()
						.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(unameSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(unameSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(26)
								.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(passwordSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(26)
										.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(confirmpasswordSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
												.addComponent(confirmPasswordSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(20)
												.addGroup(subPanel2Layout.createParallelGroup()
														.addGroup(subPanel2Layout.createSequentialGroup()
																.addComponent(emailSignUpL, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
																.addGroup(subPanel2Layout.createSequentialGroup()
																		.addComponent(emailSignUpT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
																		.addGap(16)
																		.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																				.addComponent(addressSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
																				.addComponent(addressSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
																				.addGap(18)
																				.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																						.addComponent(primSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
																						.addComponent(primSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																						.addGap(21)
																						.addGroup(subPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																								.addComponent(secSignUpL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
																								.addComponent(secSignUpT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
				subPanel2Layout.setHorizontalGroup(subPanel2Layout.createSequentialGroup()
						.addGroup(subPanel2Layout.createParallelGroup()
								.addGroup(subPanel2Layout.createSequentialGroup()
										.addComponent(unameSignUpL, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
										.addGroup(subPanel2Layout.createSequentialGroup()
												.addComponent(passwordSignUpL, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
												.addGroup(subPanel2Layout.createSequentialGroup()
														.addComponent(confirmpasswordSignUpL, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
														.addGroup(subPanel2Layout.createSequentialGroup()
																.addComponent(emailSignUpL, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
																.addGroup(subPanel2Layout.createSequentialGroup()
																		.addComponent(addressSignUpL, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
																		.addGroup(subPanel2Layout.createSequentialGroup()
																				.addComponent(primSignUpL, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
																				.addGroup(subPanel2Layout.createSequentialGroup()
																						.addComponent(secSignUpL, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)))
																						.addGap(51)
																						.addGroup(subPanel2Layout.createParallelGroup()
																								.addGroup(subPanel2Layout.createSequentialGroup()
																										.addComponent(secSignUpT, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
																										.addGroup(subPanel2Layout.createSequentialGroup()
																												.addComponent(primSignUpT, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
																												.addGroup(subPanel2Layout.createSequentialGroup()
																														.addComponent(addressSignUpT, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
																														.addGroup(subPanel2Layout.createSequentialGroup()
																																.addComponent(emailSignUpT, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
																																.addGroup(subPanel2Layout.createSequentialGroup()
																																		.addComponent(confirmPasswordSignUpT, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
																																		.addGroup(subPanel2Layout.createSequentialGroup()
																																				.addComponent(passwordSignUpT, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
																																				.addGroup(subPanel2Layout.createSequentialGroup()
																																						.addComponent(unameSignUpT, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))));
			}

			signUpPanelLayout.setHorizontalGroup(signUpPanelLayout.createSequentialGroup()
				.addContainerGap(220, 220)
				.addGroup(signUpPanelLayout.createParallelGroup()
				    .addGroup(signUpPanelLayout.createSequentialGroup()
				        .addComponent(subPanel2, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, signUpPanelLayout.createSequentialGroup()
				        .addGap(135)
				        .addComponent(signUpFinal, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 175, Short.MAX_VALUE)))
				.addContainerGap(178, 178));
			signUpPanelLayout.setVerticalGroup(signUpPanelLayout.createSequentialGroup()
				.addContainerGap(65, 65)
				.addComponent(subPanel2, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
				.addGap(43)
				.addComponent(signUpFinal, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(88, Short.MAX_VALUE));
		}
		
		
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
	
	public JTextField getPasswordLogin(){
		return passwordLogin;
	}
	
	public JButton getSignUp(){
		return signup;
	}
	
	
	/**
	 * displayOrderGui method displays the main screen of the ihungry 
	 * Vendor application. The screen consists of a tabbed pane with 
	 * multiple tabs for grouping of orders based on their status. 
	 */
	
	public void displayOrderGui()
	{
		panel1.setVisible(false);
		{
		jTabbedPane1 = new JTabbedPane();
		getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
		jTabbedPane1.setPreferredSize(new java.awt.Dimension(790, 174));
		{
			viewMenu = new JPanel();
			jTabbedPane1.addTab("View/Update Menu", null, viewMenu, null);
			GroupLayout viewMenuLayout = new GroupLayout((JComponent)viewMenu);
			viewMenu.setLayout(viewMenuLayout);
			viewMenu.setPreferredSize(new java.awt.Dimension(788, 543));
			viewMenu.setBorder(BorderFactory.createTitledBorder(""));
			{
				menuFrontMessage = new JLabel();
				menuFrontMessage.setText("             View/Update Menu allows you to view the existing menu and to add/delete/update menu items.");
				menuFrontMessage.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
			}
			{
				name = new JLabel();
				name.setText("Name");
				name.setFont(new java.awt.Font("FreeSans",2,14));
				name.setForeground(new java.awt.Color(0,0,255));
			}
			{
				decription = new JLabel();
				decription.setText("Description");
				decription.setFont(new java.awt.Font("FreeSans",2,14));
				decription.setForeground(new java.awt.Color(0,0,255));
			}
			{
				price = new JLabel();
				price.setText("Price");
				price.setFont(new java.awt.Font("FreeSans",2,14));
				price.setForeground(new java.awt.Color(0,0,255));
			}
			{
				itemSubPanel = new JPanel();
				GroupLayout itemSubPanelLayout = new GroupLayout((JComponent)itemSubPanel);
				itemSubPanel.setLayout(itemSubPanelLayout);
				itemSubPanel.setBorder(BorderFactory.createTitledBorder(""));
				for(int i = 0; i < 5; i++ )
				{
					listOfItemNames.add(new JTextField());
					listOfDescription.add(new JTextField());
					listOfPrice.add(new JTextField());
					listOfEdit.add(new JButton("Save"));
					listOfDelete.add(new JButton("Delete"));
				}	
				
				itemSubPanelLayout.setHorizontalGroup(itemSubPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(itemSubPanelLayout.createParallelGroup()
								.addComponent(listOfItemNames.get(0), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(listOfItemNames.get(1), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(listOfItemNames.get(2), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(listOfItemNames.get(3), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(listOfItemNames.get(4), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
								.addGap(37)
								.addGroup(itemSubPanelLayout.createParallelGroup()
										.addComponent(listOfDescription.get(0), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDescription.get(1), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDescription.get(2), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDescription.get(3), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDescription.get(4), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
										.addGap(39)
										.addGroup(itemSubPanelLayout.createParallelGroup()
												.addComponent(listOfPrice.get(0), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfPrice.get(1), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfPrice.get(2), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfPrice.get(3), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfPrice.get(4), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
												.addGap(32)
												.addGroup(itemSubPanelLayout.createParallelGroup()
														.addComponent(listOfEdit.get(0), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfEdit.get(1), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfEdit.get(2), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfEdit.get(3), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfEdit.get(4), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
														.addGap(25)
														.addGroup(itemSubPanelLayout.createParallelGroup()
																.addComponent(listOfDelete.get(0), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDelete.get(1), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDelete.get(2), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDelete.get(3), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDelete.get(4), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED));
				itemSubPanelLayout.setVerticalGroup(itemSubPanelLayout.createParallelGroup()
						.addGroup(itemSubPanelLayout.createSequentialGroup()
								.addGap(12)
								.addGroup(itemSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(listOfEdit.get(0), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfItemNames.get(0), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDescription.get(0), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfPrice.get(0), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(listOfDelete.get(0), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
										.addGap(38)
										.addGroup(itemSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE) 
												.addComponent(listOfItemNames.get(1), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfDescription.get(1), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfPrice.get(1), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfEdit.get(1), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
												.addComponent(listOfDelete.get(1),GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
												.addGap(38)    
												.addGroup(itemSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE) 
														.addComponent(listOfItemNames.get(2), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfDescription.get(2), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfPrice.get(2), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfEdit.get(2), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
														.addComponent(listOfDelete.get(2),GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
														.addGap(38) 
														.addGroup(itemSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE) 
																.addComponent(listOfItemNames.get(3), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDescription.get(3), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfPrice.get(3), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfEdit.get(3), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																.addComponent(listOfDelete.get(3),GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
																.addGap(38) 
																.addGroup(itemSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE) 
																		.addComponent(listOfItemNames.get(4), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																		.addComponent(listOfDescription.get(4), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																		.addComponent(listOfPrice.get(4), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																		.addComponent(listOfEdit.get(4), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
																		.addComponent(listOfDelete.get(4),GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
																		.addGap(38) 
																		.addContainerGap(209, 209)));
				itemSubPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {listOfPrice.get(0), listOfEdit.get(0), listOfDelete.get(0)});
				itemSubPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {listOfItemNames.get(0), listOfDescription.get(0)});
				
			}
			{
				prev = new JButton();
				prev.setText("Previous");
				prev.setVisible(false);
			}
			{
				next = new JButton();
				next.setText("Next");
			}
			viewMenuLayout.setHorizontalGroup(viewMenuLayout.createSequentialGroup()
					.addContainerGap(99, 99)
					.addGroup(viewMenuLayout.createParallelGroup()
							.addComponent(menuFrontMessage, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 673, GroupLayout.PREFERRED_SIZE)
							.addComponent(itemSubPanel, GroupLayout.Alignment.LEADING, 0, 676, Short.MAX_VALUE)
							.addGroup(GroupLayout.Alignment.LEADING, viewMenuLayout.createSequentialGroup()
									.addGap(46)
									.addComponent(name, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addGap(74)
									.addComponent(decription, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addGap(79)
									.addGroup(viewMenuLayout.createParallelGroup()
											.addGroup(GroupLayout.Alignment.LEADING, viewMenuLayout.createSequentialGroup()
													.addComponent(price, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
													.addGap(0, 70, Short.MAX_VALUE))
													.addGroup(GroupLayout.Alignment.LEADING, viewMenuLayout.createSequentialGroup()
															.addGap(38)
															.addComponent(prev, 0, 99, Short.MAX_VALUE)))
															.addGap(29)
															.addComponent(next, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
															.addGap(16))));
			viewMenuLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {next, prev});
			viewMenuLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {name, decription});
			viewMenuLayout.setVerticalGroup(viewMenuLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(menuFrontMessage, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addGroup(viewMenuLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(name, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(decription, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(price, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(itemSubPanel, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(viewMenuLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(prev, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addComponent(next, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
									.addContainerGap(21, 21));
			viewMenuLayout.linkSize(SwingConstants.VERTICAL, new Component[] {next, prev});
			viewMenuLayout.linkSize(SwingConstants.VERTICAL, new Component[] {name, decription});
		}
		{
			displayCurrentOrder();
		}
			
		{
			toBeDelivered = new JPanel();
			jTabbedPane1.addTab("To Be Delivered", null, toBeDelivered, null);
		}
		{
			declinedOrders = new JPanel();
			jTabbedPane1.addTab("Declined Orders", null, declinedOrders, null);
			declinedOrders.setPreferredSize(new java.awt.Dimension(359, 144));
		}
		{
			orderHistory = new JPanel();
			jTabbedPane1.addTab("Order History", null, orderHistory, null);
		}
		}
		
	}
	
	/*
	 * This method is called to display the current orders tab. CurrentOrders
	 * tab is a split pane with the list of pending orders on one side and details
	 * of the currently selected order on the right side.
	 */

	private void displayCurrentOrder() {
		
		// create split pane and add it to the tabbed pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jTabbedPane1.addTab("Current Orders", null, splitPane, null);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(500);
		
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);		
		//setPreferredSize(new Dimension(450, 110));
		splitPane.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new java.awt.Dimension(483, 312));
		
		//create a panel with a JList which shows the Order list
		subPanel10 = new JPanel();
		splitPane.setRightComponent(subPanel10);
		GroupLayout subPanel10Layout = new GroupLayout((JComponent)subPanel10);
		subPanel10.setLayout(subPanel10Layout);
		{
			orderNoL1 = new JLabel();
			orderNoL1.setText("Order No :");
		}
		{
			/*ListModel list1Model = 
				new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });*/
			list1 = new JList(orderDetails);
			//list1.setModel(list1Model);
		}
		{
			accept = new JButton();
			accept.setText("Accept");
		}
		{
			decline = new JButton();
			decline.setText("Decline");
		}
		{
			custNoL1 = new JLabel();
			custNoL1.setText("Cust No :");
		}
		{
			orderNoT = new JTextField();
		}
		{
			custNoT = new JTextField();
		}
		subPanel10Layout.setVerticalGroup(subPanel10Layout.createSequentialGroup()
			.addContainerGap(39, 39)
			.addGroup(subPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(orderNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(orderNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(subPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(custNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(custNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addGap(23)
			.addComponent(list1, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
			.addGap(27)
			.addGroup(subPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(accept, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
			    .addComponent(decline, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
			.addContainerGap(24, 24));
		subPanel10Layout.setHorizontalGroup(subPanel10Layout.createSequentialGroup()
			.addContainerGap(30, 30)
			.addGroup(subPanel10Layout.createParallelGroup()
			    .addGroup(subPanel10Layout.createSequentialGroup()
			        .addGroup(subPanel10Layout.createParallelGroup()
			            .addComponent(custNoL1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, subPanel10Layout.createSequentialGroup()
			                .addComponent(orderNoL1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
			                .addGap(7)))
			        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
			        .addGroup(subPanel10Layout.createParallelGroup()
			            .addComponent(custNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
			            .addComponent(orderNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
			        .addGap(234))
			    .addComponent(list1, GroupLayout.Alignment.LEADING, 0, 447, Short.MAX_VALUE)
			    .addGroup(GroupLayout.Alignment.LEADING, subPanel10Layout.createSequentialGroup()
			        .addGap(0, 35, Short.MAX_VALUE)
			        .addComponent(accept, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
			        .addGap(33)
			        .addComponent(decline, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
			        .addGap(191)))
			.addContainerGap(22, 22));
		subPanel10Layout.linkSize(SwingConstants.VERTICAL, new Component[] {custNoT, orderNoT});
		subPanel10Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {custNoT, orderNoT});

	}
	
	public void loadDetailPane(Order order){
		
	}
	
	
	
	public JTable getTable(){
		return table;
	}
	
	public JPanel getSubPanel10(){
		return subPanel10;
	}
	
	public JTextField getOrderNo(){
		return orderNoT;
	}
	
	public JTextField getCustNo(){
		return custNoT;
	}
	
	
	public Object[][] getCurrentOrders(){
		return data;
	}
	
	/*
	 * This method appends the new orders fetched from server to the existing list.
	 */
	
	public void setCurrentOrders(Object[][] data, int pointer){
		
		for(int i=this.pointer;i<(this.pointer+pointer);i++)
			for(int j=0;j<data[0].length;j++){
				this.data[i][j] = data[i-this.pointer][j];
			}
	}
	
	/*
	 * This method is called to populate the list with the Order details. 
	 */
	
	public void setSelectedOrderDetails(String[] orderDetails)
	{
		
		list1.setListData(orderDetails);
		
	}
	
	public int getPointer(){
		return pointer;
	}
	
	public void setPointer(int pointer){
		this.pointer=pointer;
	}
	
	
	/**
	 * getEditButton method returns a reference of the list of Edit buttons
	 * to the model so that listeners can be setup for them.
	 * 
	 * @return a reference to a list of Edit buttons
	 */
	
	public ArrayList<JButton> getEditButton(){
			return listOfEdit;
		
	}
	
	/**
	 * getDeleteButton method returns a reference of the list of Delete buttons
	 * to the model so that listeners can be setup for them.
	 * 
	 * @return a reference to a list of Delete buttons
	 */
	
	public ArrayList<JButton> getDeleteButton(){
		return listOfDelete;
	
	}
	
	/**
	 * getItemNames method returns a reference of the list of ItemName fields
	 * to the model.
	 * 
	 * @return a reference to a list of ItemName textfield
	 */
	
	public JTextField getItemNames(int ind){
		return listOfItemNames.get(ind);
	}
	
	/**
	 * getDescription method returns a reference of the list of Description fields
	 * to the model.
	 * 
	 * @return a reference to a list of Description textfield
	 */
	
	public JTextField getDescription(int ind){
		return listOfDescription.get(ind);
	
	}
	
	/**
	 * getPrice method returns a reference of the list of Price fields
	 * to the model.
	 * 
	 * @return a reference to a list of Price textfield
	 */
	
	public JTextField getPrice(int ind){
		return listOfPrice.get(ind);
	}
	
	public JButton getNext(){
		return next;
	}
	
	public JButton getPrev(){
		return prev;
	}
	
	

}
