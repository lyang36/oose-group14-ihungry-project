package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import junit.framework.TestCase;

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
	}

	public void testRetrieveMenu() {
		fail("Not yet implemented");
	}

	public void testCreateOrder() {
		fail("Not yet implemented");
	}

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

