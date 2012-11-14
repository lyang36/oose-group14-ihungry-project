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
	public String getResponseFromServerT();
	

	/**
	 * Check whether the customer's username and password are valid by sending these two values to the server.
	 * @param username
	 * @param password
	 * @return a boolean whether the username and password are valid.
	 */
	public boolean loginCheck( String username, String password);
	
	
	/**
	 * Submit the information of the customer to the client.
	 * @param customer
	 * @return a boolean value whether the signup is successful on the server side.
	 */
	public boolean signupForNewUser( Customer customer );
	
	
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
	public ContactInfo getRestaurantInfo( String restId );
	
	/**
	 * Retrieve a list of restaurants based on the customer's GPS location.
	 * @param loc LocationInfo
	 * @return a list of restaurant
	 */
	public List<Restaurant> retrieveRestaurants(LocationInfo loc);
	
	
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
	public Order createOrder(String orderId, String custId, String restId, int status, List<OrderItem> orderitems); 
	
	
	/**
	 * Submit the order to the server for processing.
	 * @param order created Order
	 */
	public void submitOrder(Order order);
	
	
	/**
	 * Retrieve all the orders this specific customer made previously
	 * @param custId Customer's ID
	 * @return
	 */
	public List<Order> retrieveAllOrders(String custId);
	
	
	/**
	 * Retrieve orders from the server based on the info of the customer, 
	 * the status of requested orders and the number of orders
	 * @param custId Customer's ID
	 * @param status status of the order (processes/confirmed/etc.)
	 * @param count # of orders customer wants to see
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public List<Order> retrieveOrders(String custId, String status, int count);
	
	
	/**
	 * This is called frequently by the client requesting status changed orders
	 * @param custId Customer's ID
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public List<Order> retrieveChangedOrders(String custId);
	
//	public void processOrder(Order order, String status);
	
}
