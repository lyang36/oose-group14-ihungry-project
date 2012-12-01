package edu.jhu.cs.oose.group14.restaurant.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import edu.jhu.cs.oose.fall2012.group14.ihungry.database.*;


public class iHungryRestaurantmodelImplTest {
		
		private ihungryRestaurantModelInterface restModel = new ihungryRestaurantModelImpl();
		
		/**
		 * Test if the server replies correct info based on username and password.
		 */
		public void testLoginCheck() {
			
			boolean result = restModel.loginCheck("r1000", "123");
			assertEquals(true,result);
		}
		
		
		/**
		 * Test if the correct Restaurant info is returned.
		 */
		public void testAttemptLogin(){
			
			AccountInfo accInfo = new AccountInfo("r1000","123");
			String rest = restModel.attemptLogin("r1000", "123");
			JSONObject obj = null;
			try{
			obj = new JSONObject(rest);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Restaurant res = new Restaurant(new Menu(), new Album());
			res.parseFromJSONObject(obj);
			assertEquals( accInfo.getUname(), res.getAccountInfo().getUname());
			
		}

		/**
		 * Test if the restaurant object generated 
		 * on the server side is the same as the return result by the model
		 */
		public void testSignupForNewUser(){
			
			String username = "Ploi Thai";
			String password = "123";
			String address = "Johns Hopkins University";
			String primphone = "111-222-4444";
			String secphone = "333-444-5555";
			String email = "ploithai@gmail.com";
			
			Restaurant res = new Restaurant(new Menu(), new Album());
			AccountInfo accInfo = new AccountInfo(username,password);
			LocationInfo linfo = new LocationInfo(address);
			ContactInfo cinfo = new ContactInfo(linfo,primphone);
			res.setAccountInfo(accInfo);
			res.setContactInfo(cinfo);
			
			restModel.signupForNewUser(res);
			
			JSONObject obj = null;
		    String rest= restModel.attemptLogin(res.getAccountInfo().getId(),res.getAccountInfo().getPasswd());
		    try{
				obj = new JSONObject(rest);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Restaurant res1 = new Restaurant(new Menu(), new Album());
			res1.parseFromJSONObject(obj);
			assertEquals( res.getContactInfo().getPrimPhone(), res1.getContactInfo().getPrimPhone());
	
		}
		
		/**
		 * Test if the correct Restaurant info is returned.
		 */
		public void testRetrieveOrders(){
			
			OrderItem o1 = new OrderItem(new Item("1","Pizza","Small Pizza",5.0,new Rating(),new Album()),3);
			List<OrderItem> list = new ArrayList<OrderItem>();
			list.add(o1);
			Order order1 = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
					MD5.getNameMd5("r1000"), Order.STATUS_UNDERPROCING, list);
			//dboperator.submitOrder(order1);
			
			AccountInfo accInfo = new AccountInfo("r1000","123");
			List<Order> orders = restModel.retreiveOrders("r1000", Order.STATUS_UNDERPROCING, 10);
			
			//assertEquals( true, orders.contains(order1));
			
		}
		
}
