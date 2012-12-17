package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;


public class internetClientTestUnit {

	@Test
	public void test() {
		
		ContactInfo contact = new ContactInfo(new LocationInfo("good place"), "1234");
		AccountInfo acc = new AccountInfo("No1Res", "abc");
		Restaurant bus = new Restaurant(new Menu(), new Album());
		bus.setAccountInfo(acc);
		bus.setContactInfo(contact);
		
		String a = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME, CommunicationProtocol.FB_SIGN_PASSWD, 
				CommunicationProtocol.BUSI_LOGIN, bus.getJSON().toString());
		
		System.out.println(a);
		System.out.println(CommunicationProtocol.getUnameFromReceivedStr(a));
		System.out.println(CommunicationProtocol.getPasswdFromReceivedStr(a));
		System.out.println(CommunicationProtocol.getRequestFromReceivedStr(a));
		System.out.println(CommunicationProtocol.getSupinfoFromReceivedStr(a));
		
		InternetClient client = new InternetClient();
		try {
			for(int i = 0; i < 6000; i++){
				assertEquals(CommunicationProtocol.getRequestFromReceivedStr(client.sendAndGet(a, 30000)), CommunicationProtocol.LOGIN_ERROR);
			}
		//	assertEquals(CommunicationProtocol.getRequestFromReceivedStr(client.sendAndGet(a, 30000)), CommunicationProtocol.LOGOUT);
			
		//	System.out.println(CommunicationProtocol.getRequestFromReceivedStr(client.sendAndGet(a, 30000)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

}
