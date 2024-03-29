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
	static private final int CONNECTION_TIMEOUT = 20000; // Time out => 20 secs
	private InternetClient internetClient;
	private AccountInfo customer_account;

	/**
	 * A constructor with no parameter.
	 */
	public AndroidClientModelImpl() {
		internetClient = new InternetClient();
		Log.v("[Constr01: Server Info]",
				CommunicationProtocol.SERVER_IP_ADDRESS + " "
						+ CommunicationProtocol.SERVER_PORT);
	}

	/**
	 * A constructor with the customer's AccountInfo as parameter.
	 * 
	 * @param customer_info_in
	 */
	public AndroidClientModelImpl(AccountInfo customer_info_in) {
		this.customer_account = customer_info_in;
		internetClient = new InternetClient();
		Log.v("[Constr02: Server Info]",
				CommunicationProtocol.SERVER_IP_ADDRESS + " "
						+ CommunicationProtocol.SERVER_PORT);
	}

	public boolean loginCheck(String username, String password) {
		String sendStr = CommunicationProtocol.construcSendingStr(
				MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_CHECK_UNAME_EXISTED, "");
		String responseStr = "";
		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);
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

		String sendStr = CommunicationProtocol.construcSendingStr(
				MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_LOGIN, "");

		String responseStr = "";
		Customer customer = new Customer();

		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);
			System.out.println("Customer [Response]: " + responseStr);

			if (CommunicationProtocol.getRequestFromReceivedStr(responseStr)
					.equals(CommunicationProtocol.LOGIN_ERROR)) {
				return null;
			}

			String supinfo = CommunicationProtocol
					.getSupinfoFromReceivedStr(responseStr);
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
		ContactInfo cinfo = new ContactInfo(realname,
				new LocationInfo(address), primphone, secphone, email,
				birthday, icon);
		Customer customer = new Customer();
		customer.setContactInfo(cinfo);
		customer.setAccountInfo(ainfo);
		/* Send the customer info to the server. */
		/*
		 * If success received, return the customer object. Else return null.
		 */
		/* Generate a request containing the JSON object string to the server. */
		String sendStr = CommunicationProtocol.construcSendingStr(MD5
				.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.CUS_SIGN_UP, customer.getJSON()
						.toString());
		String responseStr = "";

		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);

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

	public List<AccountInfo> getRestaurantAccountInfos(LocationInfo loc_in) {
		/* Get restaurant Account Infos */
		List<AccountInfo> busAccountInfos = new ArrayList<AccountInfo>();

		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_FIND_RESTAURANT_IDS, loc_in.getJSON()
						.toString());

		String responseStr = "";
		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);
			System.out.println("response: " + responseStr);

			String supinfo = CommunicationProtocol
					.getSupinfoFromReceivedStr(responseStr);
			ListedJSONObj jobj = new ListedJSONObj();
			jobj.parseFromJSONObject(new JSONObject(supinfo));

			Iterator<JSONObject> it = jobj.iterator();
			while (it.hasNext()) {
				AccountInfo busacc = new AccountInfo();
				busacc.parseFromJSONObject(it.next());
				Log.v("[bus ids]", "" + busacc.getId());
				busAccountInfos.add(busacc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return busAccountInfos;
	}

	public List<ContactInfo> getRestaurantContactInfos(
			List<AccountInfo> bus_accInfos) {
		List<ContactInfo> bus_conInfos = new ArrayList<ContactInfo>();

		for (int i = 0; i < bus_accInfos.size(); i++) {
			AccountInfo bus_acc = bus_accInfos.get(i);
			bus_conInfos.add(this.getRestaurantContactInfoSingle(bus_acc));
		}
		return bus_conInfos;
	}

	public ContactInfo getRestaurantContactInfoSingle(AccountInfo bus_acc) {
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_GET_RES_CONTACT, bus_acc.getJSON()
						.toString());
		String responseStr = "";
		ContactInfo bus_con = new ContactInfo(new LocationInfo(""), "");

		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);
			System.out.println("bus Contact Info [Response]: " + responseStr);

			String supinfo = CommunicationProtocol
					.getSupinfoFromReceivedStr(responseStr);
			JSONObject obj = new JSONObject(supinfo);
			bus_con.parseFromJSONObject(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bus_con;
	}

	public Menu retrieveMenu(String restId) {
		/* 1. Generate a sending message to be sent to the server with restId */
		/* 2. Call internetClient.sendAndGet to get response from server */
		/* 3. If valid => parse the information to Menu object */

		AccountInfo bus_acc = new AccountInfo();
		bus_acc.setId(restId);

		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_GET_MENU, bus_acc.getJSON()
						.toString());
		String responseStr = "";
		Menu menu_received = new Menu();

		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);

			String supinfo = CommunicationProtocol
					.getSupinfoFromReceivedStr(responseStr);
			JSONObject obj = new JSONObject(supinfo);
			menu_received.parseFromJSONObject(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menu_received;
	}

	@SuppressLint("SimpleDateFormat")
	public Order createOrder(String restId, int status,
			List<OrderItem> orderitems) {

		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		String orderDate = formatter.format(calendar.getTime());
		Log.v("[Order ID (Date)]",
				orderDate + " " + this.customer_account.getUname());

		String orderId = this.customer_account.getUname() + "-" + orderDate;
		Order order = new Order(orderId, this.customer_account.getId(), restId,
				status, orderitems);

		return order;
	}

	/**
	 * Combine methods submitOrder() and updateOrder().
	 * 
	 * @param order
	 * @param command
	 * @return
	 */
	public boolean submitOrUpdateOrder(Order order, String command) {
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				command, order.getJSON().toString());
		String responseStr = "";

		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);

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

	public boolean submitOrder(Order order) {
		/* Generate a request containing the JSON object string to the server. */
		Log.v("[Model: submitOrder]", "Order submitted.!");
		return submitOrUpdateOrder(order,
				CommunicationProtocol.CUS_SUBMIT_ORDER);
	}

	public boolean updateOrder(Order order) {
		Log.v("[Model: updateOrder]", "Try to cancel Order.!");
		return submitOrUpdateOrder(order,
				CommunicationProtocol.CUS_UPDATE_ORDER);
	}

	public List<Order> retrieveAllOrders() {
		/*
		 * Generate a sending message containing custId to server and if
		 * response are valid => convert to a List<Order> object
		 */
		OrderQuerier querier = new OrderQuerier();
		// querier.setCusID(customer_account.getUname());
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_RETRIVE_ORDER, querier.getJSON()
						.toString());

		return processOrdersFromServer(sendStr);
	}

	public List<Order> retrieveOrders(int status, int count) {
		OrderQuerier querier = new OrderQuerier();
		// querier.setCusID(customer_account.getUname());
		querier.setStatus(status);
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_RETRIVE_ORDER, querier.getJSON()
						.toString());

		return processOrdersFromServer(sendStr);
	}

	public List<Order> retrieveChangedOrders() {
		String sendStr = CommunicationProtocol.construcSendingStr(
				customer_account.getId(), customer_account.getPasswd(),
				CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER, "");

		return processOrdersFromServer(sendStr);
	}

	/**
	 * Process and return orders from the response of the server in methods
	 * retrieveAllOrders(), retrieveOrders(), retrieveChangedOrders().
	 * 
	 * @param sendStr
	 * @return
	 */
	public List<Order> processOrdersFromServer(String sendStr) {
		List<Order> my_orders = new ArrayList<Order>();
		String responseStr = "";
		try {
			responseStr = internetClient
					.sendAndGet(sendStr, CONNECTION_TIMEOUT);
			System.out.println("response: " + responseStr);

			String supinfo = CommunicationProtocol
					.getSupinfoFromReceivedStr(responseStr);
			ListedJSONObj jobj = new ListedJSONObj();
			jobj.parseFromJSONObject(new JSONObject(supinfo));

			Iterator<JSONObject> it = jobj.iterator();
			while (it.hasNext()) {
				Order order = new Order("", "", "", -1,
						new ArrayList<OrderItem>());
				order.parseFromJSONObject(it.next());
				my_orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return my_orders;
	}

}
