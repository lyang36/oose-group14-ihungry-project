package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperatorTestUnit;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public class MessageReactorImplTest {
	public void testCommand(String uname, String passwd,String command,
			final String expectedReturnCmd, String supinfo){
		MessageReactorImpl msgReactor = new MessageReactorImpl();
		//check username unexisted
		String input = CommunicationProtocol.construcSendingStr(
				uname, passwd, 
				command, 
				supinfo);
		msgReactor.reactToMsg(input, new InternetUtil(){

			@Override
			public void setSocket(Socket so) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public long receiveFile(String filename) throws Exception {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long sendFile(String filename) throws Exception {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String receiveMessage() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void sendMsg(String msg) throws Exception {
				String cmdString = CommunicationProtocol.getRequestFromReceivedStr(msg);
				System.out.println("SuppliInfo: \n" +
						CommunicationProtocol.getSupinfoFromReceivedStr(msg));
				//System.out.println(CommunicationProtocol.getSupinfoFromReceivedStr(msg));
				assertEquals(expectedReturnCmd, cmdString);
			}
			
		});
	}

	@Test
	public void test() {
		//check username unexisted
		testCommand(MD5.getNameMd5("szhao12345"),
				MD5.getMd5("1234"), CommunicationProtocol.CUS_CHECK_UNAME_EXISTED,
				CommunicationProtocol.FALSE, "");
		System.out.println("Check username unexisted succeeded!!!");
		
		
		//check businame unexisted
		testCommand(MD5.getNameMd5("szhao12345"),
				MD5.getMd5("1234"), CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED,
				CommunicationProtocol.FALSE, "");
		System.out.println("Check businame unexisted succeeded!!!");

		
	}


	
	@Test
	public void testBusiAccount(){
		DBOperatorTestUnit.initializeDB();
		Menu m = new Menu();
		Album ab = new Album();
		ContactInfo contact = new ContactInfo(new LocationInfo("abc dff"), "123456687");
		AccountInfo acc = new AccountInfo("lyang", "123");
		Restaurant res = new Restaurant(m, ab);
		res.setAccountInfo(acc);
		res.setContactInfo(contact);
		res.parseFromJSONObject(res.getJSON());
		System.out.println(res.getJSON().toString());
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_SIGNUP,
				CommunicationProtocol.PROCESS_SUCCEEDED, res.getJSON().toString());
		
		//test re-signup
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_SIGNUP,
				CommunicationProtocol.PROCESS_FAILED, res.getJSON().toString());
		
		//test login
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_LOGIN,
				CommunicationProtocol.LOGIN_SUCCESS, res.getJSON().toString());
		
		//test re-check name existed
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED,
				CommunicationProtocol.TRUE, res.getJSON().toString());
		
		//test update contact info
		contact = new ContactInfo(new LocationInfo("abc dff"), "4444433");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_UPDATE_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, contact.getJSON().toString());
		
		//test get contact info
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_GET_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
	}
	
	
	@Test
	public void testCustomerAccount(){
		ContactInfo contact = new ContactInfo(new LocationInfo("abc dff"), "123456687");
		AccountInfo acc = new AccountInfo("lyang", "123");
		Customer cus = new Customer();
		cus.setAccountInfo(acc);
		cus.setContactInfo(contact);

		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_SIGN_UP,
				CommunicationProtocol.PROCESS_SUCCEEDED, cus.getJSON().toString());
		
		//test re-signup
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_SIGN_UP,
				CommunicationProtocol.PROCESS_FAILED, cus.getJSON().toString());
		
		//test login
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_LOGIN,
				CommunicationProtocol.LOGIN_SUCCESS, cus.getJSON().toString());
		
		//test re-check name existed
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_CHECK_UNAME_EXISTED,
				CommunicationProtocol.TRUE, cus.getJSON().toString());
		
		
		//test update contact info
		contact = new ContactInfo(new LocationInfo("abc dff"), "4444433");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_UPDATE_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, contact.getJSON().toString());
		
		//test get contact info
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_GET_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		
	}
	
	
	@Test
	public void testCustomerFunction(){
		//test find restaurant
		LocationInfo loc = new LocationInfo("Test");
		loc.parseFromJSONObject(loc.getJSON());
		System.out.println(loc.getJSON().toString());
		AccountInfo acc = new AccountInfo("lyang", "");
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_FIND_RESTAURANT_IDS,
				CommunicationProtocol.PROCESS_SUCCEEDED, loc.getJSON().toString());
		
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_GET_RES_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, acc.getJSON().toString());
	}
}
