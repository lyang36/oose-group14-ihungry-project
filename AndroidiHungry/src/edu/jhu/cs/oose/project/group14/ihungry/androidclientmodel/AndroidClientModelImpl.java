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
		Log.v("AndroidCientModelImpl", "Initial");

		internetClient = new InternetClient();
	}

	public String getResponseFromServerT() {
		Log.v("getResponseFromServerT", "executed");
		Log.v("[Connect to]", CommunicationProtocol.SERVER_IP_ADDRESS+" "+CommunicationProtocol.SERVER_PORT);

		String a = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME, CommunicationProtocol.FB_SIGN_PASSWD, 
				CommunicationProtocol.BUSI_LOGIN, "{\"name\": \"try\"}");
		Log.v("[String a]",a);

		String responseFromServer = "";
		try {
			responseFromServer = internetClient.sendAndGet(a, CONNECTIONTIMEOUT);
			
			if(CommunicationProtocol.getRequestFromReceivedStr( responseFromServer ).equals(CommunicationProtocol.NO_SUCH_COMMAND)){
				Log.v("[Message]","No Such Command");
				return "No Such Command";
			}

		} catch (Exception e) {
			Log.e("[AndroidClientModelImpl: getResponseFromServerT]","Connection Error");
		//	e.printStackTrace();
		}

		return responseFromServer;
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
