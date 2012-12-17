package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetClient;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.ListedJSONObj;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.OrderQuerier;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public class ihungryRestaurantModelImpl implements ihungryRestaurantModelInterface {
	
	private static final int CONNECTIONTIMEOUT = 5000;
	private InternetClient internetClient;	
		
	public ihungryRestaurantModelImpl() {
		internetClient = new InternetClient();
	}
	
	
	public boolean loginCheck( String username, String password){
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(username), MD5.getMd5(password),
				         CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED, "");
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Occured");
		}
		
		if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.FALSE) ){
			return false;
		} 
		else if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.TRUE) ){
			return true;
			
		}	
		return false;
	}

	
	public String attemptLogin( String username, String password){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(username), MD5.getMd5(password),
				         CommunicationProtocol.BUSI_LOGIN, "");
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.LOGIN_SUCCESS) ){
			return CommunicationProtocol.getSupinfoFromReceivedStr( responseStr );
		} else if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.LOGIN_ERROR) ){
			return CommunicationProtocol.getSupinfoFromReceivedStr( responseStr );
		}
		
		return "";
	}
	
	
	public boolean signupForNewUser(Restaurant restaurant){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(restaurant.getAccountInfo().getUname()), MD5.getMd5(restaurant.getAccountInfo().getPasswd()),
				         CommunicationProtocol.BUSI_SIGNUP, restaurant.getJSON().toString());
		String responseStr = "";
		
		try
		{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_SUCCEEDED) )
		{			
			return true;
		} 
		else if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_FAILED) )
		{
			return false;
		}
		
		return false;
	}
	

	public boolean updateMenu(AccountInfo accinfo, Menu menu){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(accinfo.getUname()), accinfo.getPasswd(),
				         CommunicationProtocol.BUSI_UPDATE_MENU, menu.getJSON().toString());
		String responseStr = "";
		
		try
		{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_SUCCEEDED) )
		{
			return true;
		} 
		else if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_FAILED) )
		{
			return false;
		}
		
		return false;
		
	}
	
	
	public boolean updateContact(AccountInfo accInfo, ContactInfo ci){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(accInfo.getUname()), accInfo.getPasswd(),
				         CommunicationProtocol.BUSI_UPDATE_CONTACT, ci.getJSON().toString());
		String responseStr = "";
		
		try
		{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_SUCCEEDED) )
		{
			return true;
		} 
		else if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_FAILED) )
		{
			return false;
		}
		
		return false;
		
	}
	
	
	public String getCustContact(AccountInfo accInfo, Order ord){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(accInfo.getUname()), accInfo.getPasswd(),
		         CommunicationProtocol.BUSI_GET_CUS_CONTACT, ord.getJSON().toString());
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_SUCCEEDED) ){
			return CommunicationProtocol.getSupinfoFromReceivedStr( responseStr );
		} else if( CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_FAILED) ){
			return CommunicationProtocol.getSupinfoFromReceivedStr( responseStr );
		}
		
		return "";
		
	}
	
	
	public boolean updateOrder(AccountInfo accInfo,Order order){
		
		String sendStr = CommunicationProtocol.construcSendingStr(MD5.getNameMd5(accInfo.getUname()), accInfo.getPasswd(),
				         CommunicationProtocol.BUSI_PROCESS_ORDER, order.getJSON().toString());
		String responseStr = "";
		
		try
		{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_SUCCEEDED) )
		{
			return true;
		} 
		else if(CommunicationProtocol.getRequestFromReceivedStr( responseStr ).equals(CommunicationProtocol.PROCESS_FAILED) )
		{
			return false;
		}
		
		return false;

	}
	
	
	public List<Order> retreiveOrders(String restId, int status, int count){
		
		List<Order> orders = new ArrayList<Order>();
		
		//create an orderquerier object with the count and status of the orders to be retrieved
		OrderQuerier querier = new OrderQuerier();
		querier.setRestaurantID(restId);
		querier.setStatus(status);
		querier.setStartEndIndex(0, count);
		
		iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
		String sendStr = CommunicationProtocol.construcSendingStr(hungryRestaurant.getAccountInfo().getId(), hungryRestaurant.getAccountInfo().getPasswd(),
				         CommunicationProtocol.BUSI_RETRIVE_ORDERS, querier.getJSON().toString());
		String responseStr = "";
		try{
			responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Exception Occured");
		}
		
		//parse from the supinfo to a list of orders
		JSONObject supinfoObj = new JSONObject();
		String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
		
		try {
			if(supinfo!=null && supinfo.length() > 0)
				supinfoObj = new JSONObject(supinfo);
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		
		ListedJSONObj listJSONObj = new ListedJSONObj();
		listJSONObj.parseFromJSONObject(supinfoObj);
		
		for(Iterator<JSONObject> i = listJSONObj.iterator(); i.hasNext();) {
			JSONObject orderJSONObject = (JSONObject) i.next();
			Order order = new Order(orderJSONObject);
			orders.add(order);			
		}
		
		return orders;
	}
	
	
	public List<Order> retrieveChangedOrders(String restId){
		
		List<Order> orders = new ArrayList<Order>();
		OrderQuerier querier = new OrderQuerier();

		iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
		String sendStr = CommunicationProtocol.construcSendingStr(hungryRestaurant.getAccountInfo().getId(), hungryRestaurant.getAccountInfo().getPasswd(),
		                 CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS, querier.getJSON().toString());
		String responseStr = "";
		
		try{
		responseStr = internetClient.sendAndGet(sendStr, CONNECTIONTIMEOUT);
		}
		catch (Exception e) {
		System.out.println("Exception Occured in send and get");
		}
		
		//parse from the supinfo to a list of orders
		JSONObject supinfoObj = new JSONObject();
		String supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(responseStr);
		
		try {
		if(supinfo!=null && supinfo.length() > 0)
		supinfoObj = new JSONObject(supinfo);
		} 
		catch (JSONException e2) {
		e2.printStackTrace();
		}
		
		ListedJSONObj listJSONObj = new ListedJSONObj();
		listJSONObj.parseFromJSONObject(supinfoObj);
		
		for(Iterator<JSONObject> i = listJSONObj.iterator(); i.hasNext();) {
		JSONObject orderJSONObject = (JSONObject) i.next();
		Order order = new Order(orderJSONObject);
		orders.add(order);	
		}
		return orders;
		
	}
	
	
}
