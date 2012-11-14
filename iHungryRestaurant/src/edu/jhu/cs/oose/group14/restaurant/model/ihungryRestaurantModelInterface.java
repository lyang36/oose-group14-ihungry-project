package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.List;

import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public interface ihungryRestaurantModelInterface {
	
	
	
	/**
	 * Get response from server in the form of String. This method is used mainly for testing connection with server (send & response)
	 * @return a response from the server
	 */
	public String getResponseFromServer();
	
	/**
	 * Check whether the restaurant's username and password are valid by sending these two values to the server.
	 * @param username
	 * @param password
	 * @return true if (username,password) combination is fine else false
	 */
	public boolean loginCheck( String username, String password);
	
	/**
	 * Sends the (username,Password) pair to server to check if it is a valid pair 
	 * @param username
	 * @param password
	 * @return LOGIN_SUCCESS/LOGIN_ERROR
	 */
	public boolean attemptLogin( String username, String password);
	
	
	/**
	 * Send the information of the restaurant to the server.
	 * @param restaurant
	 * @return a boolean value whether the signup is successful on the server side.
	 */
	public boolean signupForNewUser( Restaurant restaurant );
	
	
	/**
	 * Get the restaurant object info from the server.
	 * @param username
	 * @param password
	 * @return restaurant object
	 */
	public Restaurant getRestaurantInfo( String username, String password);
	
	
	
	/**
	 * Retrieves 'count' number of orders with the specified status
	 * @param restId Restaurant id
	 * @param status 
	 * @param count
	 * @return returns a list of Orders
	 */
	public List<Order> retreiveOrders(String restId, int status, int count); 
	
	
	/**
	 * This is called frequently by the client requesting status changed orders
	 * @param restId Restaurant ID
	 * @return an arraylist of Orders satisfying the requirements
	 */
	public List<Order> retrieveChangedOrders(String restId);
	
	
	

}
