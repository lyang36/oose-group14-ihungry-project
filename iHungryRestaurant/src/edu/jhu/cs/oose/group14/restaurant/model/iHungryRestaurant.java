package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.*;


import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

public class iHungryRestaurant extends Observable {

	private static iHungryRestaurant restaurant = new iHungryRestaurant();

	private AccountInfo accountInfo;
	private Set<Order> pendingOrders = new TreeSet<Order>();	
	private Set<Order> acceptedOrders = new TreeSet<Order>();
	private Set<Order> declinedOrders = new TreeSet<Order>();
	private Set<Order> oldOrders = new TreeSet<Order>();
	
	public static iHungryRestaurant getInstance() {
		return restaurant;
	}
	
	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public synchronized Set<Order> getPendingOrders() {
		return Collections.unmodifiableSet(pendingOrders);
	}
	
	public synchronized Order getPendingOrder(Order order) {
		TreeSet<Order> newPendingOrders = (TreeSet<Order>) pendingOrders;
		order = newPendingOrders.ceiling(order);
		return order;
		
	}
		
	public synchronized void setPendingOrders(List<Order> pendingOrders) {
		this.pendingOrders.addAll(pendingOrders);
		notifyUpdate();
	}

	public synchronized void addOrModifyPendingOrder(Order pendingOrder) {
		if(pendingOrders.contains(pendingOrder))
		{
		// remove the old order and add the new order
			pendingOrders.remove(pendingOrder);
			pendingOrders.add(pendingOrder);
		} 
		else 
		{
			pendingOrders.add(pendingOrder);
		}
		notifyUpdate();
	}
	
	public synchronized void removePendingOrder(Order pendingOrder) {
		pendingOrders.remove(pendingOrder);
		notifyUpdate();
	}
	
	public Set<Order> getAcceptedOrders() {
		return Collections.unmodifiableSet(acceptedOrders);
	}
	
	public synchronized Order getAcceptedOrder(Order order) {
		
		TreeSet<Order> newAcceptedOrders = (TreeSet<Order>) acceptedOrders;
		order = newAcceptedOrders.ceiling(order);
		return order;
		
	}

	public void setAcceptedOrders(List<Order> acceptedOrders) {
		this.acceptedOrders.addAll(acceptedOrders);
		notifyUpdate();
	}
	
	public synchronized void addOrModifyAcceptedOrder(Order acceptedOrder) {
		
		if(acceptedOrders.contains(acceptedOrder))
		{
		// removes the old order and add the new order
		acceptedOrders.remove(acceptedOrder);
		acceptedOrders.add(acceptedOrder);
		} else {
			acceptedOrders.add(acceptedOrder);
		}
		notifyUpdate();
		}
	
	public void removeAcceptedOrder(Order acceptedOrder) {
		acceptedOrders.remove(acceptedOrder);
		notifyUpdate();
	}
	
	public Set<Order> getDeclinedOrders() {
		return Collections.unmodifiableSet(declinedOrders);
	}
	
	public synchronized Order getDeclinedOrder(Order order) {
		TreeSet<Order> newDeclinedOrders = (TreeSet<Order>) declinedOrders;
		order = newDeclinedOrders.ceiling(order);
		return order;
		
	}

	public void setDeclinedOrders(List<Order> declinedOrders) {
		this.declinedOrders.addAll(declinedOrders);
		notifyUpdate();
	}

	
	public synchronized void addOrModifyDeclinedOrder(Order declinedOrder) {
		if(declinedOrders.contains(declinedOrder))
		{
		// removes the old order and add the new order
		declinedOrders.remove(declinedOrder);
		declinedOrders.add(declinedOrder);
		} else {
			declinedOrders.add(declinedOrder);
		}
		notifyUpdate();
		}
	
	
	public void removeDeclinedOrder(Order declinedOrder) {
		declinedOrders.remove(declinedOrder);
		notifyUpdate();
	}
	
	public Set<Order> getOldOrders() {
		return Collections.unmodifiableSet(oldOrders);
	}

	public void setOldOrders(List<Order> oldOrders) {
		this.oldOrders.addAll(oldOrders);
		notifyUpdate();
	}
	
	public synchronized void addOrModifyOrderHistory(Order oldOrder) {
		if(oldOrders.contains(oldOrder))
		{
		// removes the old order and add the new order
		oldOrders.remove(oldOrder);
		oldOrders.add(oldOrder);
		} else {
			oldOrders.add(oldOrder);
		}
		notifyUpdate();
		}
	
	public void removeOldOrder(Order oldOrder) {
		oldOrders.remove(oldOrder);
		notifyUpdate();
	}
	
	private void notifyUpdate() {
		setChanged();
		notifyObservers();
	}
}