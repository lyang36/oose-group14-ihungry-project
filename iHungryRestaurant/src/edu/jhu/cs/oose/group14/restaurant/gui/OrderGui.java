package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Vector;

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
import javax.swing.table.DefaultTableModel;

import edu.jhu.cs.oose.group14.restaurant.model.iHungryRestaurant;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

public class OrderGui implements Observer {
	
	private Container contentPane;
	private JTabbedPane jTabbedPane1;
	
	private JPanel viewMenu;
	private JButton prev;
	private JButton next;
	private JPanel itemSubPanel;
	private JLabel price;
	private JLabel decription;
	private JLabel name;
	private JLabel menuFrontMessage;
	private List<JTextField> listOfItemNames = new ArrayList<JTextField>();
	private List<JTextField> listOfDescription = new ArrayList<JTextField>();
	private List<JTextField> listOfPrice = new ArrayList<JTextField>();
	private List<JButton> listOfEdit = new ArrayList<JButton>();
	private List<JButton> listOfDelete = new ArrayList<JButton>();
	
	private JSplitPane currentOrders;
	private JButton decline;
	private JButton accept;
	private JList list1;
	private JTextField orderNoT;
	private JTextField custNoT;
	private JPanel subPanel10;
	private JLabel orderNoL1;
	private JLabel custNoL1;
	
	private JSplitPane toBeDelivered;
	private JTable toBeDeliveredTable = null; 
	private DefaultTableModel toBeDeliveredTableModel = new DefaultTableModel();
	private JPanel toBeDeliveredSubPanel;
	private JLabel toBeDeliveredOrderNoL1;
	private JLabel toBeDeliveredCustNoL1;
	private JLabel toBeDeliveredCustAddrL1;
	private JList toBeDeliveredList;
	private JTextField toBeDeliveredOrderNoT;
	private JTextField toBeDeliveredCustNoT;
	private JTextField toBeDeliveredCustAddrT;
	private JButton deliver;
	
	private JSplitPane declinedOrders;
	private JTable declinedOrderTable = null; 
	private DefaultTableModel declinedOrderTableModel = new DefaultTableModel();
	private JPanel declinedOrderSubPanel;
	private JLabel declinedOrderOrderNoL1;
	private JLabel declinedOrderCustNoL1;
	private JList declinedOrderList;
	private JTextField declinedOrderOrderNoT;
	private JTextField declinedOrderCustNoT;

	private JSplitPane orderHistory;
	private JTable orderHistoryTable = null; 
	private DefaultTableModel orderHistoryTableModel = new DefaultTableModel();
	private JPanel orderHistorySubPanel;
	private JLabel orderHistoryOrderNoL1;
	private JLabel orderHistoryCustNoL1;
	private JList orderHistoryList;
	private JTextField orderHistoryOrderNoT;
	private JTextField orderHistoryCustNoT;
	
	private Vector<String> columnNames = new Vector<String>(); 
	private int pointer = 0;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table = null; 
	private String[] orderDetails = new String[50];
	private int orderDetailsPointer = 0;
	

	public OrderGui(Container contentPane){
		this.contentPane = contentPane;
		
		columnNames.add("Order No");
		columnNames.add("Customer Id");
		columnNames.add("Item Count");
		columnNames.add("Order Placed Time");
	}
	
	/**
	 * displayOrderGui method displays the main screen of the ihungry 
	 * Vendor application. The screen consists of a tabbed pane with 
	 * multiple tabs for grouping of orders based on their status. 
	 */
	
	public void displayOrderScreen()
	{
		for(int i=0;i<contentPane.getComponentCount();i++)
			contentPane.getComponents()[i].setVisible(false);
		//handle for if restaurant is null(new user) and not null(existing user)
		
		{
			jTabbedPane1 = new JTabbedPane();
			contentPane.add(jTabbedPane1, BorderLayout.CENTER);
			jTabbedPane1.setPreferredSize(new java.awt.Dimension(790, 174));
						
			displayMenu();
			displayCurrentOrder();
			displayToBeDeliveredOrder();
			displayDeclinedOrder();								
			displayOrderHistory();
			//showUpdateScreen();
			
		}
	}
	
	
	private void displayMenu()
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
	
	

	
	/*
	 * This method is called to display the current orders tab. CurrentOrders
	 * tab is a split pane with the list of pending orders on one side and details
	 * of the currently selected order on the right side.
	 */

