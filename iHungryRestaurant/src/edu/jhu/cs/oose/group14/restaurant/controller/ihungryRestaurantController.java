package edu.jhu.cs.oose.group14.restaurant.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.group14.restaurant.gui.ihungryRestaurantGui;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;

import java.util.*;

/**
 * ihungryRestaurantModel class is the model class for ihungry vendor 
 * application. It uses other helper classes like LoginHandler to handle
 * specific functions.
 */

public class ihungryRestaurantController {
	
	private ihungryRestaurantGui gui;
	private ihungryRestaurantModelInterface model;
	private ArrayList<JButton> listOfEdit;
    private ArrayList<JButton> listOfDelete;
    private ArrayList<String> listOfItemNames, listOfDescription, listOfPrice ;
	private int itemNo =0;
	private int indx=0;
	private ArrayList<Order> currOrders = new ArrayList<Order>();
	private Object[][] data =  new Object[1000][5];
	private int pointer = 0;
	private Restaurant restaurant;
	

	
	/*
	 * Constructor for ihungryVendorController
	 */
	
	public ihungryRestaurantController(ihungryRestaurantGui gui){
		this.gui = gui;
		model = new ihungryRestaurantModelImpl();
		listOfEdit = gui.getOrderGui().getEditButton();
	    listOfDelete = gui.getOrderGui().getDeleteButton();
	    listOfItemNames = new ArrayList<String>();
	    listOfDescription = new ArrayList<String>();
	    listOfPrice = new ArrayList<String>();
		this.gui.getLoginGui().getLogin().addActionListener(new LoginListener());
		this.gui.getLoginGui().getSignUp().addActionListener(new SignUpListener());
		
		//some sample data
		Item i1 = new Item("I001","Pizza","Small Pizza",4.45,new Rating(0,0),new Album());
		Item i2 = new Item("I002","Pizza Big","Big Pizza",7.30,new Rating(0,0),new Album());
		Item i3 = new Item("I003","Burger","Burger",2.25,new Rating(0,0),new Album());
		ArrayList<OrderItem> temp1 = new ArrayList<OrderItem>();
		ArrayList<OrderItem> temp2 = new ArrayList<OrderItem>();
		ArrayList<OrderItem> temp3 = new ArrayList<OrderItem>();
		temp1.add(new OrderItem(i1,1));
		temp1.add(new OrderItem(i2,2));
		Order o1 = new Order("O001","C001","R001",1,temp1);
		temp2.add(new OrderItem(i2,2));
		temp2.add(new OrderItem(i3,3));
		Order o2 = new Order("O002","C002","R001",1,temp2);
		temp3.add(new OrderItem(i3,3));
		temp3.add(new OrderItem(i1,1));
		Order o3 = new Order("O003","C003","R001",1,temp3);
		currOrders.add(o1);
		currOrders.add(o2);
		currOrders.add(o3);
		data[0][0]= o1.getOrderID();
		data[0][1]=o1.getCustID();
		data[0][2]=o1.getStatus();
		data[1][0]= o2.getOrderID();
		data[1][1]=o2.getCustID();
		data[1][2]=o2.getStatus();
		data[2][0]= o3.getOrderID();
		data[2][1]=o3.getCustID();
		data[2][2]=o3.getStatus();
		pointer = 3;
		
		/*listOfItemNames.add("Pizza");
		listOfItemNames.add("Pizza Big");
		listOfItemNames.add("Burger");
		listOfDescription.add("Garden Fresh Pizza");
		listOfDescription.add("Garden Fresh Pizza Big");
		listOfDescription.add("Veggie Burger");
		listOfPrice.add(Double.toString(4.45));
		listOfPrice.add(Double.toString(7.30));
		listOfPrice.add(Double.toString(2.30));*/
		setCurrentOrders();

	}
	
	
	
	/**
	 * LoginListener class implements the actionPerformed method checking for
	 * the user credentials and allowing the user to log into the application.
	 * 
	 * @author parkavi
	 *
	 */
	
