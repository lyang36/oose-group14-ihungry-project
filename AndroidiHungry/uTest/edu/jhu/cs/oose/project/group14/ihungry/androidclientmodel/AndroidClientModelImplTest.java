package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import junit.framework.TestCase;

/**
 * This is an Android JUnit test class used to test the AndroidClientModel together with its implementation.
 * 
 * @author SuNFloWer
 *
 */
public class AndroidClientModelImplTest extends TestCase {

	AndroidClientModel clientmodel = new AndroidClientModelImpl();
	String responseSvr = clientmodel.getResponseFromServerT();
	
	/**
	 * Test the connection with the server.
	 */
	public void testGetResponseFromServerT() {

		System.out.println(responseSvr);
		assertEquals(responseSvr, "No Such Command");
		
	//	fail("Not yet implemented");
	}

}
