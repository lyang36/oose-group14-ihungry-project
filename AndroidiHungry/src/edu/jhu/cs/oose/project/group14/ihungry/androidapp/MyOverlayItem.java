package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * A customized overlay item with one extra attribute: restaurant ID. <p>
 * Three other attributes are from parent: geopoint, title and snippet.
 * @author SuNFloWer
 *
 */
public class MyOverlayItem extends OverlayItem {

	//protected String mImageURL;
	/**
	 * restaurantID: the added extra attribute.
	 */
	private String restaurantID;
	private String restaurantPrimPhone;
	
	/**
	 * MyOverlayItem constructor.
	 * @param point
	 * @param title
	 * @param snippet
	 * @param restID
	 */
	public MyOverlayItem(GeoPoint point, String title, String snippet, String restID, String primphone) {
		super(point, title, snippet);
		restaurantID = restID;
		this.restaurantPrimPhone = primphone;
	}

	/**
	 * Get restaurant ID.
	 * @return
	 */
	public String getRestaurantID() {
		return this.restaurantID;
	}
	
	/**
	 * Get restaurant primary phone number;
	 * @return
	 */
	public String getRestaurantPhone() {
		return this.restaurantPrimPhone;
	}

	/**
	 * Set restaurant ID.
	 * @param restID
	 */
	public void setRestaurantID(String restID) {
		this.restaurantID = restID;
	}
	
}