	class LoginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) 
		{
			composeForLogIn();			
		}
		
		
		public void composeForLogIn(){
			
			String username = gui.getLoginGui().getUsernameLogin().getText();
			String password = new String(gui.getLoginGui().getPasswordLogin().getPassword());
			
			/*listOfItemNames.add("Pizza");
			listOfItemNames.add("Small Pizza");
			listOfItemNames.add("Big Pizza");
			listOfItemNames.add("Burger");
			listOfItemNames.add("Small Burger");
			listOfItemNames.add("Big Burger");
			
			listOfDescription.add("Pizza");
			listOfDescription.add("Small Pizza");
			listOfDescription.add("Big Pizza");
			listOfDescription.add("Burger");
			listOfDescription.add("Small Burger");
			listOfDescription.add("Big Burger");
			
			listOfPrice.add(new String("4.00"));
			listOfPrice.add(new String("5.00"));
			listOfPrice.add(new String("6.00"));
			listOfPrice.add(new String("4.00"));
			listOfPrice.add(new String("5.00"));
			listOfPrice.add(new String("6.00"));
			
			gui.getOrderGui().displayOrderScreen();
			gui.getOrderGui().getTabbedPane().setSelectedIndex(1);
			onClickNext(); 
			for(int i=0;i<5;i++)
			{
				listOfEdit.get(i).addActionListener(new EditButtonListener() );
				listOfDelete.get(i).addActionListener(new DeleteButtonListener() );
			}
			gui.getOrderGui().getNext().addActionListener(new NextButtonListener());
			gui.getOrderGui().getPrev().addActionListener(new PrevButtonListener());
			gui.getOrderGui().getTable().getSelectionModel().addListSelectionListener(new SelectionListener());*/
			
			//check for errors in login
			if (model.loginCheck(username,password))
				if (model.attemptLogin(username,password))
				{
					System.out.println("successfully logged in");
					restaurant = model.getRestaurantInfo(username,password);
					//populate listOfItemNames,listOfDescription,listOfPrice from the retrieved Restaurant object
					populateMenuFields();
					gui.getOrderGui().displayOrderScreen();
					gui.getOrderGui().getTabbedPane().setSelectedIndex(1);
					onClickNext(); 
					for(int i=0;i<5;i++)
					{
						listOfEdit.get(i).addActionListener(new EditButtonListener() );
						listOfDelete.get(i).addActionListener(new DeleteButtonListener() );
					}
					gui.getOrderGui().getNext().addActionListener(new NextButtonListener());
					gui.getOrderGui().getPrev().addActionListener(new PrevButtonListener());
					gui.getOrderGui().getTable().getSelectionModel().addListSelectionListener(new SelectionListener());
				}
				else
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Password is incorrect.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
				}
			else
			{
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Username does not exists.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
			}
		}
		
		
		public void populateMenuFields(){
			
			if (restaurant.getMenu().getItems().size()!=0)
			{
				for(int i=0;i<restaurant.getMenu().getItems().size();i++)
				{
					listOfItemNames.add(restaurant.getMenu().getItemAt(i).getItemName());
					listOfDescription.add(restaurant.getMenu().getItemAt(i).getItemDescription());
					listOfPrice.add(restaurant.getMenu().getItemAt(i).getItemPrice()+"");
				}
			}
		}
	}
	
	
	/**
	 * SignUpListener class implements the actionPerformed method. Sends a JSON
	 * object of the Restaurant to the server which creates a new restaurant
	 * 
	 * @author parkavi
	 *
	 */
	
	class SignUpListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			gui.getSignupGui().displayFirstPageSignUpGui();
			gui.getSignupGui().getNextSignUp().addActionListener(new NextSignUpListener());
			
		}
		
		class NextSignUpListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event) {
				gui.getSignupGui().displaySecondPageSignUpGui();
				gui.getSignupGui().getSignUp2().addActionListener(new NextSignUpListener());
				composeForSignUp();
						                  
			}
			
			public void composeForSignUp(){
				
				String username = gui.getSignupGui().getUserName().getText();
				String password = new String(gui.getSignupGui().getPassword().getPassword());
				String confirmPassword = new String(gui.getSignupGui().getCOnfirmPassword().getPassword());
				String realname = gui.getSignupGui().getFirstName().getText().concat(gui.getSignupGui().getLastName().getText());
				String priPhone = gui.getSignupGui().getPriPhone().getText();
				String secPhone = gui.getSignupGui().getSecPhone().getText();
				String email = gui.getSignupGui().getEmail().getText();
				String birthDate = "";
				String state = (String) gui.getSignupGui().getState().getSelectedItem();
				String address = gui.getSignupGui().getStreet().getText().concat(gui.getSignupGui().getCity().getText()).
				                      concat(state).concat(gui.getSignupGui().getZip().getText());
				
				Icon newIcon = new Icon();
				AccountInfo newAccount = new AccountInfo(username,password);
				LocationInfo newLocation = new LocationInfo(address);
				ContactInfo newContact = new ContactInfo(realname,newLocation,priPhone,secPhone,email,birthDate,newIcon);
				Menu newMenu = new Menu();
				Album newAlbum = new Album();
				restaurant = new Restaurant(newAccount,newContact,newMenu,newAlbum);
				
				boolean result;
				
				if(password.equals(confirmPassword))
				{
					result = model.signupForNewUser(restaurant);
					if (result==true)
						gui.getOrderGui().displayOrderScreen();
					else
					{
						JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Username already exists.Choose a different username.</FONT></HTML>");	
						JOptionPane.showMessageDialog(null,errorFields);
					}	
				}
				else
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Password and Confirm Password fields doesn't match.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
				}
			}
		}
	}
	
	
	
	/**
	 * SelectionListener class implements ListSelection Listener. Gives 
	 * implementation for the valueChanged method.
	 * 
	 * @author parkavi
	 *
	 */
	public class SelectionListener implements ListSelectionListener{
		
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		
		public void valueChanged(ListSelectionEvent e){
			
			gui.getOrderGui().getSubPanel10().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
                       
            
            //for(int i=0;i<currOrders.size();i++){
            	//if(currOrders.get(i).getOrderID().equals(data[minIndex][0])){
            		//System.out.println("match found");
            		//gui.getSubPanel10().setVisible(true);
            		Order selectedOrder = currOrders.get(minIndex);
            		gui.getOrderGui().getOrderNo().setText(selectedOrder.getOrderID());
            		gui.getOrderGui().getCustNo().setText(selectedOrder.getCustID());
            		String[] orderDetails = new String[50];
            		for(int j=0;j<selectedOrder.getOrderItems().size();j++){
            			orderDetails[j] = selectedOrder.getOrderItems().get(j).getItem().getItemName() + "    " + selectedOrder.getOrderItems().get(j).getQuantity();
            		}
            		gui.getOrderGui().setSelectedOrderDetails(orderDetails);
            	//}
            //}
		}
		
	}
	
	
	
	/**
	 * EditButtonListener class implements the actionPerformed method checking for 
	 * the user credentials and allowing the user to log into the application.
	 * 
	 * @author parkavi
	 *
	 */
	
	
	class EditButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) 
		{
			int ind = listOfEdit.indexOf(event.getSource());
			
			JTextField name = gui.getOrderGui().getItemNames(ind);
		    JTextField description = gui.getOrderGui().getDescription(ind);
		    JTextField price = gui.getOrderGui().getPrice(ind);
		    
		    if(name.getText().equals("") || description.getText().equals("") || price.getText().equals("")){
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Please enter a value in each field before saving.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
			}
		    else{
		    	
				if(listOfEdit.get(ind).getText()=="Save")
				{		
				     name.setEditable(false);
				     description.setEditable(false);
				     price.setEditable(false);
				     listOfEdit.get(ind).setText("Edit");
				     
				     //save the newly entered item and its price
				     if(indx*5+ind < listOfItemNames.size()){
					     listOfItemNames.set(indx*5+ind,name.getText());
					     listOfDescription.set(indx*5+ind,description.getText());
					     listOfPrice.set(indx*5+ind,price.getText());
					     
					     //update the menu 
					     Item updateItem = restaurant.getMenu().getItems().get(restaurant.getMenu().getMenuSize());
					     updateItem.setItemName(name.getText());
					     updateItem.setItemDescription(description.getText());
					     updateItem.setItemPrice(Double.parseDouble(price.getText()));
					     
					     //write Menu to DB
					     while (!model.updateMenu(restaurant.getAccountInfo(),restaurant.getMenu()))
					     {
					    	 
					     }
					     
					     
				     }
				     else{
				    	 listOfItemNames.add(name.getText());
					     listOfDescription.add(description.getText());
					     listOfPrice.add(price.getText());
					     
					     //add item to menu
					     Item lastItem = restaurant.getMenu().getItems().get(restaurant.getMenu().getMenuSize());
					     String newItemId = Integer.toString(Integer.parseInt(lastItem.getItemId()) + 1);
					     Item newItem = new Item(newItemId,name.getText(),description.getText(),Double.parseDouble(price.getText()),new Rating(0,0),new Album());
					     restaurant.getMenu().addItem(newItem);
					     
					     //write Menu to DB
					     while (!model.updateMenu(restaurant.getAccountInfo(),restaurant.getMenu()))
					     {
					    	 
					     }
				     }
				     		
				}
				
				else if(listOfEdit.get(ind).getText()=="Edit")
				{
					name.setEditable(true);
			        description.setEditable(true);
			        price.setEditable(true);
			        listOfEdit.get(ind).setText("Save");
			        
			        /*//replace the newly entered item and its price
			        listOfItemNames.set(indx*5+ind,name.getText());
			        listOfDescription.set(indx*5+ind,description.getText());
			        listOfPrice.set(indx*5+ind,price.getText());*/
					
				}
		    }
		}
	}
	
	
	class DeleteButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			int ind = listOfDelete.indexOf(event.getSource());
			
			if(indx*5+ind<listOfItemNames.size())
			{
				listOfItemNames.remove(indx*5+ind);
				listOfDescription.remove(indx*5+ind);
				listOfPrice.remove(indx*5+ind);
				onClickNext();
				
				//remove item from menu
			    restaurant.getMenu().removeItem(indx*5+ind);
				
				//write Menu to DB
				while (!model.updateMenu(restaurant.getAccountInfo(),restaurant.getMenu()))
			     {
			    	 
			     }
			}
		}
	}
	
	
	/**
	 * NextButtonListener class implements the actionPerformed method and 
	 * populates the textfields to show the next set of menu items.
	 * 
	 * @author parkavi
	 *
	 */
	
	class NextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
				
			boolean nextProceed=true;
			for(int i=0;i<5;i++){
				if (listOfEdit.get(i).getText().equals("Save")){
					nextProceed = false;
					break;
				}
			}
			
			if(nextProceed){
				indx = indx + 1;
				System.out.println("into next index = " + indx);
				System.out.println("into next size = " + listOfItemNames.size());
				onClickNext();
				gui.getOrderGui().getPrev().setVisible(true);			
			}
			else{
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Please fill all the values before going to next page.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
			}			
		}
	}
	
	
	public void onClickNext(){
		
		for(int i=0;i<5;i++)
		{
			if(indx*5+i<listOfItemNames.size()){
				gui.getOrderGui().getItemNames(i).setText(listOfItemNames.get(indx*5+i));
				gui.getOrderGui().getDescription(i).setText(listOfDescription.get(indx*5+i));
				gui.getOrderGui().getPrice(i).setText(listOfPrice.get(indx*5+i));
				gui.getOrderGui().getItemNames(i).setEditable(false);
				gui.getOrderGui().getDescription(i).setEditable(false);
				gui.getOrderGui().getPrice(i).setEditable(false);
				listOfEdit.get(i).setText("Edit");
				listOfDelete.get(i).setText("Delete");	
			}
			else{
				gui.getOrderGui().getItemNames(i).setText("");
				gui.getOrderGui().getDescription(i).setText("");
				gui.getOrderGui().getPrice(i).setText("");
				gui.getOrderGui().getItemNames(i).setEditable(true);
				gui.getOrderGui().getDescription(i).setEditable(true);
				gui.getOrderGui().getPrice(i).setEditable(true);
				listOfEdit.get(i).setText("Save");
				listOfDelete.get(i).setText("Delete");	
			}
		}
	}
	
	
	/**
	 * PrevButtonListener class implements the actionPerformed method and 
	 * populates the textfields to show the previous set of menu items.
	 * 
	 * @author parkavi
	 *
	 */
	
	class PrevButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			indx = indx - 1;
			System.out.println("into prev index = " + indx);
			System.out.println("into prev size = " + listOfItemNames.size());
			for (int i=0;i<5;i++){
				listOfEdit.get(i).setText("Edit");
				gui.getOrderGui().getItemNames(i).setText(listOfItemNames.get(indx*5+i));
				gui.getOrderGui().getDescription(i).setText(listOfDescription.get(indx*5+i));
				gui.getOrderGui().getPrice(i).setText(listOfPrice.get(indx*5+i));
				gui.getOrderGui().getItemNames(i).setEditable(false);
				gui.getOrderGui().getDescription(i).setEditable(false);
				gui.getOrderGui().getPrice(i).setEditable(false);
			}
			if (indx == 0){
				System.out.println("exiting prev");
				gui.getOrderGui().getPrev().setVisible(false);
			}
			
		}
	}
	
	
	/*
	 * This method is called to update the list of pending orders.
	 */
	
	public void setCurrentOrders(){
		gui.getOrderGui().setCurrentOrders(data,pointer);
		for(int i=0;i<pointer;i++)
			for(int j=0;j<5;j++)
				this.data[i][j]="";
	}

	
}
