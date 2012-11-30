package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperatorTestUnit;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.OrderQuerier;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;


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


	public void addRestaurants(){
		
		//incert data
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		edu.jhu.cs.oose.project.group14.ihungry.model.Menu menu = new edu.jhu.cs.oose.project.group14.ihungry.model.Menu();
		Album album = new Album();
		Icon icon = new Icon();

		AccountInfo acc1 = new AccountInfo("newchina", "");
		ContactInfo contact1 = new ContactInfo("New China II",
				new LocationInfo("1030 WEST 41st St, Baltimore, MD 21211"), "445-685-6652", "",
				"", "", icon);
		Restaurant rest1 = new Restaurant(menu, album);
		rest1.setAccountInfo(acc1);
		rest1.setContactInfo(contact1);

		AccountInfo acc2 = new AccountInfo("carlyleclub", "");
		ContactInfo contact2 = new ContactInfo("The Carlyle Club",
				new LocationInfo("500 W University Pkwy, Baltimore, MD 21210"), "", "", "", "",
				icon);
		Restaurant rest2 = new Restaurant(menu, album);
		rest2.setAccountInfo(acc2);
		rest2.setContactInfo(contact2);

		restaurants.add(rest1);
		restaurants.add(rest2);
		
		for(Restaurant res:restaurants){
			testCommand(res.getAccountInfo().getId(),
					res.getAccountInfo().getPasswd(), CommunicationProtocol.BUSI_SIGNUP,
					CommunicationProtocol.PROCESS_SUCCEEDED, res.getJSON().toString());
		}
		
		
	}
	
	
	@Test
	public void testBusiAccount(){
		DBOperatorTestUnit.initializeDB();
		
		addRestaurants();
		
		
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
		System.out.println("Test Login");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_LOGIN,
				CommunicationProtocol.LOGIN_SUCCESS, res.getJSON().toString());
		
		//test re-check name existed
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED,
				CommunicationProtocol.TRUE, res.getJSON().toString());
		
		//test update contact info
		//contact = new ContactInfo(new LocationInfo("abc dff"), "4444433");
		contact = new ContactInfo("Lyang Cafe",new LocationInfo("Near JHU, 21218, MD"), 
				"911-000-1111", "111-111-2222", 
				"lyang@cafe.com", "1989-0-0", new Icon());
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_UPDATE_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, contact.getJSON().toString());
		
		//test get contact info
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_GET_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		
		//test update album info
		Album album = new Album();
		album.addPhoto(new Icon());
		album.addPhoto(new Icon());
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_UPDATE_ALBUM,
				CommunicationProtocol.PROCESS_SUCCEEDED, album.getJSON().toString());
		
		//test get album
		System.out.println("Test Busi get album");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_GET_ALBUM,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		
		
		Menu menu0 = new Menu();
		menu0.setRestId(MD5.getNameMd5("lyang"));
		menu0.addItem(new Item());
		menu0.addItem(new Item());
		
		/* Add Menu */
		Menu menu1 = new Menu();
		menu1.setRestId(MD5.getNameMd5("newchina"));
		menu1.addItem(new Item("i001", "Chicken with Broccoli", 4.5, new Rating(
				4.0, 10), new Album()));
		menu1.addItem(new Item("i002", "Assorted Mixed Vegetable", 4.65,
				new Rating(4.4, 11), new Album()));
		menu1.addItem(new Item("i003", "Shrimp with Lobster Sauce", 4.95,
				new Rating(4.3, 12), new Album()));
		menu1.addItem(new Item("i004", "Chicken with Cashew Nuts", 5.05,
				new Rating(4.1, 13), new Album()));
		menu1.addItem(new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(
				3.95, 14), new Album()));
		menu1.addItem(new Item("i006", "Skewered Beef", 4.5,
				new Rating(4.8, 15), new Album()));
		menu1.addItem(new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16),
				new Album()));
		menu1.addItem(new Item("i008", "House Special Soup", 5.50, new Rating(
				4.7, 17), new Album()));
		testCommand(MD5.getNameMd5("newchina"),
				MD5.getMd5(""), CommunicationProtocol.BUSI_UPDATE_MENU,
				CommunicationProtocol.PROCESS_SUCCEEDED, menu1.getJSON().toString());
		
		
		System.out.println(menu0.getJSON().toString());
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_UPDATE_MENU,
				CommunicationProtocol.PROCESS_SUCCEEDED, menu0.getJSON().toString());
		
		//test get album
		System.out.println("Test Busi get menu");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_GET_MENU,
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
		System.out.println("Test Login");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_LOGIN,
				CommunicationProtocol.LOGIN_SUCCESS, cus.getJSON().toString());
		
		//test re-check name existed
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_CHECK_UNAME_EXISTED,
				CommunicationProtocol.TRUE, cus.getJSON().toString());
		
		
		//test update contact info
		//contact = new ContactInfo(new LocationInfo("abc dff"), "4444433");
		contact = new ContactInfo("Lin Yang",new LocationInfo("JHU, 21218, MD"), 
				"911-000-1111", "111-111-2222", 
				"lyang@jhu.edu", "1989-0-0", new Icon());
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
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_GET_RES_ALBUM,
				CommunicationProtocol.PROCESS_SUCCEEDED, acc.getJSON().toString());
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_GET_MENU,
				CommunicationProtocol.PROCESS_SUCCEEDED, acc.getJSON().toString());
		
		
	}
	
	
	@Test
	public void testOrderFunctions(){
		//test find restaurant
		LocationInfo loc = new LocationInfo("Test");
		loc.parseFromJSONObject(loc.getJSON());
		System.out.println(loc.getJSON().toString());
		AccountInfo acc = new AccountInfo("lyang", "");
		
		Menu m = new Menu();
		Album ab = new Album();
		ContactInfo contact = new ContactInfo(new LocationInfo("abc dff"), "123456687");
		AccountInfo busiacc = new AccountInfo("lyres", "123");
		Restaurant res = new Restaurant(m, ab);
		res.setAccountInfo(busiacc);
		res.setContactInfo(contact);
		
		System.out.println(res.getJSON().toString());
		testCommand(MD5.getNameMd5("lyres"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_SIGNUP,
				CommunicationProtocol.PROCESS_SUCCEEDED, res.getJSON().toString());
		
		
		Order order = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("lyres"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_SUBMIT_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, order.getJSON().toString());
		
		
		order = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("lyang"), Order.STATUS_CANCELLED, new ArrayList<OrderItem>());
		
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_UPDATE_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, order.getJSON().toString());
		
		System.out.println("Customer try to retrive orders");
		OrderQuerier querier = new OrderQuerier();
		querier.setCusID(MD5.getNameMd5("lyang"));
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_RETRIVE_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, querier.getJSON().toString());
		
		
		System.out.println("Customer try to retrive changed orders, should be zero");

		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		
		System.out.println("Business try to retrive changed orders -- 1 changed");
		querier = new OrderQuerier();
		//querier.setCusID(MD5.getNameMd5("lyang"));
		querier.setRestaurantID(MD5.getNameMd5("lyres"));
		testCommand(MD5.getNameMd5("lyres"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS,
				CommunicationProtocol.PROCESS_SUCCEEDED, querier.getJSON().toString());
		
		System.out.println("Business try to retrive changed orders -- 0 changed");
		querier.setRestaurantID(MD5.getNameMd5("lyres"));
		testCommand(MD5.getNameMd5("lyres"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS,
				CommunicationProtocol.PROCESS_SUCCEEDED, querier.getJSON().toString());
		
		
		System.out.println("Business update an order");
		order = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("lyres"), Order.STATUS_CONFIRMED, new ArrayList<OrderItem>());
		testCommand(MD5.getNameMd5("lyres"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_PROCESS_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, order.getJSON().toString());
		
		System.out.println("Customer try to retrive changed orders, should be 1");
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123"), CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		
		
		System.out.println("Restaurant test use order to get customer's contact");
		testCommand(MD5.getNameMd5("lyres"),
				MD5.getMd5("123"), CommunicationProtocol.BUSI_GET_CUS_CONTACT,
				CommunicationProtocol.PROCESS_SUCCEEDED, order.getJSON().toString());
		

	}
	
	
}
