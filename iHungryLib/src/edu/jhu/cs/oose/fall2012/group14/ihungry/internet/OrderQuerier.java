package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.project.group14.ihungry.model.JSONHandler;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;


/**
 * this class is used for quey orders
 * <p> If any field is not applicable, set it to be null for object or -1 for integer 
 * @author lyang
 *
 */
public class OrderQuerier implements JSONHandler<OrderQuerier>{
	public static final String KEY_START_INDEX = "startind";
	public static final String KEY_END_INDEX = "endind";
	
	private String orderID = null;
	private String customerID = null;
	private String restaurantID = null;
	private int status	= -1;
	private int startIndex = 0;
	private int endIndex = 65535;
	
	
	public OrderQuerier(){
		
	}
	
	/**
	 * If any field is not applicable, set it to be null for object or -1 for integer 
	 * @param od
	 */
	public void setOrderID(String od){
		this.orderID = od;
	}
	
	/**
	 * If any field is not applicable, set it to be null for object or -1 for integer 
	 * @param od
	 */
	public void setCusID(String cd){
		this.customerID = cd;
	}
	
	/**
	 * If any field is not applicable, set it to be null for object or -1 for integer 
	 * @param od
	 */
	public void setRestaurantID(String rd){
		this.restaurantID = rd;
	}
	
	/**
	 * set the status
	 * @param stat
	 */
	public void setStatus(int stat)
	{
		this.status = stat;
	}
	
	/**
	 * set the startindex and endindex
	 * @param startindex
	 * @param endindex
	 */
	public void setStartEndIndex(int startindex, int endindex){
		this.startIndex = startindex;
		this.endIndex = endindex;
	}
	
	public String getOrderId(){
		return this.orderID;
	}
	
	public String getResId(){
		return this.restaurantID;
	}
	
	public String getCusId(){
		return this.customerID;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public int getStartIndex(){
		return this.startIndex;
	}
	
	public int getEndIndex(){
		return this.endIndex;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_START_INDEX, this.startIndex);
			
			retObj.put(KEY_END_INDEX, this.endIndex);
			
			if(orderID != null)
				retObj.put(Order.KEY_ORDERID, this.orderID);
			
			if(customerID != null)
				retObj.put(Order.KEY_CUSTID, this.customerID);
			
			if(restaurantID != null)
				retObj.put(Order.KEY_RESTID, this.restaurantID);
			
			//if(status != -1)
				retObj.put(Order.KEY_STATUS, this.status);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public OrderQuerier parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.status = jsonobj.getInt(Order.KEY_STATUS);
			this.startIndex = jsonobj.getInt(KEY_START_INDEX);
			this.endIndex = jsonobj.getInt(KEY_END_INDEX);
			this.orderID = jsonobj.getString(Order.KEY_ORDERID);
			this.customerID = jsonobj.getString(Order.KEY_CUSTID);
			this.restaurantID = jsonobj.getString(Order.KEY_RESTID);
		} catch (JSONException e) {
		}
		return this;
	}
	
}
