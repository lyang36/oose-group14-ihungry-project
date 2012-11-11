package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

@SuppressWarnings("serial")
public class Order_Test implements Serializable{
	String restId;
	ArrayList<ListMenuItem> orderItems; 
	
	public Order_Test(String restID, ArrayList<ListMenuItem> orderitems_in){
		this.restId = restID;
		this.orderItems = orderitems_in;
	}
	
	public String getrestID(){
		return this.restId;
	}
	
	public ArrayList<ListMenuItem> getOrderItems(){
		return this.orderItems;
	}
	
	public void printOrderItems(){
		for (int i = 0; i < orderItems.size(); i++) {
			ListMenuItem item_t = (ListMenuItem)orderItems.get(i);
			Log.v("[Order]",
					item_t.getTitle()+" "+item_t.getQuantity() + "");
		}
	}
	
	public double getTotalPrice(){
		double totalPrice = 0;
		for(int i=0; i<orderItems.size(); i++){
			ListMenuItem item = orderItems.get(i);
			totalPrice += item.getPrice() * item.getQuantity();
		}
		/* Round to 2 points double */
		int temp_price = (int)java.lang.Math.round(totalPrice * 100);
		return (double)(temp_price )/ 100;
	}
}
