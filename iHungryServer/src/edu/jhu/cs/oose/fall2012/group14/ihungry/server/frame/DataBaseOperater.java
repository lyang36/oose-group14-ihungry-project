package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;


import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.ListedJSONObj;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

/**
 * The Database operator
 * @author lyang
 *
 */
public interface DataBaseOperater {
	
	/**
	 * Connect to the DB
	 * @return whether or not successful
	 */
	public boolean connectToDB();
	
	/**
	 * close connection
	 * @return
	 */
	public void close();
	
	/**
	 * authenicate
	 * @param myUserName
	 * @param myPassword
	 * @return
	 */
	public boolean authenticate(String myUserName, String myPassword);
	
	/**
	 * check whether the username is existed. 
	 * The AccountInfo ignores passwd
	 */
	public boolean checkUserUnameExisted(AccountInfo acc);
	
	/**
	 * check whether the username is existed. 
	 * The AccountInfo ignores passwd
	 */
	public boolean checkBusiUnameExisted(AccountInfo acc);
	
	/**
	 * get the Customer object
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public Customer getCustomer(AccountInfo acc);
	
	/**
	 * add the cus, if the cus is already existed, then change it
	 * @param cus
	 */
	public void addCustomer(Customer cus);
	
	/**
	 * add the bus, if the bus is already existed, then change it
	 * @param bus
	 */
	public void addBusiness(Restaurant bus);
	
	/**
	 * get the Business
	 */
	public Restaurant getBusiness(AccountInfo accs);
	
	
	/**
	 * Find business in certain region
	 * @return ListedJSONObj made by AccountInfo(ignore passwd)
	 */
	public ListedJSONObj findBusinessById(LocationInfo loc);
	
	/**
	 * get the contact info for the restaurant (ignore passwd)
	 * @param acc
	 * @return
	 */
	public ContactInfo getBusinessContactInfo(AccountInfo acc);
	
	/**
	 * get the album info for the restaurant (ignore passwd)
	 * @param acc
	 * @return
	 */	
	public Album getBusinessAlbum(AccountInfo acc);
	
	
	/**
	 * get the menu info for the restaurant (ignore passwd)
	 * @param acc
	 * @return
	 */	
	public Menu getBusinessMenu(AccountInfo acc);
	
	
	/**
	 * get the contact info for a give customer account (ignore passwd)
	 * @param acc
	 * @return
	 */
	public ContactInfo getCustomerContactInfo(AccountInfo acc);
	
	/**
	 * Business get the contact info of the customer. Since the contact info of customer is private, 
	 * busi must use order to retrive
	 * @param odr
	 * @return customer's contact info
	 */
	public ContactInfo busiGetCusContactInfo(Order odr);
	
	/**
	 * get the orders for the user, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public ListedJSONObj getUserOrders(AccountInfo acc, int startInd, int endInd);
	
	/**
	 * get the orders for the user, from newest to the oldest, make them to be unchanged
	 * 
	 * @param uname
	 * @param passwd
	 * @param status 
	 * @return
	 */
	public ListedJSONObj getUserOrders(AccountInfo acc, int status, 
			int startInd, int endInd);
	
	/**
	 * get the changed orders for the user, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public ListedJSONObj getChangedUserOrders(AccountInfo acc);
	
	
	/**
	 * get the orders for the busi, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public ListedJSONObj getBusiOrders(AccountInfo acc, int startInd, int endInd);
	
	/**
	 * get the orders for the Busi, make them to be unchanged
	 * 
	 * @param uname
	 * @param passwd
	 * @param status - 0:unprocessed, <p> 1: processing <p> 2: processed <p> 3: cancelled
	 * @return
	 */
	public ListedJSONObj getBusiOrders(AccountInfo acc, int status, 
			int startInd, int endInd);
	
	/**
	 * get the changed orders for the Busi, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public ListedJSONObj getChangedBusiOrders(AccountInfo acc);
	
	/**
	 * restaurant updates an order
	 * make it new to customers
	 * @param o
	 */
	public void busiUpdateOrder(Order o);
	
	/**
	 * user updates an order, make it new to the restaurants
	 * @param o
	 */
	public void userUpdateOrder(Order o);
	
	public void submitOrder(Order o);
	
	
}
