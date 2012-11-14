package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;
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
	public static final String KEY_ORDERID = "Orderid";
	public static final String KEY_CUSTID = "Custid";
	public static final String KEY_RESTID = "Restid";
	public static final String KEY_STATUS = "Status";
	public static final String KEY_ARRAYSIZE = "Arraysize";
	
	private String orderId;
	private String custId;
	private String restId;
	private int status;
	private int arraySize;
	private List<OrderItem> orderItems;
	
	public Order (String orderId, String customerId, String restaurantId, int status, List<OrderItem> orderitems){
		this.orderId = orderId;
		this.custId = customerId;
		this.restId = restaurantId;
		this.status = status;
		this.orderItems = orderitems;
		
		this.arraySize = orderitems.size();
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
	
	public void printOrderItems(){
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem item_t = (OrderItem)orderItems.get(i);
			System.out.println("[Order]"+item_t.getItem().toString()+" "+item_t.getQuantity());
		}
	}
	
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
			
			this.orderItems = new ArrayList<OrderItem>();
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
}
