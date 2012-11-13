package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.*;

/**
 * An implementation of the model AndroidClientModel
 * @author SuNFloWer
 *
 */
public class AndroidClientModelImpl implements AndroidClientModel {
	static private final int CONNECTIONTIMEOUT = 5000;
	private InternetClient internetClient;

	public AndroidClientModelImpl() {
		internetClient = new InternetClient();
		Log.v("[Server Info]", CommunicationProtocol.SERVER_IP_ADDRESS+" "+CommunicationProtocol.SERVER_PORT);
	}

	public String getResponseFromServerT() {
		Log.v("getResponseFromServerT", "executed");

		String a = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME, CommunicationProtocol.FB_SIGN_PASSWD, 
				CommunicationProtocol.BUSI_LOGIN, "{\"name\": \"try\"}");

		String responseFromServer = "";
		try {
			responseFromServer = internetClient.sendAndGet(a, CONNECTIONTIMEOUT);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseFromServer;
		
	}
	

	public boolean loginCheck(String username, String password) {
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_CHECK_UNAME_EXISTED, "");
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.FALSE) ){
			return false;
		} else if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.TRUE) ){
			return true;
		}
		return false;
	}

	public List<Restaurant> retrieveRestaurants(LocationInfo loc) {
		/* 1. Generate a sending message to be sent to the server with locationinfo */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to List<Restaurant> object */
		
		/*
		static final private String[][] restaurant_info = {
				{ "r1000", "New China II", "1030 WEST 41st St, Baltimore, MD 21211" },
				{ "r1001", "The Carlyle Club",
						"500 W University Pkwy, Baltimore, MD 21210" },
				{ "r1002", "Miss Shirley's Cafe",
						"513 W Cold Spring Ln,Baltimore, MD 21210" },
				{ "r1003", "One World Cafe",
						"100 W University Pkwy, Baltimore, MD 21210" },
				{ "r1004", "SanSoo Kab San",
						"2101 Maryland Ave, Baltimore, MD 21218" },
				{ "r1005", "The Food Market", "1017 W 36th St, Baltimore, MD 21211" },
				{ "r1006", "Tamber's Nifty Fifties Dining",
						"3327 St. Paul St, Baltimore, MD 21218" },
				{ "r1007", "Thai Restaurant",
						"3316 Greenmount Ave, Baltimore, MD 21218" } };
		*/
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		edu.jhu.cs.oose.project.group14.ihungry.model.Menu menu = new edu.jhu.cs.oose.project.group14.ihungry.model.Menu();
		Album album = new Album();
		Icon icon = new Icon();
		
		AccountInfo acc1 = new AccountInfo("newchina","");
		ContactInfo contact1 = new ContactInfo("New China II", "1030 WEST 41st St, Baltimore, MD 21211", "445-685-6652","","","",icon);
		Restaurant rest1 = new Restaurant(menu, album);
		rest1.setAccountInfo(acc1);
		rest1.setContactInfo(contact1);
		
		AccountInfo acc2 = new AccountInfo("carlyleclub", "");
		ContactInfo contact2 = new ContactInfo("The Carlyle Club", "500 W University Pkwy, Baltimore, MD 21210", "","","","",icon);
		Restaurant rest2 = new Restaurant(menu, album);
		rest2.setAccountInfo(acc2);
		rest2.setContactInfo(contact2);
		
		restaurants.add(rest1);
		restaurants.add(rest2);
		
		return restaurants;
	}

	public Menu retrieveMenu(String restId) {
		/* 1. Generate a sending message to be sent to the server with restId */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to Menu object */
		
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
		Menu menuServer = new Menu(restId, items);
		/* menuObj_Str should be inside the SupplyInfo of the response */
		String menuObj_Str = menuServer.getJSON().toString();
		/* -----------> transmitting from server ----------->  */
		
		/* <----------- received by client <-----------  */
		Menu menuClient = new Menu();
		JSONObject obj = null;
		try {
			Log.i("[Menu_info]", menuObj_Str+"");
			obj = new JSONObject(menuObj_Str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		menuClient.parseFromJSONObject( obj );
		
		return menuClient;
	}

	public Order createOrder(String orderId, String custId, String restId, int status, List<OrderItem> orderitems) {
		Order order = new Order(orderId, custId, restId, status, orderitems);
		
		return order;
	}

	public void submitOrder(Order order) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Order> retrieveOrders(Customer cust, String status,
			int count) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Order> retrieveChangedOrders() {
		// TODO Auto-generated method stub
		return null;
	}


}
