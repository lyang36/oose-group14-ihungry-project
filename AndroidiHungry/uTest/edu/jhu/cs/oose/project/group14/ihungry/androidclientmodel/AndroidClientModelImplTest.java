package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import junit.framework.TestCase;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;

/**
 * This is an Android JUnit test class used to test the AndroidClientModel together with its implementation.
 * 
 * @author SuNFloWer
 *
 */
public class AndroidClientModelImplTest extends TestCase {
	AndroidClientModel clientmodel;
	
	/**
	 * Initialize the AndroidClientModel.
	 */
	@Override
	public void setUp(){
		clientmodel = new AndroidClientModelImpl();
	}
	
	/**
	 * Test the connection with the server.
	 */
/*	public void testGetResponseFromServerT() {
		String responseSvr1 = clientmodel.getResponseFromServerT();

		Boolean condition = false;
		if( !responseSvr1.equals("")){
			condition = true;
		}
		
		System.out.println(responseSvr1);
		// assertEquals(responseSvr1, "No Such Command");
		assertTrue(responseSvr1, condition);
	}*/
	
	/**
	 * Test if the server replies correct info based on different username and password.
	 */
	public void testLoginCheck() {
		Boolean responseSvr2 = clientmodel.loginCheck("lyang", "123");

		System.out.println(responseSvr2);
		assertEquals(responseSvr2, (Boolean)true);
	}
	
	
	/**
	 * Test if the correct Customer info is returned.
	 */
	public void testGetCustomerInfo(){
		Customer myCustInfo = clientmodel.getCustomerInfo("szhao12", "12345");
		
		ContactInfo cInfo = new ContactInfo("Shang Zhao",
				new LocationInfo("Johns Hopkins University, Baltimore, MD, 21218"),
				"911-911-9999", "443-343-1111", "szhao12@jhu.edu",
				"1989-12-11", new Icon());
		AccountInfo aInfo = new AccountInfo("szhao12", "12345");
		Customer customer = new Customer();
		customer.setAccountInfo(aInfo);
		customer.setContactInfo(cInfo);
		String responseFromServer = customer.getJSON().toString();
		
		assertEquals( responseFromServer, myCustInfo.getJSON().toString());
		
	}

	/**
	 * Test if the customer object generated 
	 * on the server side is the same as the return result by the model
	 */
	public void testSignupForNewUser(){
		String username = "szhao12";
		String password = "12345";
		String realname = "Shang Zhao";
		String address = "Johns Hopkins University";
		String primphone = "111-222-4444";
		String secphone = "333-444-5555";
		String email = "szhao12@jhu.edu";
		String birthday = "1989-12-11";
		Icon icon = new Icon();
		/* signupresult object is generated by the model */
		Customer signupresult = clientmodel.signupForNewUser(username,
				password, realname, address, primphone,
				secphone, email, birthday, icon);
		
		/* customer object is generated on the server side. */
		AccountInfo ainfo = new AccountInfo(username, password);
		ContactInfo cinfo = new ContactInfo(realname, new LocationInfo(address), primphone,
				secphone, email, birthday, icon);
		Customer customer = new Customer();
		customer.setContactInfo(cinfo);
		customer.setAccountInfo(ainfo);
		
		assertEquals(customer.getJSON().toString(), signupresult.getJSON().toString());
		
	}
	
	/*
	public void testGetRestaurantAccountInfos(){
		List<AccountInfo> busAccountInfos = clientmodel.getRestaurantAccountInfos();
		
		assertEquals(busAccountInfos.size(), 1);
	}
	*/
	
	public void testGetRestaurantContactInfos(){
		LocationInfo loc_test = new LocationInfo("Test");

		List<AccountInfo> busAccountInfos = clientmodel.getRestaurantAccountInfos(loc_test);
		List<ContactInfo> busContactInfos = clientmodel.getRestaurantContactInfos(busAccountInfos);
		
		System.out.println(busContactInfos.size());
		for(int i=0; i<busContactInfos.size(); i++){
			ContactInfo ci = (ContactInfo)busContactInfos.get(i);
			System.out.println(ci.getRealName()+" "+ci.getPrimPhone()+" " +ci.getAddress().getAddress() );
			
		}
		
	}
	
	
	/**
	 * Test the Restaurant list returned by the model is valid.
	 */
/*	public void testRetrieveRestaurants() {
		List<ContactInfo> rests = this.clientmodel.retrieveRestaurants(new LocationInfo(0.0, 0.0));
		
		
		assertEquals(rests.size(), 2);
		assertEquals(rests.get(0).getContactInfo().getRealName(), "New China II");
		assertEquals(rests.get(1).getContactInfo().getAddress().getAddress(), "500 W University Pkwy, Baltimore, MD 21210");
	
		
	}
*/

	/**
	 * Test if the Menu returned by the model is the same as the one generated by the server.
	 */
	public void testRetrieveMenu() {
		Menu menu = clientmodel.retrieveMenu("");
	
		/* Below is Generated by the server. */
		Item item1 = new Item("i001", "Chicken with Broccoli", 4.5, new Rating(4.0, 10), new Album());
		Item item2 = new Item("i002", "Assorted Mixed Vegetable", 4.65, new Rating(4.4, 11), new Album());
		Item item3 = new Item("i003", "Shrimp with Lobster Sauce", 4.95, new Rating(4.3, 12), new Album());
		Item item4 = new Item("i004", "Chicken with Cashew Nuts", 5.05, new Rating(4.1, 13), new Album());
		Item item5 = new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(3.95, 14), new Album());
		Item item6 = new Item("i006", "Skewered Beef", 4.5, new Rating(4.8, 15), new Album());
		Item item7 = new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16), new Album());
		Item item8 = new Item("i008", "House Special Soup", 5.50, new Rating(4.7, 17), new Album());
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		items.add(item8);
		Menu menuServer = new Menu("", items);
		/* menuObj_Str should be inside the SupplyInfo of the response */
		String menuObj_Str = menuServer.getJSON().toString();
		
		assertEquals(menuObj_Str, menu.getJSON().toString());
	}

	/**
	 * Test all the orders returned by the model are valid.
	 */
	public void testRetrieveAllOrders(){
		List<Order> orders = clientmodel.retrieveAllOrders("");
		
		List<OrderItem> o_items = orders.get(1).getOrderItems();
		assertEquals(o_items.get(3).getItem().getItemName(),"Wonton Soup");
		
		assertEquals(orders.get(2).getOrderItems().get(0).getQuantity(), 2);
	}
	
	/*
	public void testSubmitOrder() {
		fail("Not yet implemented");
	}

	public void testRetrieveOrders() {
		fail("Not yet implemented");
	}

	public void testRetrieveChangedOrders() {
		fail("Not yet implemented");
	}
	*/
}

