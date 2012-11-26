package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import edu.jhu.cs.oose.project.group14.ihungry.model.*;

import java.util.*;

/**
 * Define the android client side model <b>interfaces</b>.
 * @author Group 14
 *
 */

public interface AndroidClientModel {
	
	/**
	 * Get response from server in the form of String. This method is used mainly for testing connection with server (send & response)
	 * @return a response from the server
	 */
//	public String getResponseFromServerT();
	

	/**
	 * Check whether the customer's username and password are valid by sending these two values to the server.
	 * @param username
	 * @param password
	 * @return a boolean whether the username and password are valid.
	 */
	public boolean loginCheck( String username, String password);
	

	/**
	 * Submit the information of the customer to the client.
	 * @param username
	 * @param password
	 * @param realname
	 * @param address
	 * @param primphone
	 * @param secphone
	 * @param email
	 * @param birthday
	 * @param icon
	 * @return a boolean value: if success, return true; else return false.
	 */
	public boolean signupForNewUser(String username, String password,
			String realname, String address, String primphone, String secphone,
			String email, String birthday, Icon icon);	
	
	/**
	 * Return the Customer object info from the server.
	 * @param username
	 * @param password
	 * @return Customer object
	 */
	public Customer getCustomerInfo( String username, String password);
	
	
	/**
	 * Get the Restaurant object info from the server.
	 * @param restId Restaurant ID
	 * @return the restaurant's ContactInfo
	 */
	
	/**
	 * Get the list of Restaurant info
	 * @return
	 */
	public List<AccountInfo> getRestaurantAccountInfos(LocationInfo loc);
	
	
	public List<ContactInfo> getRestaurantContactInfos(List<AccountInfo> bus_accInfos);
	
	
	/**
	 * Get the Contact Info of a specific 
	 * @param bus_accInfo
	 * @return
	 */
	public ContactInfo getRestaurantContactInfoSingle(AccountInfo bus_accInfo);

	
	/**
	 * Retrieve a list of restaurants based on the customer's GPS location.
	 * @param loc LocationInfo
	 * @return a list of restaurant
	 */
	//public List<ContactInfo> retrieveRestaurants(LocationInfo loc);
	
	
	/**
	 * Return a Menu of a specified restaurant.
	 * @param restId Restaurant id
	 * @return a Menu of that Restaurant
	 */
	public Menu retrieveMenu(String restId);
	 
	
	/**
	 * Create an order given the info of the customer, restaurant and order-items.
	 * @param orderId Order id
	 * @param custId Customer id
	 * @param restId Restaurant id
	 * @param status status of order
	 * @param orderitems a list of orderitems
	 * @return
	 */
	public Order createOrder(String restId, int status, List<OrderItem> orderitems); 
	
	
	/**
	 * Submit the order to the server for processing.
	 * @param order created Order
	 * @return a boolean value indicating whether the order is successfully submitted
	 */
	public boolean submitOrder(Order order);
	
	
	/**
	 * Retrieve all the orders this specific customer made previously
	 * @param custId Customer's ID
	 * @return
	 */
	public List<Order> retrieveAllOrders();
	
	
	/**
	 * Retrieve orders from the server based on the info of the customer, 
	 * the status of requested orders and the number of orders
	 * @param status status of the order (processes/confirmed/etc.)
	 * @param count # of orders customer wants to see
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public List<Order> retrieveOrders(String status, int count);
	
	
	/**
	 * This is called frequently by the client requesting status changed orders
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public List<Order> retrieveChangedOrders();
	
//	public void processOrder(Order order, String status);
	
}
