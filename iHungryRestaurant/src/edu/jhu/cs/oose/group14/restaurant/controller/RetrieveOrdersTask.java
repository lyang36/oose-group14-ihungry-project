package edu.jhu.cs.oose.group14.restaurant.controller;

import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import edu.jhu.cs.oose.group14.restaurant.model.iHungryRestaurant;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

public class RetrieveOrdersTask extends TimerTask {

	private ihungryRestaurantModelInterface hungryRestaurantService; 
	
	public RetrieveOrdersTask(){
		this.hungryRestaurantService = new ihungryRestaurantModelImpl();
	}
	
	@Override
	public void run() {

		System.out.println("Thread running...");
		iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
		List<Order> orders = hungryRestaurantService.retrieveChangedOrders(hungryRestaurant.getAccountInfo().getId());
		System.out.println("uname = "+hungryRestaurant.getAccountInfo().getUname());
		System.out.println("passwd = "+hungryRestaurant.getAccountInfo().getPasswd());
		System.out.println("new size = "+orders.size());
		
		for (Iterator<Order> orderIterator = orders.iterator(); orderIterator.hasNext();) {
			Order order = orderIterator.next();
			
			// check the status of the order
			if(order.getStatus() == Order.STATUS_UNDERPROCING) {
				hungryRestaurant.addOrModifyPendingOrder(order);
			} else if(order.getStatus() == Order.STATUS_CANCELLED) {
				hungryRestaurant.removePendingOrder(order);
			}
			
		}
		
	}	
}
