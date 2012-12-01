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
	private AndroidClientModel clientmodel0;
	private AndroidClientModel clientmodel;
	
	/**
	 * Initialize the AndroidClientModel.
	 */
	@Override
	public void setUp(){
		// For login check test => no account info is provided
		clientmodel0 = new AndroidClientModelImpl();
		
		// A valid account info is provided
		clientmodel = new AndroidClientModelImpl(new AccountInfo("szhao", "123"));
	}

	/**
	 * Test if the server replies correct info based on different username and password.
	 */
	public void testLoginCheck() {
		Boolean responseSrv1 = clientmodel0.loginCheck("szhao12", "456");
		Boolean responseSvr2 = clientmodel0.loginCheck("szhao", "123");

		assertEquals(responseSrv1, (Boolean)false);
		assertEquals(responseSvr2, (Boolean)true);
	}
	
	
	/**
	 * Test if the correct Customer info is returned.
	 */
	public void testGetCustomerInfo(){
		Customer myCustInfo = clientmodel.getCustomerInfo("szhao", "123");
		AccountInfo aInfo = myCustInfo.getAccountInfo();
		ContactInfo cInfo = myCustInfo.getContactInfo();
		assertEquals(cInfo.getPrimPhone(), "444-333-1234");
	}

	/**
	 * Test testSignupForNewUser.
	 */
	public void testSignupForNewUser(){
		String username = "sun";
		String password = "12345";
		String realname = "Shang Zhao";
		String address = "Johns Hopkins University";
		String primphone = "111-222-4444";
		String secphone = "333-444-5555";
		String email = "szhao12@jhu.edu";
		String birthday = "1989-12-11";
		Icon icon = new Icon();

		/* The command below will return true when the database do not have account with username: sun */
		Boolean signupresult = clientmodel0.signupForNewUser(username,
				password, realname, address, primphone,
				secphone, email, birthday, icon);
		assertEquals(signupresult, (Boolean)true);
		
		// Re-signup using the same info (username) => should be false
		Boolean signupresult2 = clientmodel0.signupForNewUser(username,
					password, realname, address, primphone,
					secphone, email, birthday, icon);
		assertEquals(signupresult2, (Boolean)false);	
	}
	
	/**
	 * Test getRestaurantAccountInfos() and getRestaurantContactInfos().
	 */
	public void testGetRestaurantAllInfos(){
		LocationInfo loc_test = new LocationInfo("Test");

		List<AccountInfo> busAccountInfos = clientmodel.getRestaurantAccountInfos(loc_test);
		List<ContactInfo> busContactInfos = clientmodel.getRestaurantContactInfos(busAccountInfos);
		
		for(int i=0; i<busAccountInfos.size(); i++){
			AccountInfo ai = (AccountInfo)busAccountInfos.get(i);
			System.out.println(ai.getId());
			Boolean check_bool = false;
			if(ai.getId() != null){
				check_bool = true;
			}
			assertEquals(check_bool, (Boolean)true);
		}
		
		System.out.println(busContactInfos.size());
		for(int i=0; i<busContactInfos.size(); i++){
			ContactInfo ci = (ContactInfo)busContactInfos.get(i);
			System.out.println(ci.getRealName()+" "+ci.getPrimPhone()+" " +ci.getAddress().getAddress() );
			Boolean check_bool = false;
			if(ci.getAddress().getAddress() != null){
				check_bool = true;
			}
			assertEquals(check_bool, (Boolean)true);
		}
		
	}

	/**
	 * Test retrieve Menu given a specific restaurant id.
	 */
	public void testRetrieveMenu() {
		Menu menu = clientmodel.retrieveMenu("704bfd2447528546a4d9913c"); // rest nameID
		List<Item> items = new ArrayList<Item>();
		items = menu.getItems();
		for(int i=0; i<items.size(); i++){
			Item item = items.get(i);
			System.out.println(item.getItemName()+ " " +item.getItemPrice() + " "+item.getItemRating().getRating() );
		}		
		assertEquals(items.size(), 8);
	}
	
	/**
	 * Test retrieve all the orders.
	 */
	public void testRetrieveAllOrders(){
		List<Order> orders = clientmodel.retrieveAllOrders();
		System.out.println("Order Size: "+orders.size());

		for(int i=0; i<orders.size(); i++){
			Order order = orders.get(i);
			System.out.println(order.getOrderID()+" "+order.getCustID() +" "+order.getRestID()+" "+order.getStatus());
		}
		
		// Currently in database, account szhao only have one order.
		assertEquals(orders.size(), 1);

	}

}

