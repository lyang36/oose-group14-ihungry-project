package edu.jhu.cs.oose.group14.restaurant.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;

/**
 * ihungryRestaurantModel class is the model class for ihungry vendor 
 * application. It uses other helper classes like LoginHandler to handle
 * specific functions.
 */

public class ihungryRestaurantController {
	
	private ihungryRestaurantGui gui;
	private ihungryRestaurantModelInterface model;
	private List<JButton> listOfEdit;
    private List<JButton> listOfDelete;
    private List<String> listOfItemNames, listOfDescription, listOfPrice ;
	private int indx=0;
	private Restaurant restaurant = new Restaurant(new Menu(),new Album());
	private String username,realname,password,confirmPassword = null;
	private String priPhone,secPhone,email,birthDate,state,address,coord1,coord2 = null;
	private iHungryRestaurant hungryRestaurant;
	
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
	
	/*
	 * Converts the address in String to location coordinates using the Google Maps API
	 */
	
	public String getCoordinates(String address){
		try
        {
            String thisLine;
            String address1 = address;
            address1 = address1.replace(' ', '+');
            address1 = address1.replace(',', '+');
            System.out.println("address:       "+address1);
            URL u = new URL("http://maps.google.com/maps/geo?q=\'" + address1 + "\'&output=xml&key=AIzaSyDA6y535k8_IQu-3XLpVn865SiEkBLSQ74");
 
            BufferedReader theHTML = new BufferedReader(new InputStreamReader(u.openStream()));
 
            FileWriter fstream = new FileWriter("url.xml");
            BufferedWriter out = new BufferedWriter(fstream);
            while ((thisLine = theHTML.readLine()) != null)
                out.write(thisLine);
            out.close();
            File file = new File("url.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("code");
            Element n = (Element)nl.item(0);
            String st = n.getFirstChild().getNodeValue();
 
            if (st.equals("200"))
            {
                NodeList n2 = doc.getElementsByTagName("coordinates");
                Element nn = (Element)n2.item(0);
                String st1 = nn.getFirstChild().getNodeValue();
 
                return st1;
            }
            else
            {
            	return null;
            }
        }
		catch (Exception e) {
			System.out.println("Exception Occured");
            return null;
        }
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
		
		/*
		 * Passes the username and password entered to the server. If the server
		 * accepts this combination then user is logged into the OrderGui screen.
		 */
		public void composeForLogIn(){
			
			String username = gui.getLoginGui().getUsernameLogin().getText();
			String password = new String(gui.getLoginGui().getPasswordLogin().getPassword());
			
			//check for errors in login
			if (model.loginCheck(username,password))
			{
				String result = model.attemptLogin(username,password);
				if (!result.equals(""))
				{	
					hungryRestaurant = iHungryRestaurant.getInstance();
					hungryRestaurant.setAccountInfo(new AccountInfo(username, password));
					
					//start the thread
					Timer timer = new Timer("RetrieveOrdersTask", true);					
				    timer.scheduleAtFixedRate(new RetrieveOrdersTask(), 5000, 5000);

					JSONObject jsonobj = null;
					try {
						jsonobj = new JSONObject(result);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					restaurant.parseFromJSONObject(jsonobj);
					populateMenuFields();
										
					gui.getOrderGui().displayOrderScreen();
					gui.getOrderGui().getTabbedPane().setSelectedIndex(1);
					
					//Build the menu table
					onClickNext(); 
					
					// Get the orders and store it in iHungryRestaurant class					
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
					
					//Build the UpdateDetails screen
					gui.getOrderGui().getEmailOrderT().setText(restaurant.getContactInfo().getEmail());
					String address = restaurant.getContactInfo().getAddress().getAddress();
					System.out.println(address);
					String street = address.split(",")[0];
					String city = address.split(",")[1];
					String state = address.split(",")[2].trim();
					String zip ="";
					if (state.split(" ").length==2)
					{
						zip = state.split(" ")[1];
					}
					state = state.split(" ")[0];
					gui.getOrderGui().getStreetOrderT().setText(street);
					gui.getOrderGui().getCityOrderT().setText(city);
					gui.getOrderGui().getZipOrderT().setText(zip);
					Object stateObj = state;
					
					//Highlight the state
					int j=0;
					for(Object o:gui.getOrderGui().getStatesList())
					{
						if (o.toString().split(" ")[0].trim().equals(stateObj.toString().trim()))
							break;
						j++;
					}
					gui.getOrderGui().getStateOrderT().setSelectedIndex(j);
					gui.getOrderGui().getPriOrderT().setText(restaurant.getContactInfo().getPrimPhone());
					gui.getOrderGui().getSecOrderT().setText(restaurant.getContactInfo().getSecPhone());
					
					for(int i=0;i<5;i++)
					{
						listOfEdit.get(i).addActionListener(new EditButtonListener() );
						listOfDelete.get(i).addActionListener(new DeleteButtonListener() );
					}
					gui.getOrderGui().getNext().addActionListener(new NextButtonListener());
					gui.getOrderGui().getPrev().addActionListener(new PrevButtonListener());
					gui.getOrderGui().getAcceptButton().addActionListener(new AcceptButtonListener());
					gui.getOrderGui().getDeclineButton().addActionListener(new DeclineButtonListener());
					gui.getOrderGui().getDeliverButton().addActionListener(new DeliverButtonListener());
					gui.getOrderGui().getTable().getSelectionModel().addListSelectionListener(new SelectionListener());
					gui.getOrderGui().getToBeDeliveredTable().getSelectionModel().addListSelectionListener(new ToBeDeliveredSelectionListener());
					gui.getOrderGui().getDeclinedOrderTable().getSelectionModel().addListSelectionListener(new DeclinedOrderSelectionListener());
					gui.getOrderGui().getOrderHistoryTable().getSelectionModel().addListSelectionListener(new OrderHistorySelectionListener());
					gui.getOrderGui().getUpdate().addActionListener(new UpdateButtonListener());
					gui.getOrderGui().getLogout().addActionListener(new LogoutButtonListener());
					
					
				}
				else
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Password is incorrect.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
					gui.getLoginGui().getPasswordLogin().setText("");
					gui.getLoginGui().getPasswordLogin().requestFocus();
				}
			}
			else
			{
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Username does not exists.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
				gui.getLoginGui().getUsernameLogin().setText("");
				gui.getLoginGui().getUsernameLogin().requestFocus();
			}
		}
		
		/*
		 * Populates the fields in the Update Menu page. Called whenever next
		 * and previous buttons are clicked.
		 */
		
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
			gui.getSignupGui().getFirstName().requestFocus();
			gui.getSignupGui().getNextSignUp().addActionListener(new NextSignUpListener());
			
		}
		
		/**
		 * Inner class that displays the first screen of Sign up process
		 */
		
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
							gui.getSignupGui().getEmail().requestFocus();
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
					gui.getSignupGui().getUserName().requestFocus();	
				}
			}
		}
		
		
		/**
		 * Inner class that displays the second screen of Sign Up process
		 * @author parkavi
		 *
		 */
		
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
				System.out.println(state);
				address = gui.getSignupGui().getStreet().getText().concat(",").concat(gui.getSignupGui().getCity().getText()).concat(",").
				                      concat(state.split(" ")[0]).concat(" ").concat(gui.getSignupGui().getZip().getText());
				String coords = getCoordinates(address);
				if (coords!=null)
				{
					coord2=coords.split(",")[0];
					coord2=coord2.replace(".","");
					coord1=coords.split(",")[1];
					coord1=coord1.replace(".","");
				}
				
				Icon newIcon = new Icon();
				AccountInfo newAccount = new AccountInfo(username,password);
				LocationInfo newLocation = new LocationInfo(address,Long.parseLong(coord1),Long.parseLong(coord2));
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
					gui.getLoginGui().displayLoginScreen();
					gui.getLoginGui().getUsernameLogin().requestFocus();
					gui.getLoginGui().getLogin().addActionListener(new LoginListener());
					gui.getLoginGui().getSignUp().addActionListener(new SignUpListener());
				}
			}
			
		}
	}
	

	/**
	 * AcceptButtonListener class implements the actionPerformed method. Moves the
	 * accepted order from pending orders to to be delivered orders.
	 * 
	 * @author parkavi
	 *
	 */	
	
	public class AcceptButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			Order order = new Order(gui.getOrderGui().getOrderNoT().getText());
			//clear the order details pane
			gui.getOrderGui().getOrderNoT().setText("");
			gui.getOrderGui().getCustNo().setText("");
			String[] emptyDetails = new String[2];
			gui.getOrderGui().getList1().setListData(emptyDetails);
			
			order = hungryRestaurant.getPendingOrder(order);
			order.setStatus(Order.STATUS_CONFIRMED);
			if(model.updateOrder(restaurant.getAccountInfo(), order)){
				int index = gui.getOrderGui().getTable().getSelectedRow();
				if(index>=1){
					gui.getOrderGui().getTable().setRowSelectionInterval(index-1, index-1);
					gui.getOrderGui().getTable().addRowSelectionInterval(index-1, index-1);
				}
				if (gui.getOrderGui().getToBeDeliveredTable().getRowCount()>0){
					index = gui.getOrderGui().getTable().getSelectedRow();
				}
				hungryRestaurant.removePendingOrder(order);
				hungryRestaurant.addOrModifyAcceptedOrder(order);
			}
			
		}
	}
	
	
	/**
	 * DeleteButtonListener class implements the actionPerformed method. Moves the
	 * accepted order from pending orders to declined orders.
	 * 
	 * @author parkavi
	 *
	 */	
	
	public class DeclineButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			Order order = new Order(gui.getOrderGui().getOrderNoT().getText());
			gui.getOrderGui().getOrderNoT().setText("");
			gui.getOrderGui().getCustNo().setText("");
			String[] emptyDetails = new String[2];
			gui.getOrderGui().getList1().setListData(emptyDetails);
			
			order = hungryRestaurant.getPendingOrder(order);
			order.setStatus(Order.STATUS_REJECTED);
			if(model.updateOrder(restaurant.getAccountInfo(), order)){
				int index = gui.getOrderGui().getTable().getSelectedRow();
				if(index>=1){
					gui.getOrderGui().getTable().setRowSelectionInterval(index-1, index-1);
				}
				hungryRestaurant.removePendingOrder(order);
				hungryRestaurant.addOrModifyDeclinedOrder(order);
			}
			
		}
	}
	
	
	/**
	 * DeliverButtonListener class implements the actionPerformed method. Moves the
	 * accepted order from to-be-delivered orders to old orders.
	 * 
	 * @author parkavi
	 *
	 */	
	
	public class DeliverButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			Order order = new Order(gui.getOrderGui().getToBeDeliveredOrderNoT().getText());
			//clear the order details section
			gui.getOrderGui().getToBeDeliveredOrderNo().setText("");
			gui.getOrderGui().getToBeDeliveredCustNo().setText("");
			String[] emptyDetails = new String[2];
			gui.getOrderGui().getToBeDeliveredList().setListData(emptyDetails);
			
			order = hungryRestaurant.getAcceptedOrder(order);
			order.setStatus(Order.STATUS_FINISHED);
			if(model.updateOrder(restaurant.getAccountInfo(), order)){
				int index = gui.getOrderGui().getToBeDeliveredTable().getSelectedRow();
				if(index>=1){
					gui.getOrderGui().getToBeDeliveredTable().setRowSelectionInterval(index-1, index-1);
				}
				hungryRestaurant.removeAcceptedOrder(order);
				hungryRestaurant.addOrModifyOrderHistory(order);
			}
			
		}
	}
	
	
	/**
	 * UpdateButtonListener class implements the actionPerformed method. Updates 
	 * the restaurant's details that the user has entered.
	 * 
	 * @author parkavi
	 *
	 */	
	
	public class UpdateButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if (gui.getOrderGui().getEmailOrderT().getText()!=restaurant.getContactInfo().getEmail() ||
			gui.getOrderGui().getStreetOrderT().getText()!=restaurant.getContactInfo().getAddress().getAddress() ||
			gui.getOrderGui().getPriOrderT().getText()!=restaurant.getContactInfo().getPrimPhone() ||
			gui.getOrderGui().getSecOrderT().getText()!=restaurant.getContactInfo().getSecPhone())
			{
				//create a ContactInfo object and update it with the values user entered
				ContactInfo ci = restaurant.getContactInfo();
				ci.setPrimPhone(gui.getOrderGui().getPriOrderT().getText());
				ci.setSecPhone(gui.getOrderGui().getSecOrderT().getText()); 
				String addr = gui.getOrderGui().getStreetOrderT().getText().concat(",").concat(gui.getOrderGui().getCityOrderT().getText()).concat(",").
                      concat((String) gui.getOrderGui().getStateOrderT().getSelectedItem().toString().split(" ")[0]).concat(" ").concat(gui.getOrderGui().getZipOrderT().getText());
				String coords = getCoordinates(addr);
				String coor1 ="";
				String coor2 ="";
				if (coords!=null)
				{
					coor2=coords.split(",")[0];
					coor2=coor2.replace(".","");
					coor1=coords.split(",")[1];
					coor1=coor1.replace(".","");
				}
				try{
				ci.setAddress(new LocationInfo(addr,Long.parseLong(coor1),Long.parseLong(coor2)));}
				catch(NumberFormatException nfe){
					System.out.println("Number Format Exception occured");
				}
				catch(Exception e1){
					System.out.println("Exception occured");
				}
				ci.setEmail(gui.getOrderGui().getEmailOrderT().getText());
				
				//update the restaurant details
				if(model.updateContact(restaurant.getAccountInfo(), ci))
				{
					JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Successfully Updated.</FONT></HTML>");	
					JOptionPane.showMessageDialog(null,errorFields);
				}
						
			}
			
				
		}
	}
	
	/**
	 * LogoutButtonListener class implements the actionPerformed method. After user
	 * logs out, the initial login screen is again displayed.
	 * @author parkavi
	 *
	 */
	
	
	public class LogoutButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			gui.getLoginGui().displayLoginScreen();
			gui.getLoginGui().getUsernameLogin().requestFocus();
			gui.getLoginGui().getLogin().addActionListener(new LoginListener());
			gui.getLoginGui().getSignUp().addActionListener(new SignUpListener());
			
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
			
			if(minIndex>=0)
			{
				List<Order> pendingOrders = new ArrayList<Order>(hungryRestaurant.getPendingOrders());
				Order selectedOrder = pendingOrders.get(minIndex);			
				gui.getOrderGui().getOrderNoT().setText(selectedOrder.getOrderID());
				gui.getOrderGui().getCustNo().setText(selectedOrder.getCustID());
				String[] orderDetails = new String[50];
				for (int j = 0; j < selectedOrder.getOrderItems().size(); j++) {
					orderDetails[j] = selectedOrder.getOrderItems().get(j)
							.getItem().getItemName()
							+ "    "
							+ selectedOrder.getOrderItems().get(j).getQuantity();
				}
				gui.getOrderGui().getList1().setListData(orderDetails);
			}
		}
	}
	
	
	/*
	 *Splits the address from ContactInfo object which is in String format. 
	 */
	
	
	public String getCustAddress(String supinfo){
		
		ContactInfo ci = new ContactInfo(new LocationInfo(""),"");
		//parse from the supinfo to address and phone number
		JSONObject supinfoObj = new JSONObject();
		try {
			if(supinfo!=null && supinfo.length() > 0)
				supinfoObj = new JSONObject(supinfo);
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		ci.parseFromJSONObject(supinfoObj);
		return ci.getAddress().getAddress();
	}
	
	
	public class ToBeDeliveredSelectionListener implements ListSelectionListener{
		
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		
		public void valueChanged(ListSelectionEvent e) {
			
			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getToBeDeliveredSubPanel().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();			
			
			if (minIndex>=0)
			{
				List<Order> acceptedOrders = new ArrayList<Order>(hungryRestaurant.getAcceptedOrders());
				Order selectedOrder = acceptedOrders.get(minIndex);
				gui.getOrderGui().getToBeDeliveredOrderNo().setText(selectedOrder.getOrderID());
				gui.getOrderGui().getToBeDeliveredCustNo().setText(selectedOrder.getCustID());
				
				//get the customer address 
				String supinfo = model.getCustContact(restaurant.getAccountInfo(), selectedOrder);
				String custAddress = getCustAddress(supinfo);
				gui.getOrderGui().getToBeDeliveredCustAddrT().setText(custAddress);
				
				String[] orderDetails = new String[50];
				for (int j = 0; j < selectedOrder.getOrderItems().size(); j++) {
					orderDetails[j] = selectedOrder.getOrderItems().get(j)
							.getItem().getItemName()
							+ "    "
							+ selectedOrder.getOrderItems().get(j).getQuantity();
				}
				gui.getOrderGui().getToBeDeliveredList().setListData(orderDetails);
			}
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
		private boolean loop = true;
		
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 */
		
		public void valueChanged(ListSelectionEvent e) {
			
			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getDeclinedOrderSubPanel().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();
			
			if(minIndex>=1)
				loop = true;
			
			if (minIndex>=0)
			{	
				if(loop)
				{
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
				if(minIndex==0)
					loop = false;
			}
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
		
		private boolean loop = true;
		/*
		 * This method will populate the JList with the details of the currently selected order.
		 */
		
		public void valueChanged(ListSelectionEvent e) {
		
			iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
			gui.getOrderGui().getOrderHistorySubPanel().setVisible(true);
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int minIndex = lsm.getMinSelectionIndex();		
			
			if(minIndex>=1)
				loop = true;
			
			if (minIndex>=0)
			{
				if (loop)
				{
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
				if(minIndex==0)
					loop = false;
			}
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
					    	 newItemId = "i001";
					     else
					     {
					    	 Item lastItem = restaurant.getMenu().getItems().get(restaurant.getMenu().getMenuSize()-1);
					    	 String lastItemId = lastItem.getItemId().substring(1);
					    	 newItemId = "i" + Integer.toString(Integer.parseInt(lastItemId) + 1);
					     }
					     Item newItem = new Item(newItemId,name.getText(),description.getText(),Double.parseDouble(price.getText()),new Rating(0,0),new Album());
					     restaurant.getMenu().addItem(newItem);
					     
					     //write Menu to DB
					     boolean result = model.updateMenu(restaurant.getAccountInfo(),restaurant.getMenu());
					     
				     }
				}
				else if(listOfEdit.get(ind).getText()=="Edit")
				{
					name.setEditable(true);
			        description.setEditable(true);
			        price.setEditable(true);
			        listOfEdit.get(ind).setText("Save");			
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
