package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetClient;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public class ihungryRestaurantModelImpl implements ihungryRestaurantModelInterface {
	static private final int CONNECTIONTIMEOUT = 5000;
	private InternetClient internetClient;
	
	public ihungryRestaurantModelImpl() {
		internetClient = new InternetClient();
	}
	
	public String getResponseFromServer() {
		
		String sendStr = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME, CommunicationProtocol.FB_SIGN_PASSWD, 
				CommunicationProtocol.BUSI_LOGIN, "{\"name\": \"try\"}");

		String responseFromServer = "";
		try {
			responseFromServer = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseFromServer;
		
	}
	
	public boolean loginCheck( String username, String password){
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED, "");
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

	
	public boolean attemptLogin( String username, String password){
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(username), MD5.getMd5(password),
				CommunicationProtocol.BUSI_LOGIN, "");
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.LOGIN_SUCCESS) ){
			return true;
		} else if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.LOGIN_ERROR) ){
			return false;
		}
		
		return false;
	}
	
	public boolean signupForNewUser( Restaurant restaurant ){
		return true;
	}
	
	public Restaurant getRestaurantInfo( String username, String password){
		/*Item i1 = new Item("I001","Pizza",4.45,new Rating(0,0),new Album());
		Item i2 = new Item("I002","Pizza Big",7.30,new Rating(0,0),new Album());
		ArrayList<Item> itemList = new ArrayList<Item>();
		itemList.add(i1);
		itemList.add(i2);
		Menu menu = new Menu("R0001",itemList);
		Restaurant restInfo = new Restaurant(menu,new Album());
		AccountInfo aInfo = new AccountInfo("group14", "12345");
		ContactInfo cInfo = new ContactInfo("Group14", new LocationInfo("Johns Hopkins University"), "111-111-1111", "222-222-2222", "group14@jhu.edu","1980-01-01", new Icon());
		
		restInfo.setAccountInfo(aInfo);
		restInfo.setContactInfo(cInfo);
		String responseFromServer = restInfo.getJSON().toString();
		
		/* -----------> transmitting from server ----------->  */
		
		/* <----------- received by client <-----------  */
		
		return null;
	}
	
	public List<Order> retreiveOrders(String restId, int status, int count){
		return null;
	}
	
	public List<Order> retrieveChangedOrders(String restId){
		return null;
	}
}
