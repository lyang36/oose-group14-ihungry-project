package edu.jhu.cs.oose.group14.restaurant.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;


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
		
	public synchronized void setPendingOrders(List<Order> pendingOrders) {
		this.pendingOrders.addAll(pendingOrders);
		notifyUpdate();
	}

	//TODO: Need to handle modify senario
	public synchronized void addPendingOrder(Order pendingOrder) {
		pendingOrders.add(pendingOrder);
		notifyUpdate();
	}
	
	public synchronized void removePendingOrder(Order pendingOrder) {
		pendingOrders.remove(pendingOrder);
		notifyUpdate();
	}
	
	public Set<Order> getAcceptedOrders() {
		return Collections.unmodifiableSet(acceptedOrders);
	}

	public void setAcceptedOrders(List<Order> acceptedOrders) {
		this.acceptedOrders.addAll(acceptedOrders);
		notifyUpdate();
	}
	
	public void addAcceptedOrder(Order acceptedOrder) {
		acceptedOrders.add(acceptedOrder);
		notifyUpdate();
	}
	
	public void removeAcceptedOrder(Order acceptedOrder) {
		acceptedOrders.remove(acceptedOrder);
		notifyUpdate();
	}
	
	public Set<Order> getDeclinedOrders() {
		return Collections.unmodifiableSet(declinedOrders);
	}

	public void setDeclinedOrders(List<Order> declinedOrders) {
		this.declinedOrders.addAll(declinedOrders);
		notifyUpdate();
	}

	public void addDeclinedOrder(Order declinedOrder) {
		declinedOrders.add(declinedOrder);
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
	
	public void addOldOrder(Order oldOrder) {
		oldOrders.add(oldOrder);
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