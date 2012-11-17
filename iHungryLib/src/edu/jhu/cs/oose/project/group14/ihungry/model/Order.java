package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An order consists of Customer and Restaurant info together with an order list
 * @author group14
 *
 */

@SuppressWarnings("serial")
public class Order implements JSONHandler<Order>, Serializable{
	/**
	 * JSON Key
	 */
	public static final String KEY_ORDERID = "Orderid";
	
	/**
	 * JSON Key
	 */
	public static final String KEY_CUSTID = "Custid";
	
	/**
	 * JSON Key
	 */
	public static final String KEY_RESTID = "Restid";
	
	/**
	 * JSON Key
	 */
	public static final String KEY_STATUS = "Status";
	
	
	/**
	 * JSON Key -- time in miliseconds
	 */
	public static final String KEY_TIME = "Time";
	
	/**
	 * JSON Key
	 */
	public static final String KEY_ARRAYSIZE = "Arraysize";
	
	/**
	 * JSON Key
	 */
	public static final String IS_NEW_TO_RES = "IsNewToRes";
	
	/**
	 * JSON KEY
	 */
	public static final String IS_NEW_TO_CUS = "IsNewToCus";
	
	/**
	 * order status under processing (a new order submitted)
	 */
	public static final int STATUS_UNDERPROCING = 0x01;
	
	/**
	 * order is confirmed by the restaurant
	 */
	public static final int STATUS_CONFIRMED 	= 0x02;
	
	
	/**
	 * order is rejected by the restaurant
	 */
	public static final int STATUS_REJECTED 	= 0x03;
	
	
	/**
	 * order is finished by the restaurant
	 */
	public static final int STATUS_FINISHED 	= 0x04;
	
	/**
	 * order is cancelled by the customer
	 */
	public static final int STATUS_CANCELLED 	= 0x05;
	
	
	private String orderId;
	private String custId;
	private String restId;
	private boolean isNewToRes= true;
	private boolean isNewToCus = false;
	private int status;
	private int arraySize;
	private List<OrderItem> orderItems;
	private long timemilisecs;
	
	
	public Order(JSONObject jobj){
		this.parseFromJSONObject(jobj);
	}
	
	public Order (String orderId, String customerId, String restaurantId, int status, List<OrderItem> orderitems){
		this.orderId = orderId;
		this.custId = customerId;
		this.restId = restaurantId;
		this.status = status;
		this.orderItems = orderitems;
		this.arraySize = orderitems.size();
		
		/**
		 * create time stamp
		 */
		Calendar rightNow = Calendar.getInstance();
		long militime = rightNow.getTimeInMillis();
		this.timemilisecs = militime;
	}
	
	/**
	 * @return the time of this object created in milisecond
	 */
	public long getTime(){
		return this.timemilisecs;
	}
	
	public String getOrderID(){
		return this.orderId;
	}
	
	public String getCustID(){
		return this.custId;
	}
	
	public String getRestID(){
		return this.restId;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public List<OrderItem> getOrderItems(){
		return this.orderItems;
	}
	
	/**
	 * test function, will be removed
	 */
	public void printOrderItems(){
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem item_t = (OrderItem)orderItems.get(i);
			System.out.println("[Order]"+item_t.getItem().toString()+" "+item_t.getQuantity());
		}
	}
	
	/**
	 * 
	 * @return get total price
	 */
	public double getTotalPrice(){
		double totalPrice = 0;
		for(int i=0; i<orderItems.size(); i++){
			OrderItem item = orderItems.get(i);
			totalPrice += item.getItem().getItemPrice() * item.getQuantity();
		}
		/* Round to 2 points double */
		int temp_price = (int)java.lang.Math.round(totalPrice * 100);
		return (double)(temp_price )/ 100;
	}
	

	@Override
	public JSONObject getJSON() {
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put(KEY_ORDERID, this.orderId);
			jsonobj.put(KEY_CUSTID, this.custId);
			jsonobj.put(KEY_RESTID, this.restId);
			jsonobj.put(KEY_STATUS, this.status);
			jsonobj.put(KEY_ARRAYSIZE, this.arraySize);
			jsonobj.put(IS_NEW_TO_CUS, isNewToCus);
			jsonobj.put(IS_NEW_TO_RES, isNewToRes);
			jsonobj.put(KEY_TIME, this.timemilisecs);
			
			for(int i=0; i<this.arraySize; i++){
				jsonobj.put(i+"", this.orderItems.get(i).getJSON());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	@Override
	public Order parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.orderId = jsonobj.getString(KEY_ORDERID);
			this.custId = jsonobj.getString(KEY_CUSTID);
			this.restId = jsonobj.getString(KEY_RESTID);
			this.status = jsonobj.getInt(KEY_STATUS);
			this.arraySize = jsonobj.getInt(KEY_ARRAYSIZE);
			this.isNewToCus = jsonobj.getBoolean(IS_NEW_TO_CUS);
			this.isNewToRes = jsonobj.getBoolean(IS_NEW_TO_RES);
			this.orderItems = new ArrayList<OrderItem>();
			this.timemilisecs = jsonobj.getLong(KEY_TIME);
			for(int i=0; i<arraySize; i++){
				OrderItem orderItem = new OrderItem();
				orderItem.parseFromJSONObject(jsonobj.getJSONObject(i+""));
				this.orderItems.add(orderItem);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return this;
		
	}
	
	/**
	 * server used function: check whether this order is new to the restaurant
	 * @return
	 */
	public boolean checkIsNewToRes(){
		return isNewToRes;
	}
	
	/**
	 * server used function: check whether this order is new to the customer
	 * @return
	 */
	public boolean checkIsNewToCus(){
		return isNewToCus;
	}
	
	/**
	 * server used function: flip the to Res status
	 */
	public void flipToResStatus(){
		isNewToRes =!isNewToRes;
	}
	
	/**
	 * set the to Restaurant status
	 * @param bool
	 */
	public void setToResStatus(boolean st){
		isNewToRes = st;
	}
	
	/**
	 * server used function: flip the to Cus status
	 */
	public void flipToCusStatus(){
		isNewToCus =!isNewToCus;
	}
	
	/**
	 * set the to Customer status
	 * @param bool
	 */
	public void setToCusStatus(boolean st){
		isNewToCus = st;
	}
}
