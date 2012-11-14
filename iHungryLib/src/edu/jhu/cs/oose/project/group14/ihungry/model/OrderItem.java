package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An orderItem contains an item and its quantity (like 2 pizzas)
 * @author group14
 *
 */

@SuppressWarnings("serial")
public class OrderItem implements JSONHandler<OrderItem>, Serializable{
	/**
	 * JSON key.
	 */
	public static final String KEY_ITEM = "Item";
	/**
	 * JSON key.
	 */
	public static final String KEY_QUANTITY = "Quantity";

	private Item item;
	private int quantity;
	
	public OrderItem(){}
	
	public OrderItem(Item item, int itemQuantity){
		this.item = item;
		this.quantity = itemQuantity;
	}
	
	public Item getItem(){
		return this.item;
	}
	
	public int getQuantity(){
		return this.quantity;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	public void setQuantity(int quantity_in){
		this.quantity = quantity_in;
	}
	
	/**
	 * Add quantity by a number: no.
	 * @param no
	 */
	public void addQuantity(int no){
		this.quantity += no;
	}
	
	/**
	 * Minus quantity by a number: no.
	 * @param no
	 */
	public void minusQuantity(int no){
		if(this.quantity - no >= 0){
			this.quantity -= no;
		}
	}

	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_ITEM, this.item.getJSON());
			retObj.put(KEY_QUANTITY, this.quantity);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public OrderItem parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.item = new Item();
			this.item.parseFromJSONObject(jsonobj.getJSONObject(KEY_ITEM));
			this.quantity = jsonobj.getInt(KEY_QUANTITY);

		} catch (JSONException e) {
			e.printStackTrace();
		}	
		
		return this;
		
	} 
}
