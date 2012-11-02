package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.awt.Image;

/**
 * One object in each row of the list.
 * @author SuNFloWer
 *
 */
public class ListMenuItem {
	String title; 
	double rating;
	double price;
	int quantity;
//	Image thumb_image;

	public ListMenuItem(String title_in, double rating_in, double price_in, int quantity_in){
		this.title = title_in;
		this.rating = rating_in;
		this.price = price_in;
		this.quantity = quantity_in;
	}
	
	public void setTitle(String title_in){
		this.title = title_in;
	}
	
	public void setRating(double rating_in){
		this.rating = rating_in;
	}
	
	public void setPrice(double price_in){
		this.price = price_in;
	}
	
	public void setQuantity(int quantity_in){
		this.quantity = quantity_in;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public double getRating(){
		return this.rating;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public int getQuantity(){
		return this.quantity;
	}
	
	public void addQuantity(int no){
		this.quantity += no;
	}
	
	public void minusQuantity(int no){
		if(this.quantity - no >= 0){
			this.quantity -= no;
		}
	}

}
