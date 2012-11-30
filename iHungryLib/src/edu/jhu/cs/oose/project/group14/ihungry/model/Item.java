package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represent a single item in the Menu
 * 
 * @author group14
 * 
 */
@SuppressWarnings("serial")
public class Item implements JSONHandler<Item>, Serializable{
	/**
	 * JSON key.
	 */
	public static final String KEY_ITEMID = "Itemid";
	/**
	 * JSON key.
	 */
	public static final String KEY_ITEMNAME = "Itemname";
	/**
	 * JSON key.
	 */
	public static final String KEY_PRICE = "Price";
	/**
	 * JSON key.
	 */
	public static final String KEY_RATING = "Rating";
	/**
	 * JSON key.
	 */
	public static final String KEY_ALBUM = "Album";
	
	public static final String KEY_DESCRIPTION = "Itemdescription";
	
	private String itemID;
	private String itemName;
	private String itemDescription;
	private double price;
	private Rating rating;
	private Album album;

/*	public Item(String itemId, String itemName, double price, Rating rating ) {
		this.itemID = itemId;
		this.itemName = itemName;
		this.price = price;
		this.rating = rating;
	}
*/	
	public Item(){
		this.itemID = "1";
		this.album = new Album();
		this.itemName = "item";
		this.itemDescription = "new item";
		this.price = 12.33;
		this.rating = new Rating();
		this.itemDescription = " ";
	}
	
	public Item(String itemId, String itemName, double price, Rating rating, Album album ) {
		this.itemID = itemId;
		this.itemName = itemName;
		this.price = price;
		this.rating = rating;
		this.album = album;
		this.itemDescription = " ";
	}

	
	public Item(String itemId, String itemName, String itemDescription,double price, Rating rating, Album album ) {
		this.itemID = itemId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.price = price;
		this.rating = rating;
		this.album = album;
	}

	
	public String getItemId() {
		return this.itemID;
	}

	public String getItemName() {
		return this.itemName;
	}
	
	public String getItemDescription() {
		return this.itemDescription;
	}

	public double getItemPrice() {
		return this.price;
	}
	
	public Album getItemAlbum() {
		return this.album;
	}
	
	public Rating getItemRating() {
		return this.rating;
	}

	public void setItemId(String itemID) {
		this.itemID = itemID;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemPrice(double price) {
		this.price = price;
	}
	
	public void setItemRating(Rating rating) {
		this.rating = rating;
	}
	public void setItemAlbum(Album album) {
		this.album = album;
	}
	
	@Override
	public String toString(){
		return this.itemID+" "+this.itemName+" "+this.itemDescription+" "+this.price+" "+this.rating.getRating();
	}

	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_ITEMID, this.itemID);
			retObj.put(KEY_ITEMNAME, this.itemName);
			retObj.put(KEY_DESCRIPTION, this.itemDescription);
			retObj.put(KEY_PRICE, this.price);
			retObj.put(KEY_RATING, this.rating.getJSON());
			retObj.put(KEY_ALBUM, this.album.getJSON());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public Item parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.itemID = jsonobj.getString(KEY_ITEMID);
			this.itemName = jsonobj.getString(KEY_ITEMNAME);
			this.itemDescription = jsonobj.getString(KEY_DESCRIPTION);
			this.price = jsonobj.getDouble(KEY_PRICE);
			this.rating = new Rating();
			this.rating.parseFromJSONObject(jsonobj.getJSONObject(KEY_RATING));
			this.album = new Album(null);
			this.album.parseFromJSONObject(jsonobj.getJSONObject(KEY_ALBUM));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		return this;
	}
}