	private void displayCurrentOrder() {
		
		// create split pane and add it to the tabbed pane
		currentOrders = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jTabbedPane1.addTab("Current Orders", null, currentOrders, null);
		currentOrders.setOneTouchExpandable(true);
		currentOrders.setDividerLocation(500);
				
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);
		
		//First row is selected by default
		if(table.getRowCount()>0)
		{
			table.setRowSelectionInterval(0,0);
			table.addRowSelectionInterval(0,0);
		}
		
		JScrollPane scrollPane = new JScrollPane(table);		
		currentOrders.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new java.awt.Dimension(483, 312));
		
		//create a panel with a JList which shows the Order list
		subPanel10 = new JPanel();
		currentOrders.setRightComponent(subPanel10);
		GroupLayout subPanel10Layout = new GroupLayout((JComponent)subPanel10);
		subPanel10.setLayout(subPanel10Layout);
		{
			orderNoL1 = new JLabel();
			orderNoL1.setText("Order No :");
		}
		{
			list1 = new JList(orderDetails);
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
	
	
	private void displayToBeDeliveredOrder() {
		
		// create split pane and add it to the tabbed pane
		toBeDelivered = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jTabbedPane1.addTab("To Be Delivered", null, toBeDelivered, null);
		toBeDelivered.setOneTouchExpandable(true);
		toBeDelivered.setDividerLocation(500);
				
		toBeDeliveredTable = new JTable(toBeDeliveredTableModel);
		toBeDeliveredTable.setRowHeight(30);
		toBeDeliveredTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//First row is selected by default
		if(table.getRowCount()>0)
		{
			table.setRowSelectionInterval(0,0);
			table.addRowSelectionInterval(0,0);
		}
		
		JScrollPane scrollPane = new JScrollPane(toBeDeliveredTable);
		toBeDelivered.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new java.awt.Dimension(483, 312));
		
		//create a panel with a JList which shows the Order list
		toBeDeliveredSubPanel = new JPanel();
		toBeDelivered.setRightComponent(toBeDeliveredSubPanel);
		GroupLayout toBeDeliveredSubPanelLayout = new GroupLayout((JComponent)toBeDeliveredSubPanel);
		toBeDeliveredSubPanel.setLayout(toBeDeliveredSubPanelLayout);
		{
			toBeDeliveredOrderNoL1 = new JLabel();
			toBeDeliveredOrderNoL1.setText("Order No :");
		}	
		{
			toBeDeliveredCustNoL1 = new JLabel();
			toBeDeliveredCustNoL1.setText("Cust No :");
		}
		{
			toBeDeliveredCustAddrL1 = new JLabel();
			toBeDeliveredCustAddrL1.setText("Cust Addr :");
		}
		{
			toBeDeliveredOrderNoT = new JTextField();
		}
		{
			toBeDeliveredCustNoT = new JTextField();
		}
		{
			toBeDeliveredCustAddrT = new JTextField();
		}
		{
			toBeDeliveredList = new JList(orderDetails);		
		}	
		{
			deliver = new JButton();
			deliver.setText("Deliver");
		}
		toBeDeliveredSubPanelLayout.setVerticalGroup(toBeDeliveredSubPanelLayout.createSequentialGroup()
			.addContainerGap(30, 30)
			.addGroup(toBeDeliveredSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(toBeDeliveredOrderNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(toBeDeliveredOrderNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(toBeDeliveredSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(toBeDeliveredCustNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(toBeDeliveredCustNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(toBeDeliveredSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(toBeDeliveredCustAddrT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(toBeDeliveredCustAddrL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addGap(20)
			.addComponent(toBeDeliveredList, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
			.addGap(27)
			.addGroup(toBeDeliveredSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(deliver, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
			.addContainerGap(24, 24));
		toBeDeliveredSubPanelLayout.setHorizontalGroup(toBeDeliveredSubPanelLayout.createSequentialGroup()
			.addContainerGap(30, 30)
			.addGroup(toBeDeliveredSubPanelLayout.createParallelGroup()
			    .addGroup(toBeDeliveredSubPanelLayout.createSequentialGroup()
			        .addGroup(toBeDeliveredSubPanelLayout.createParallelGroup()
			            .addComponent(toBeDeliveredCustAddrL1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, toBeDeliveredSubPanelLayout.createSequentialGroup()
			                .addComponent(toBeDeliveredCustNoL1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
			                .addGap(7))
			                .addGroup(GroupLayout.Alignment.LEADING, toBeDeliveredSubPanelLayout.createSequentialGroup()
					                .addComponent(toBeDeliveredOrderNoL1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					                .addGap(7)) )
			        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
			        .addGroup(toBeDeliveredSubPanelLayout.createParallelGroup()
			            .addComponent(toBeDeliveredCustAddrT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
			            .addComponent(toBeDeliveredCustNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
			            .addComponent(toBeDeliveredOrderNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
			        .addGap(234))
			    .addComponent(toBeDeliveredList, GroupLayout.Alignment.LEADING, 0, 447, Short.MAX_VALUE)
			    .addGroup(GroupLayout.Alignment.LEADING, toBeDeliveredSubPanelLayout.createSequentialGroup()
			        .addGap(0, 35, Short.MAX_VALUE)
			        .addComponent(deliver, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
			        .addGap(260)))
			.addContainerGap(22, 22));
		toBeDeliveredSubPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {toBeDeliveredCustNoT, toBeDeliveredOrderNoT});
		toBeDeliveredSubPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {toBeDeliveredCustNoT, toBeDeliveredOrderNoT});
	}
	
	
	/*
	 * This method is called to display the Declined Order tab. Declined Order
	 * tab is a split pane with the list of declined orders on one side and details
	 * of the currently selected order on the right side.
	 */
	private void displayDeclinedOrder() {
		
		// create split pane and add it to the tabbed pane
		declinedOrders = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jTabbedPane1.addTab("Declined Orders", null, declinedOrders, null);
		declinedOrders.setOneTouchExpandable(true);
		declinedOrders.setDividerLocation(500);
				
		declinedOrderTable = new JTable(declinedOrderTableModel);
		declinedOrderTable.setRowHeight(30);
		declinedOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//First row is selected by default
		if(table.getRowCount()>0)
		{
			table.setRowSelectionInterval(0,0);
			table.addRowSelectionInterval(0,0);
		}
		
		JScrollPane scrollPane = new JScrollPane(declinedOrderTable);
		declinedOrders.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new java.awt.Dimension(483, 312));
		
		//create a panel with a JList which shows the Order list
		declinedOrderSubPanel = new JPanel();
		declinedOrders.setRightComponent(declinedOrderSubPanel);
		GroupLayout declinedOrderSubPanelLayout = new GroupLayout((JComponent)declinedOrderSubPanel);
		declinedOrderSubPanel.setLayout(declinedOrderSubPanelLayout);
		{
			declinedOrderOrderNoL1 = new JLabel();
			declinedOrderOrderNoL1.setText("Order No :");
		}
		{
			declinedOrderList = new JList(orderDetails);		
		}		
		{
			declinedOrderCustNoL1 = new JLabel();
			declinedOrderCustNoL1.setText("Cust No :");
		}
		{
			declinedOrderOrderNoT = new JTextField();
		}
		{
			declinedOrderCustNoT = new JTextField();
		}
		declinedOrderSubPanelLayout.setVerticalGroup(declinedOrderSubPanelLayout.createSequentialGroup()
			.addContainerGap(39, 39)
			.addGroup(declinedOrderSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(declinedOrderOrderNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(declinedOrderOrderNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(declinedOrderSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(declinedOrderCustNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(declinedOrderCustNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addGap(23)
			.addComponent(declinedOrderList, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
			.addGap(27)
			.addGroup(declinedOrderSubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
			.addContainerGap(24, 24));
		declinedOrderSubPanelLayout.setHorizontalGroup(declinedOrderSubPanelLayout.createSequentialGroup()
			.addContainerGap(30, 30)
			.addGroup(declinedOrderSubPanelLayout.createParallelGroup()
			    .addGroup(declinedOrderSubPanelLayout.createSequentialGroup()
			        .addGroup(declinedOrderSubPanelLayout.createParallelGroup()
			            .addComponent(declinedOrderCustNoL1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, declinedOrderSubPanelLayout.createSequentialGroup()
			                .addComponent(declinedOrderOrderNoL1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
			                .addGap(7)))
			        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
			        .addGroup(declinedOrderSubPanelLayout.createParallelGroup()
			            .addComponent(declinedOrderCustNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
			            .addComponent(declinedOrderOrderNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
			        .addGap(234))
			    .addComponent(declinedOrderList, GroupLayout.Alignment.LEADING, 0, 447, Short.MAX_VALUE)
			    .addGroup(GroupLayout.Alignment.LEADING, declinedOrderSubPanelLayout.createSequentialGroup()
			        .addGap(0, 35, Short.MAX_VALUE)))
			.addContainerGap(22, 22));
		declinedOrderSubPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {declinedOrderCustNoT, declinedOrderOrderNoT});
		declinedOrderSubPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {declinedOrderCustNoT, declinedOrderOrderNoT});
	}
	
	/*
	 * This method is called to display the Order History tab. Order History
	 * tab is a split pane with the list of old orders on one side and details
	 * of the currently selected order on the right side.
	 */
	private void displayOrderHistory() {
		
		// create split pane and add it to the tabbed pane
		orderHistory = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jTabbedPane1.addTab("Order History", null, orderHistory, null);
		orderHistory.setOneTouchExpandable(true);
		orderHistory.setDividerLocation(500);
				
		orderHistoryTable = new JTable(orderHistoryTableModel);
		orderHistoryTable.setRowHeight(30);
		orderHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//First row is selected by default
		if(table.getRowCount()>0)
		{
			table.setRowSelectionInterval(0,0);
			table.addRowSelectionInterval(0,0);
		}
		
		JScrollPane scrollPane = new JScrollPane(orderHistoryTable);
		orderHistory.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new java.awt.Dimension(483, 312));
		
		//create a panel with a JList which shows the Order list
		orderHistorySubPanel = new JPanel();
		orderHistory.setRightComponent(orderHistorySubPanel);
		GroupLayout orderHistorySubPanelLayout = new GroupLayout((JComponent)orderHistorySubPanel);
		orderHistorySubPanel.setLayout(orderHistorySubPanelLayout);
		{
			orderHistoryOrderNoL1 = new JLabel();
			orderHistoryOrderNoL1.setText("Order No :");
		}
		{
			orderHistoryList = new JList(orderDetails);		
		}		
		{
			orderHistoryCustNoL1 = new JLabel();
			orderHistoryCustNoL1.setText("Cust No :");
		}
		{
			orderHistoryOrderNoT = new JTextField();
		}
		{
			orderHistoryCustNoT = new JTextField();
		}
		orderHistorySubPanelLayout.setVerticalGroup(orderHistorySubPanelLayout.createSequentialGroup()
			.addContainerGap(39, 39)
			.addGroup(orderHistorySubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(orderHistoryOrderNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(orderHistoryOrderNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(orderHistorySubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(orderHistoryCustNoT, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
			    .addComponent(orderHistoryCustNoL1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addGap(23)
			.addComponent(orderHistoryList, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
			.addGap(27)
			.addGroup(orderHistorySubPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
			.addContainerGap(24, 24));
		orderHistorySubPanelLayout.setHorizontalGroup(orderHistorySubPanelLayout.createSequentialGroup()
			.addContainerGap(30, 30)
			.addGroup(orderHistorySubPanelLayout.createParallelGroup()
			    .addGroup(orderHistorySubPanelLayout.createSequentialGroup()
			        .addGroup(orderHistorySubPanelLayout.createParallelGroup()
			            .addComponent(orderHistoryCustNoL1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, orderHistorySubPanelLayout.createSequentialGroup()
			                .addComponent(orderHistoryOrderNoL1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
			                .addGap(7)))
			        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
			        .addGroup(orderHistorySubPanelLayout.createParallelGroup()
			            .addComponent(orderHistoryCustNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
			            .addComponent(orderHistoryOrderNoT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
			        .addGap(234))
			    .addComponent(orderHistoryList, GroupLayout.Alignment.LEADING, 0, 447, Short.MAX_VALUE)
			    .addGroup(GroupLayout.Alignment.LEADING, orderHistorySubPanelLayout.createSequentialGroup()
			        .addGap(0, 35, Short.MAX_VALUE)))
			.addContainerGap(22, 22));
		orderHistorySubPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {orderHistoryCustNoT, orderHistoryOrderNoT});
		orderHistorySubPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {orderHistoryCustNoT, orderHistoryOrderNoT});
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
	
	public JTextField getOrderNoT(){
		return orderNoT;
	}
	
	public JTextField getCustNo(){
		return custNoT;
	}
	
	public JButton getAcceptButton(){
		return accept;
	}
	
	public JButton getDeclineButton(){
		return decline;
	}
	
	public JTable getOrderHistoryTable(){
		return orderHistoryTable;
	}
	
	public JPanel getOrderHistorySubPanel(){
		return orderHistorySubPanel;
	}
	
	public JTextField getOrderHistoryOrderNo(){
		return orderHistoryOrderNoT;
	}
	
	public JTextField getOrderHistoryCustNo(){
		return orderHistoryCustNoT;
	}
	
	public JList getOrderHistoryList(){
		return orderHistoryList;
	}
	
	public JTextField getOrderHistoryOrderrNoT(){
		return orderHistoryOrderNoT;
	}
	
	public JTable getDeclinedOrderTable(){
		return declinedOrderTable;
	}
	
	public JPanel getDeclinedOrderSubPanel(){
		return declinedOrderSubPanel;
	}
	
	public JTextField getDeclinedOrderOrderNo(){
		return declinedOrderOrderNoT;
	}
	
	public JTextField getDeclinedOrderCustNo(){
		return declinedOrderCustNoT;
	}
	
	public JList getDeclinedOrderList(){
		return declinedOrderList;
	}
	
	public JTextField getDeclinedOrderNoT(){
		return declinedOrderOrderNoT;
	}
	
	public JTable getToBeDeliveredTable(){
		return toBeDeliveredTable;
	}
	
	public JPanel getToBeDeliveredSubPanel(){
		return toBeDeliveredSubPanel;
	}
	
	public JTextField getToBeDeliveredOrderNo(){
		return toBeDeliveredOrderNoT;
	}
	
	public JTextField getToBeDeliveredCustNo(){
		return toBeDeliveredCustNoT;
	}
	
	public JList getToBeDeliveredList(){
		return toBeDeliveredList;
	}
	
	public JButton getDeliverButton(){
		return deliver;
	}
	
	public JTextField getToBeDeliveredOrderNoT(){
		return toBeDeliveredOrderNoT;
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
	
	public List<JButton> getEditButton(){
			return listOfEdit;
		
	}
	
	/**
	 * getDeleteButton method returns a reference of the list of Delete buttons
	 * to the model so that listeners can be setup for them.
	 * 
	 * @return a reference to a list of Delete buttons
	 */
	
	public List<JButton> getDeleteButton(){
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
	
	
	/*private void showUpdateScreen()
	{
		updateScreen = new JPanel();
		jTabbedPane1.addTab("View/Update Menu", null, updateScreen, null);
		GroupLayout viewMenuLayout = new GroupLayout((JComponent)viewMenu);
		viewMenu.setLayout(viewMenuLayout);
		viewMenu.setPreferredSize(new java.awt.Dimension(788, 543));
		viewMenu.setBorder(BorderFactory.createTitledBorder(""));
		{
			menuFrontMessage = new JLabel();
			menuFrontMessage.setText("             View/Update Menu allows you to view the existing menu and to add/delete/update menu items.");
			menuFrontMessage.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
		}
	}*/

	 public void update(Observable obs, Object x) {
		 
		 
		 iHungryRestaurant hugryRestaurant = (iHungryRestaurant) obs;
		 Set<Order> orders = hugryRestaurant.getPendingOrders();
		
		 Vector data = new Vector();
		 
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();) {
			Order order = i.next();
			Vector row = new Vector();
			row.add(order.getOrderID());
			row.add(order.getCustID());
			row.add(order.getOrderItems().size());
			row.add(new Date(order.getTime()));
			data.add(row);
		}
		 
		 int index = 0;
		 
		 if(table.getRowCount()>0){
			 index = table.getSelectedRow();
		 }
		 
		 tableModel.setDataVector(data, columnNames) ;
		 
		 
		 if(table.getRowCount()>0){
			 if(table.getRowCount() == 1)
				 index = 0;
			 table.setRowSelectionInterval(index,index);
			 table.addRowSelectionInterval(index,index);
		 }
		 table.repaint();
		 
		 //Populate the orderNo and cusNo fields when a change is made to the existing table
		 int j = 0;
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();){
			 Order order = i.next();
			 if(index==j)
			 {
				 orderNoT.setText(order.getOrderID());
				 custNoT.setText(order.getCustID());
				 break;
			 }
			 j++;
			 
		 }
		 	 
		 
		 
		 orders = hugryRestaurant.getAcceptedOrders();
		 Vector acceptedOrderData  = new Vector();
		 
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();) {
			Order order = i.next();
			Vector row = new Vector();
			row.add(order.getOrderID());
			row.add(order.getCustID());
			row.add(order.getOrderItems().size());
			row.add(new Date(order.getTime()));
			acceptedOrderData.add(row);
		}
		 
		 
		 if(toBeDeliveredTable.getRowCount()>0){
			 index = toBeDeliveredTable.getSelectedRow();
		 }
		 
		 toBeDeliveredTableModel.setDataVector(acceptedOrderData, columnNames) ;
		 		 
		 
		 if(toBeDeliveredTable.getRowCount()>0){
			 if(toBeDeliveredTable.getRowCount() == 1)
				 index = 0;
			 toBeDeliveredTable.setRowSelectionInterval(index,index);
			 toBeDeliveredTable.addRowSelectionInterval(index,index);
		 }
		 toBeDeliveredTable.repaint();
		 
		//Populate the orderNo and custNo fields when a change is made to the existing table
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();){
			 Order order = i.next();
			 if(index==j)
			 {
				 toBeDeliveredOrderNoT.setText(order.getOrderID());
				 toBeDeliveredCustNoT.setText(order.getCustID());
				 break;
			 }
			 j++;
			 
		 }
		
		 
		 orders = hugryRestaurant.getDeclinedOrders();
		 Vector declinedOrderData  = new Vector();
		 
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();) {
			Order order = i.next();
			Vector row = new Vector();
			row.add(order.getOrderID());
			row.add(order.getCustID());
			row.add(order.getOrderItems().size());
			row.add(new Date(order.getTime()));
			declinedOrderData.add(row);
		}
		 
		 if(declinedOrderTable.getRowCount()>0){
			 index = declinedOrderTable.getSelectedRow();
		 }
		 
		 declinedOrderTableModel.setDataVector(declinedOrderData, columnNames) ;
		 		 
		
		 if(declinedOrderTable.getRowCount()>0){
			 if(declinedOrderTable.getRowCount() == 1)
				 index = 0;
			 declinedOrderTable.setRowSelectionInterval(index,index);
			 declinedOrderTable.addRowSelectionInterval(index,index);
		 }
		 declinedOrderTable.repaint();
		 
		//Populate the orderNo and custNo fields when a change is made to the existing table
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();){
			 Order order = i.next();
			 if(index==j)
			 {
				 declinedOrderOrderNoT.setText(order.getOrderID());
				 declinedOrderCustNoT.setText(order.getCustID());
				 break;
			 }
			 j++;
			 
		 }
		 
		 
		 orders = hugryRestaurant.getOldOrders();
		 Vector oldOrderData  = new Vector();
		 
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();) {
			Order order = i.next();
			Vector row = new Vector();
			row.add(order.getOrderID());
			row.add(order.getCustID());
			row.add(order.getOrderItems().size());
			row.add(new Date(order.getTime()));
			oldOrderData.add(row);
		}
		 
		 if(orderHistoryTable.getRowCount()>0){
			 index = orderHistoryTable.getSelectedRow();
		 }
		 
		 orderHistoryTableModel.setDataVector(oldOrderData, columnNames);
		 		 
		 
		 if(orderHistoryTable.getRowCount()>0){
			 if(orderHistoryTable.getRowCount() == 1)
				 index = 0;
			 orderHistoryTable.setRowSelectionInterval(index,index);
			 orderHistoryTable.addRowSelectionInterval(index,index);
		 }
		 orderHistoryTable.repaint();
		 
		//Populate the orderNo and custNo fields when a change is made to the existing table
		 for (Iterator<Order> i = orders.iterator(); i.hasNext();){
			 Order order = i.next();
			 if(index==j)
			 {
				 orderHistoryOrderNoT.setText(order.getOrderID());
				 orderHistoryCustNoT.setText(order.getCustID());
				 break;
			 }
			 j++;
			 
		 }
	 }
}
