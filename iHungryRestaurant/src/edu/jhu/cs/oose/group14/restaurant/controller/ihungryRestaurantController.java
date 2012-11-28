package edu.jhu.cs.oose.group14.restaurant.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.group14.restaurant.gui.ihungryRestaurantGui;
import edu.jhu.cs.oose.group14.restaurant.model.iHungryRestaurant;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

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
	private Object[][] data =  new Object[1000][5];
	private int pointer = 0;
	private Restaurant restaurant = new Restaurant(new Menu(),new Album());
	private String username,realname,password,confirmPassword = null;
	private String priPhone,secPhone,email,birthDate,state,address = null;
	
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
		
		// Register the Observer to the Observable
		iHungryRestaurant.getInstance().addObserver(gui.getOrderGui());
	}
	
	/**
	 * LoginListener class implements the actionPerformed method checking for
	 * the user credentials and allowing the user to log into the application.
	 * 
	 * @author parkavi
	 */	
	class LoginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) 
		{
			composeForLogIn();			
		}
		
		public void composeForLogIn(){
			
			String username = gui.getLoginGui().getUsernameLogin().getText();
			String password = new String(gui.getLoginGui().getPasswordLogin().getPassword());
			
			/*gui.getOrderGui().displayOrderScreen();
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
			{
				String result = model.attemptLogin(username,password);
				System.out.println("result="+result);
				if (!result.equals(""))
				{
					System.out.println("successfully logged in");
					
					iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
					hungryRestaurant.setAccountInfo(new AccountInfo(username, password));
										
					// get the orders and store it in iHungryRestaurant class					
					hungryRestaurant.setPendingOrders(model.retreiveOrders(
							hungryRestaurant.getAccountInfo().getId(),
							Order.STATUS_UNDERPROCING, 100));
					
					hungryRestaurant.setAcceptedOrders(model.retreiveOrders(
							hungryRestaurant.getAccountInfo().getId(),
							Order.STATUS_CONFIRMED, 100));
					
					hungryRestaurant.setDeclinedOrders(model.retreiveOrders(
							hungryRestaurant.getAccountInfo().getId(),
							Order.STATUS_REJECTED, 100));

					hungryRestaurant.setOldOrders(model.retreiveOrders(
							hungryRestaurant.getAccountInfo().getId(),
							Order.STATUS_FINISHED, 100));

					JSONObject jsonobj = null;
					try {
						jsonobj = new JSONObject(result);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					restaurant.parseFromJSONObject(jsonobj);
					populateMenuFields();
					Runnable r = new OrderRunnable(ihungryRestaurantController.this, restaurant.getAccountInfo());
					Thread t =  new Thread(r);
					t.start();
					
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
					gui.getOrderGui().getDeclinedOrderTable().getSelectionModel().addListSelectionListener(new DeclinedOrderSelectionListener());
					gui.getOrderGui().getOrderHistoryTable().getSelectionModel().addListSelectionListener(new OrderHistorySelectionListener());
					
					//add listeners to accept and decline buttons
				}
				else
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Password is incorrect.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
				}
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
				
				username = gui.getSignupGui().getUserName().getText();
				password = new String(gui.getSignupGui().getPassword().getPassword());
				confirmPassword = new String(gui.getSignupGui().getConfirmPassword().getPassword());
				realname = gui.getSignupGui().getFirstName().getText().concat(gui.getSignupGui().getLastName().getText());
				
				if(!gui.getSignupGui().getUserName().getText().equals("") &&
						!new String(gui.getSignupGui().getPassword().getPassword()).equals("")	&&
						!new String(gui.getSignupGui().getConfirmPassword().getPassword()).equals(""))
					if(password.equals(confirmPassword))
					{
						if(!model.loginCheck(username, password))
						{
							gui.getSignupGui().displaySecondPageSignUpGui();
							gui.getSignupGui().getSignUp2().addActionListener(new SecondSignUpListener());							
						}
						else
						{
							JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Username already exists.Choose a different username.</FONT></HTML>");	
							JOptionPane.showMessageDialog(null,errorFields);
							gui.getSignupGui().getUserName().setText("");
							gui.getSignupGui().getUserName().requestFocus();							
						}
					}
					else
					{
						JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Password and Confirm Password fields doesn't match.</FONT></HTML>");	
						JOptionPane.showMessageDialog(null,errorFields);
						gui.getSignupGui().getPassword().setText("");
						gui.getSignupGui().getConfirmPassword().setText("");
						gui.getSignupGui().getPassword().requestFocus();
					}
				else
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Username and Password fields cannot be empty.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
				}
			}
		}
		
		class SecondSignUpListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				composeForSignUp();
			}
			
			public void composeForSignUp(){
				
				priPhone = gui.getSignupGui().getPriPhone().getText();
				secPhone = gui.getSignupGui().getSecPhone().getText();
				email = gui.getSignupGui().getEmail().getText();
				birthDate = "";
				state = (String) gui.getSignupGui().getState().getSelectedItem();
				address = gui.getSignupGui().getStreet().getText().concat(gui.getSignupGui().getCity().getText()).
				                      concat(state).concat(gui.getSignupGui().getZip().getText());
				
				Icon newIcon = new Icon();
				AccountInfo newAccount = new AccountInfo(username,password);
				LocationInfo newLocation = new LocationInfo(address);
				ContactInfo newContact = new ContactInfo(realname,newLocation,priPhone,secPhone,email,birthDate,newIcon);
				Menu newMenu = new Menu();
				Album newAlbum = new Album();
				restaurant = new Restaurant(newMenu,newAlbum);
				restaurant.setAccountInfo(newAccount);
				restaurant.setContactInfo(newContact);
				
				boolean result;
				
				result = model.signupForNewUser(restaurant);
				if (result)
				{
					gui.getOrderGui().displayOrderScreen();
					//gui.getOrderGui().getAcceptButton().addActionListener()
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
		
		public void valueChanged(ListSelectionEvent e) {

			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getSubPanel10().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();

			List<Order> pendingOrders = new ArrayList<Order>(hungryRestaurant.getPendingOrders());
			Order selectedOrder = pendingOrders.get(minIndex);
			gui.getOrderGui().getOrderNo().setText(selectedOrder.getOrderID());
			gui.getOrderGui().getCustNo().setText(selectedOrder.getCustID());
			String[] orderDetails = new String[50];
			
			for (int j = 0; j < selectedOrder.getOrderItems().size(); j++) {
				orderDetails[j] = selectedOrder.getOrderItems().get(j)
						.getItem().getItemName()
						+ "    "
						+ selectedOrder.getOrderItems().get(j).getQuantity();
			}
			gui.getOrderGui().setSelectedOrderDetails(orderDetails);
		}
		
	}
	
	/**
	 * DeclinedOrderSelectionListener class implements ListSelection Listener. Gives 
	 * implementation for the valueChanged method.
	 * 
	 * @author parkavi
	 *
	 */
	public class DeclinedOrderSelectionListener implements ListSelectionListener{
		
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		
		public void valueChanged(ListSelectionEvent e) {

			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getDeclinedOrderSubPanel().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();			

			List<Order> declinedOrders = new ArrayList<Order>(hungryRestaurant.getDeclinedOrders());
			Order selectedOrder = declinedOrders.get(minIndex);
			gui.getOrderGui().getDeclinedOrderOrderNo().setText(selectedOrder.getOrderID());
			gui.getOrderGui().getDeclinedOrderCustNo().setText(selectedOrder.getCustID());
			String[] orderDetails = new String[50];
			
			for (int j = 0; j < selectedOrder.getOrderItems().size(); j++) {
				orderDetails[j] = selectedOrder.getOrderItems().get(j)
						.getItem().getItemName()
						+ "    "
						+ selectedOrder.getOrderItems().get(j).getQuantity();
			}
			gui.getOrderGui().getDeclinedOrderList().setListData(orderDetails);
		}
		
	}
	
	/**
	 * OrderHistorySelectionListener class implements ListSelection Listener. Gives 
	 * implementation for the valueChanged method.
	 * 
	 * @author parkavi
	 *
	 */
	public class OrderHistorySelectionListener implements ListSelectionListener{
		
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		
		public void valueChanged(ListSelectionEvent e) {

			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getOrderHistorySubPanel().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();			

			List<Order> oldOrders = new ArrayList<Order>(hungryRestaurant.getOldOrders());
			Order selectedOrder = oldOrders.get(minIndex);
			gui.getOrderGui().getOrderHistoryOrderNo().setText(selectedOrder.getOrderID());
			gui.getOrderGui().getOrderHistoryCustNo().setText(selectedOrder.getCustID());
			String[] orderDetails = new String[50];
			
			for (int j = 0; j < selectedOrder.getOrderItems().size(); j++) {
				orderDetails[j] = selectedOrder.getOrderItems().get(j)
						.getItem().getItemName()
						+ "    "
						+ selectedOrder.getOrderItems().get(j).getQuantity();
			}
			gui.getOrderGui().getOrderHistoryList().setListData(orderDetails);
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
					     Item updateItem = restaurant.getMenu().getItems().get(indx*5+ind);
					     updateItem.setItemName(name.getText());
					     updateItem.setItemDescription(description.getText());
					     updateItem.setItemPrice(Double.parseDouble(price.getText()));
					     restaurant.getMenu().setItem(indx*5+ind, updateItem);
					     
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
					     String newItemId = null;
					     if (restaurant.getMenu().getMenuSize()==0)
					    	 newItemId = "1";
					     else
					     {
					    	 Item lastItem = restaurant.getMenu().getItems().get(restaurant.getMenu().getMenuSize()-1);
					    	 newItemId = Integer.toString(Integer.parseInt(lastItem.getItemId()) + 1);
					     }
					     Item newItem = new Item(newItemId,name.getText(),description.getText(),Double.parseDouble(price.getText()),new Rating(0,0),new Album());
					     restaurant.getMenu().addItem(newItem);
					     
					     //write Menu to DB
					     boolean result = model.updateMenu(restaurant.getAccountInfo(),restaurant.getMenu());
					     System.out.println("result = "+result);
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
	
	public ihungryRestaurantModelInterface getModel(){
		return model;
	}		
}
