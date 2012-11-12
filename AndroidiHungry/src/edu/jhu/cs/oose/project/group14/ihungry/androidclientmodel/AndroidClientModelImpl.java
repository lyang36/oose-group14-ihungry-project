package edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel;

import java.util.ArrayList;

import android.util.Log;

import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
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

	public ArrayList<Restaurant> retrieveRestaurants(LocationInfo loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Menu retrieveMenu(Restaurant rest) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createOrder(Customer cust, Restaurant rest,
			ArrayList<OrderItem> order) {
		// TODO Auto-generated method stub

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
