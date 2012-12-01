package edu.jhu.cs.oose.group14.restaurant.controller;

import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import edu.jhu.cs.oose.group14.restaurant.model.iHungryRestaurant;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelImpl;
import edu.jhu.cs.oose.group14.restaurant.model.ihungryRestaurantModelInterface;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;


/**
 * Specifies the workload for the daemon thread to connect to the server every 
 * 5 seconds and fetch the new/changed orders.
 *  
 * @author parkavi
 *
 */

public class RetrieveOrdersTask extends TimerTask {

	private ihungryRestaurantModelInterface hungryRestaurantService; 
	
	public RetrieveOrdersTask(){
		this.hungryRestaurantService = new ihungryRestaurantModelImpl();
	}
	
	@Override
	public void run() {

		//System.out.println("Thread running...");
		iHungryRestaurant hungryRestaurant = iHungryRestaurant.getInstance();
		List<Order> orders = hungryRestaurantService.retrieveChangedOrders(hungryRestaurant.getAccountInfo().getId());
		
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
