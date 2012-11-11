package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModel;

import java.util.*;

/*
 * ihungryRestaurantGui class is responsible for displaying the vendor-Gui screens
 *  of the ihungry application.It holds methods to display various Gui screens 
 *  for ihungry vendor application and few methods to return the swing 
 *  components in the current screen to the model class.
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
	
	
	/*
	 * Main method creates an object of ihungryVendorGui class and an object 
	 * of model class and passes the Gui class object to model object thus 
	 * creating a connection between them.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ihungryRestaurantGui gui = new ihungryRestaurantGui();
				ihungryRestaurantModel model = new ihungryRestaurantModel(gui);
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
			currentOrders = new JPanel();
			jTabbedPane1.addTab("Current Orders", null, currentOrders, null);
			GroupLayout currentOrdersLayout = new GroupLayout((JComponent)currentOrders);
			currentOrders.setLayout(currentOrdersLayout);
			currentOrders.setPreferredSize(new java.awt.Dimension(652, 383));
			{
				jButton1 = new JButton();
				jButton1.setText("Click");
			}
			{
				panel4 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)panel4);
				panel4.setLayout(jPanel1Layout);
				{
					orderNoL = new JLabel();
					orderNoL.setText("Order No");
					orderNoL.setForeground(new java.awt.Color(0,0,255));
				}
				{
					listL = new JLabel();
					listL.setText("List");
					listL.setForeground(new java.awt.Color(0,0,255));
				}
				{
					statusL = new JLabel();
					statusL.setText("Status");
					statusL.setForeground(new java.awt.Color(0,0,255));
				}
				{
					timeL = new JLabel();
					timeL.setText("Order Placed Time");
					timeL.setForeground(new java.awt.Color(0,0,255));
				}
				{
					scrollBar1 = new JScrollBar();
					scrollBar1.setBlockIncrement(9);
				}
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(orderNoL, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(listL, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addComponent(statusL, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addComponent(timeL, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 83, Short.MAX_VALUE)
					.addComponent(scrollBar1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE));
				jPanel1Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {orderNoL, statusL});
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addGap(6)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(scrollBar1, GroupLayout.Alignment.LEADING, 0, 537, Short.MAX_VALUE)
					    .addGroup(jPanel1Layout.createSequentialGroup()
					        .addGap(27)
					        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					            .addComponent(orderNoL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					            .addComponent(listL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					            .addComponent(statusL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					            .addComponent(timeL, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					        .addGap(483))));
				jPanel1Layout.linkSize(SwingConstants.VERTICAL, new Component[] {orderNoL, statusL});
			}
			currentOrdersLayout.setHorizontalGroup(currentOrdersLayout.createSequentialGroup()
				.addComponent(panel4, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
				.addGap(80)
				.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(134, Short.MAX_VALUE));
			currentOrdersLayout.setVerticalGroup(currentOrdersLayout.createParallelGroup()
				.addComponent(panel4, GroupLayout.Alignment.LEADING, 0, 543, Short.MAX_VALUE)
				.addGroup(GroupLayout.Alignment.LEADING, currentOrdersLayout.createSequentialGroup()
				    .addGap(361)
				    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				    .addContainerGap(147, Short.MAX_VALUE)));
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