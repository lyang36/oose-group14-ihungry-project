package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import java.util.ArrayList;

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
	 * Retrieve a list of restaurants based on the customer's GPS location.
	 * @param loc LocationInfo
	 * @return an arraylist of restaurant
	 */
	public ArrayList<Restaurant> retrieveRestaurants(LocationInfo loc);
	
	/**
	 * Return a Menu of a specified restaurant.
	 * @param rest a Restaurant object
	 * @return a Menu of that Restaurant
	 */
	public Menu retrieveMenu(Restaurant rest);
	 
	/**
	 * Create an order given the info of the customer, restaurant and order-items.
	 * @param cust Customer info
	 * @param rest Restaurant info
	 * @param order an arraylist of order-items
	 */
	public void createOrder(Customer cust, Restaurant rest, ArrayList<OrderItem> order); 
	
	/**
	 * Submit the order to the server for processing.
	 * @param order created Order
	 */
	public void submitOrder(Order order);
	
	/**
	 * Retrieve orders from the server based on the info of the customer, 
	 * the status of requested orders and the number of orders
	 * @param cust Customer info
	 * @param status status of the order (processes/confirmed/etc.)
	 * @param count # of orders customer wants to see
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public ArrayList<Order> retrieveOrders(Customer cust, String status, int count);
	
	/**
	 * This is called frequently by the client requesting status changed orders
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public ArrayList<Order> retrieveChangedOrders();
	
//	public void processOrder(Order order, String status);
	
}
