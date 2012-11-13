package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public void setUp(){
		clientmodel = new AndroidClientModelImpl();
	}
	
	/**
	 * Test the connection with the server.
	 */
	public void testGetResponseFromServerT() {
		String responseSvr1 = clientmodel.getResponseFromServerT();

		Boolean condition = false;
		if( !responseSvr1.equals("")){
			condition = true;
		}
		
		System.out.println(responseSvr1);
		// assertEquals(responseSvr1, "No Such Command");
		assertTrue(responseSvr1, condition);
	}
	
	public void testLoginCheck() {
		Boolean responseSvr2 = clientmodel.loginCheck("lyang", "1234");

		System.out.println(responseSvr2);
		assertEquals(responseSvr2, (Boolean)true);
	}

	/*
	public void testRetrieveRestaurants() {
		fail("Not yet implemented");
	}*/

	public void testRetrieveMenu() {
		Menu menu = clientmodel.retrieveMenu("");
	
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

