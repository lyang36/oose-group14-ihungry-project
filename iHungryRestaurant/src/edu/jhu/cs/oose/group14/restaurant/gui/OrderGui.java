package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public class OrderGui {
	
	private Container contentPane;
	
	private JTabbedPane jTabbedPane1;
	private JPanel declinedOrders;
	private JPanel currentOrders;
	private JPanel viewMenu;
	private JButton decline;
	private JButton accept;
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
	
	
	public OrderGui(Container contentPane){
		this.contentPane = contentPane;
	}
	
	/**
	 * displayOrderGui method displays the main screen of the ihungry 
	 * Vendor application. The screen consists of a tabbed pane with 
	 * multiple tabs for grouping of orders based on their status. 
	 */
	
	public void displayOrderScreen()
	{
		
		contentPane.getComponents()[0].setVisible(false);
		//handle for if restaurant is null(new user) and not null(existing user)
		
		{
			jTabbedPane1 = new JTabbedPane();
			contentPane.add(jTabbedPane1, BorderLayout.CENTER);
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
	
	public JTabbedPane getTabbedPane(){
		return jTabbedPane1;
	}

}
