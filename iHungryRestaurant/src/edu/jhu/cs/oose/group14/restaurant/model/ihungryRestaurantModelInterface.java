package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.List;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public interface ihungryRestaurantModelInterface {
	
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
	public String attemptLogin( String username, String password);
	
	
	/**
	 * Called whenever a change has been made to the menu. This method
	 * updates the menu in the database.
	 *  
	 * @param accinfo
	 * @param menu
	 * @return PROCESS_SUCCEEDED/PROCESS_FAILED
	 */
	public boolean updateMenu(AccountInfo accinfo, Menu menu);
	
	
	/**
	 * Called whenever a change has been made to the contact details. This method
	 * updates the contact in the database.
	 *  
	 * @param accinfo
	 * @param ci
	 * @return PROCESS_SUCCEEDED/PROCESS_FAILED
	 */
	public boolean updateContact(AccountInfo accinfo, ContactInfo ci);
	
	
	/**
	 * Called by the business to retrieve the address of customer.
	 *  
	 * @param AccountInfo accInfo 
	 * @param Order ord
	 * @return PROCESS_SUCCEEDED/PROCESS_FAILED
	 */
	public String getCustContact(AccountInfo accInfo, Order ord);
	
	
	/**
	 * Called whenever a change has been made to an order. This method
	 * updates the order in the database.
	 *  
	 * @param accinfo
	 * @param menu
	 * @return PROCESS_SUCCEEDED/PROCESS_FAILED
	 */
	public boolean updateOrder(AccountInfo accinfo, Order order);
	
	
	/**
	 * Send the information of the restaurant to the server.
	 * @param restaurant
	 * @return a boolean value whether the signup is successful on the server side.
	 */
	public boolean signupForNewUser( Restaurant restaurant );
	
	
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
