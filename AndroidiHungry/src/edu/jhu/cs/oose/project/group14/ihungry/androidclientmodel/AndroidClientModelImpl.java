package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;
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
 * 
 * @author SuNFloWer
 * 
 */
public class AndroidClientModelImpl implements AndroidClientModel {
	static private final int CONNECTIONTIMEOUT = 10000;
	private InternetClient internetClient;
	private AccountInfo customer_account;
	
	public AndroidClientModelImpl() {
		internetClient = new InternetClient();
		Log.v("[Constr01: Server Info]", CommunicationProtocol.SERVER_IP_ADDRESS + " "
				+ CommunicationProtocol.SERVER_PORT);
	}
	
	public AndroidClientModelImpl(AccountInfo customer_info_in){
		this.customer_account = customer_info_in;
		internetClient = new InternetClient();
		Log.v("[Constr02: Server Info]", CommunicationProtocol.SERVER_IP_ADDRESS + " "
				+ CommunicationProtocol.SERVER_PORT);
	}

	/*
	public String getResponseFromServerT() {
		Log.v("getResponseFromServerT", "executed");

		String a = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME,
				CommunicationProtocol.FB_SIGN_PASSWD,
				CommunicationProtocol.BUSI_LOGIN, "{\"name\": \"try\"}");

		String responseFromServer = "";
		try {
			responseFromServer = internetClient
					.sendAndGet(a, CONNECTIONTIMEOUT);

		} catch (Exception e) {
			Log.e("[getResponseFromServer Exception]",""+e.getMessage());
			e.printStackTrace();
		}

		return responseFromServer;

	}*/

