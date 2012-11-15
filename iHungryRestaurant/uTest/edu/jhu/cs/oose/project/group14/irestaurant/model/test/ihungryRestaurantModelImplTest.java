package edu.jhu.cs.oose.project.group14.irestaurant.model.test;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;

import junit.framework.TestCase;




/**
 * This is a JUnit test class used to test the RestaurantModelImpl class.
 * 
 * @author parkavi
 *
 */
public class ihungryRestaurantModelImplTest extends TestCase {
	ihungryRestaurantModelInterface testModel;
	
	/**
	 * Initialize the AndroidClientModel.
	 */
	@Override
	public void setUp(){
		testModel = new ihungryRestaurantModelImpl();
	}
	
	/**
	 * Test the connection with the server.
	 */
	public void testGetResponseFromServer() {
		String responseSvr1 = testModel.getResponseFromServer();

		Boolean condition = false;
		if( !responseSvr1.equals("")){
			condition = true;
		}
		
		System.out.println(responseSvr1);
		// assertEquals(responseSvr1, "No Such Command");
		assertTrue(responseSvr1, condition);
	}
	
	/**
	 * Test if the server replies correct info based on different username and password.
	 */
	public void testLoginCheck() {
		Boolean responseSvr2 = testModel.loginCheck("lyang", "1234");

		System.out.println(responseSvr2);
		assertEquals(responseSvr2, (Boolean)true);
	}
	
	
	
}

