package edu.jhu.cs.oose.group14.restaurant.controller;

import java.util.*;

import edu.jhu.cs.oose.group14.restaurant.gui.ihungryRestaurantGui;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

public class OrderRunnable implements Runnable{
	
	private ihungryRestaurantController c;
	private List<Order> orders = new ArrayList<Order>();
	private AccountInfo accinfo = null;
	
	public OrderRunnable(ihungryRestaurantController c, AccountInfo accinfo)
	{
		this.c = c;
		this.accinfo = accinfo;
	}
	
	public void run()
	{
		System.out.println("into run method");
		orders = c.getModel().retreiveOrders(accinfo.getId(), 0x01, 10);
		System.out.println("Orders size = " + orders.size());
		//List<Order> orders = c.getOrders();
		//orders.removeAll(c.getOrders());
		orders.addAll(this.orders);
		//c.setCurrentOrders();
		
		
	}
	
	
}