	public boolean loginCheck(String username, String password) {
		String sendStr = CommunicationProtocol.construcSendingStr(
				MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_CHECK_UNAME_EXISTED, "");
		String responseStr = "";
		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.FALSE)) {
			return false;
		} else if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.TRUE)) {
			return true;
		}
		return false;
	}

	public Customer getCustomerInfo(String username, String password) {
		
		/* Assume below is Generated on the server side */
/*		ContactInfo cInfo = new ContactInfo("Shang Zhao",
				new LocationInfo("Johns Hopkins University, Baltimore, MD, 21218"),
				"911-911-9999", "443-343-1111", "szhao12@jhu.edu",
				"1989-12-11", new Icon());
		AccountInfo aInfo = new AccountInfo("szhao12", "12345");
		Customer customer = new Customer();
		customer.setAccountInfo(aInfo);
		customer.setContactInfo(cInfo);
		String responseFromServer = customer.getJSON().toString();
*/
		/* -----------> transmitting from server -----------> */

		/* <----------- received by client <----------- */
		String sendStr = CommunicationProtocol.construcSendingStr(
				MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_LOGIN, "");

		String responseStr = "";
		Customer customer = new Customer();

		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			System.out.println("Customer [Response]: "+responseStr);

			if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
					.equals(CommunicationProtocol.LOGIN_ERROR)) {
				return null;
			}
			
			String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
			JSONObject obj = new JSONObject(supinfo);
			customer.parseFromJSONObject(obj);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return customer;
	}

	public boolean signupForNewUser(String username, String password,
			String realname, String address, String primphone, String secphone,
			String email, String birthday, Icon icon) {
		
		AccountInfo ainfo = new AccountInfo(username, password);
		ContactInfo cinfo = new ContactInfo(realname, new LocationInfo(address), primphone,
				secphone, email, birthday, icon);
		Customer customer = new Customer();
		customer.setContactInfo(cinfo);
		customer.setAccountInfo(ainfo);
		/* Send the customer info to the server. */
		/* If success received, return the customer object. 
		 * Else return null. */
		/* Generate a request containing the JSON object string to the server. */		
		String sendStr = CommunicationProtocol.construcSendingStr(
				MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_SIGN_UP, customer.getJSON().toString());
		String responseStr = "";

		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.PROCESS_FAILED)) {
			return false;
		} else if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.PROCESS_SUCCEEDED)) {
			return true;
		}
		return false;
		
	}

	public List<AccountInfo> getRestaurantAccountInfos(LocationInfo loc_in){
		
		/* Get restaurant Account Infos */
		LocationInfo loc_test = new LocationInfo("Test");
		loc_test.parseFromJSONObject(loc_test.getJSON());
		List<AccountInfo> busAccountInfos = new ArrayList<AccountInfo>();

		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_FIND_RESTAURANT_IDS, loc_test.getJSON().toString());
		
		String responseStr = "";
		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			System.out.println("response: "+responseStr);
			
			String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
			ListedJSONObj jobj = new ListedJSONObj();
			jobj.parseFromJSONObject(new JSONObject(supinfo));
		
			Iterator<JSONObject> it = jobj.iterator();
			while(it.hasNext()){
				AccountInfo busacc = new AccountInfo();
				busacc.parseFromJSONObject(it.next());
				Log.v("[bus ids]",""+busacc.getId());
				busAccountInfos.add(busacc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return busAccountInfos;
	}
	
	public List<ContactInfo> getRestaurantContactInfos(List<AccountInfo> bus_accInfos){
		List<ContactInfo> bus_conInfos = new ArrayList<ContactInfo>();

		for(int i=0; i<bus_accInfos.size(); i++){
			AccountInfo bus_acc = bus_accInfos.get(i);
			bus_conInfos.add(this.getRestaurantContactInfoSingle(bus_acc));
		}
		return bus_conInfos;
	}
	
	public ContactInfo getRestaurantContactInfoSingle(AccountInfo bus_acc){
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_GET_RES_CONTACT, bus_acc.getJSON().toString());
		String responseStr = "";
		ContactInfo bus_con = new ContactInfo(new LocationInfo(""), "");

		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			System.out.println("bus Contact Info [Response]: "+responseStr);

			String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
			JSONObject obj = new JSONObject(supinfo);
			bus_con.parseFromJSONObject(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bus_con;
	}

	/*
	public List<ContactInfo> retrieveRestaurants(LocationInfo loc) {
		LocationInfo loc_test = new LocationInfo("Test");
		List<AccountInfo> busAccountInfos = this.getRestaurantAccountInfos(loc_test);
		List<ContactInfo> busContactInfos = this.getRestaurantContactInfos(busAccountInfos);
		
		return busContactInfos;
*/
		/*
		 * 1. Generate a sending message to be sent to the server with
		 * locationinfo
		 */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to List<Restaurant> object */

		/*
		 * static final private String[][] restaurant_info = { { "r1000",
		 * "New China II", "1030 WEST 41st St, Baltimore, MD 21211" }, {
		 * "r1001", "The Carlyle Club",
		 * "500 W University Pkwy, Baltimore, MD 21210" }, { "r1002",
		 * "Miss Shirley's Cafe", "513 W Cold Spring Ln,Baltimore, MD 21210" },
		 * { "r1003", "One World Cafe",
		 * "100 W University Pkwy, Baltimore, MD 21210" }, { "r1004",
		 * "SanSoo Kab San", "2101 Maryland Ave, Baltimore, MD 21218" }, {
		 * "r1005", "The Food Market", "1017 W 36th St, Baltimore, MD 21211" },
		 * { "r1006", "Tamber's Nifty Fifties Dining",
		 * "3327 St. Paul St, Baltimore, MD 21218" }, { "r1007",
		 * "Thai Restaurant", "3316 Greenmount Ave, Baltimore, MD 21218" } };
		 */
		/*
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
		 */
		
//	}

	public Menu retrieveMenu(String restId) {
		/* 1. Generate a sending message to be sent to the server with restId */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to Menu object */

/*		Item item1 = new Item("i001", "Chicken with Broccoli", 4.5, new Rating(
				4.0, 10), new Album());
		Item item2 = new Item("i002", "Assorted Mixed Vegetable", 4.65,
				new Rating(4.4, 11), new Album());
		Item item3 = new Item("i003", "Shrimp with Lobster Sauce", 4.95,
				new Rating(4.3, 12), new Album());
		Item item4 = new Item("i004", "Chicken with Cashew Nuts", 5.05,
				new Rating(4.1, 13), new Album());
		Item item5 = new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(
				3.95, 14), new Album());
		Item item6 = new Item("i006", "Skewered Beef", 4.5,
				new Rating(4.8, 15), new Album());
		Item item7 = new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16),
				new Album());
		Item item8 = new Item("i008", "House Special Soup", 5.50, new Rating(
				4.7, 17), new Album());
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		items.add(item8);
		Menu menuServer = new Menu(restId, items); */
		/* menuObj_Str should be inside the SupplyInfo of the response */
//		String menuObj_Str = menuServer.getJSON().toString();
		/* -----------> transmitting from server -----------> */

		AccountInfo bus_acc = new AccountInfo();
		bus_acc.setId(restId);
				
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_GET_MENU, bus_acc.getJSON().toString());
		String responseStr = "";
		Menu menu_received = new Menu();

		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			
			String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
			JSONObject obj = new JSONObject(supinfo);
			menu_received.parseFromJSONObject(obj);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* <----------- received by client <----------- */
/*		JSONObject obj = null;
		try {
			Log.i("[Menu_info]", menuObj_Str + "");
			obj = new JSONObject(menuObj_Str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		menuClient.parseFromJSONObject(obj);
*/
		return menu_received;
	}

	@SuppressLint("SimpleDateFormat") 
	public Order createOrder(String restId,
			int status, List<OrderItem> orderitems) {
		
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		String orderDate = formatter.format(calendar.getTime());
		Log.v("[Order ID (Date)]", orderDate+" "+this.customer_account.getUname());
		
		String orderId = this.customer_account.getUname()+"-"+orderDate;
		Order order = new Order(orderId, this.customer_account.getId(), restId, status, orderitems);

		return order;
	}

	public boolean submitOrder(Order order) {
		/* Generate a request containing the JSON object string to the server. */
		Log.v("[Model: submitOrder]", "Order submitted.!");
		
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_SUBMIT_ORDER, order.getJSON().toString());
		String responseStr = "";

		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.PROCESS_FAILED)) {
			return false;
		} else if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
				.equals(CommunicationProtocol.PROCESS_SUCCEEDED)) {
			return true;
		}
		return false;
	}

	public List<Order> retrieveAllOrders() {
		/*
		 * Generate a sending message containing custId to server and if
		 * response are valid => convert to a List<Order> object
		 */
/*
		List<Order> orders = new ArrayList<Order>();

		Item item1 = new Item("i001", "Chicken with Broccoli", 4.5, new Rating(
				4.0, 10), new Album());
		Item item2 = new Item("i002", "Assorted Mixed Vegetable", 4.65,
				new Rating(4.4, 11), new Album());
		Item item3 = new Item("i003", "Shrimp with Lobster Sauce", 4.95,
				new Rating(4.3, 12), new Album());
		List<OrderItem> o_items1 = new ArrayList<OrderItem>();
		o_items1.add(new OrderItem(item1, 2));
		o_items1.add(new OrderItem(item2, 1));
		o_items1.add(new OrderItem(item3, 1));
		Order order1 = new Order("o002", "c003", "r004", 1, o_items1);

		Item item4 = new Item("i004", "Chicken with Cashew Nuts", 5.05,
				new Rating(4.1, 13), new Album());
		Item item5 = new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(
				3.95, 14), new Album());
		Item item6 = new Item("i006", "Skewered Beef", 4.5,
				new Rating(4.8, 15), new Album());
		Item item7 = new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16),
				new Album());
		List<OrderItem> o_items2 = new ArrayList<OrderItem>();
		o_items2.add(new OrderItem(item4, 1));
		o_items2.add(new OrderItem(item5, 1));
		o_items2.add(new OrderItem(item6, 3));
		o_items2.add(new OrderItem(item7, 2));
		Order order2 = new Order("o001", "c003", "r009", 3, o_items2);

		Item item8 = new Item("i008", "House Special Soup", 5.50, new Rating(
				4.7, 17), new Album());
		List<OrderItem> o_items3 = new ArrayList<OrderItem>();
		o_items3.add(new OrderItem(item8, 2));
		Order order3 = new Order("o003", "c003", "r012", 2, o_items3);

		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
*/
		/* Get restaurant Account Infos */
		List<Order> my_orders = new ArrayList<Order>();
		OrderQuerier querier = new OrderQuerier();
		querier.setCusID("lyang");
		
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_RETRIVE_ORDER, querier.getJSON().toString());
		
		String responseStr = "";
		try {
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
			System.out.println("response: "+responseStr);
			
			String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
			ListedJSONObj jobj = new ListedJSONObj();
			jobj.parseFromJSONObject(new JSONObject(supinfo));
		
			Iterator<JSONObject> it = jobj.iterator();
			while(it.hasNext()){
				Order order = new Order("", "", "", -1, new ArrayList<OrderItem>());
				order.parseFromJSONObject(it.next());
				my_orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return my_orders;
	}

	public List<Order> retrieveOrders(String status, int count) {
		// TODO Auto-generated method stub
		/*
		 * 1. Generate a sending message to be sent to the server with Customer,
		 * status and count
		 */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to List<Order> object */
		
		return null;
	}

	public List<Order> retrieveChangedOrders() {
		// TODO Auto-generated method stub
		return null;
	}
}
