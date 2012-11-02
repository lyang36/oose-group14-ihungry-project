package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * An order consists of Customer and Restaurant info together with an order list
 * @author group14
 *
 */

public class Order implements JSONHandler{
	public Order(){
		
	}
	
	public Order (String orderId, String customerId, String restaurantId, int status, ArrayList<OrderItem> orderList){
		
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		// TODO Auto-generated method stub
		
	}
}
