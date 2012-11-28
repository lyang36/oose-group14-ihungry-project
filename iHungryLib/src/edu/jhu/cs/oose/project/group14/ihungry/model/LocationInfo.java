package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The GPS info class containing latitude, longitude, address
 * 
 * @author Group 14
 *
 */

public class LocationInfo implements JSONHandler<LocationInfo>{
	/**
	 * JSON key.
	 */
	public static final String KEY_STRING_ADDRESS = "str_address";
	/**
	 * JSON key.
	 */
	public static final String KEY_LATITUDE = "latitude";
	/**
	 * JSON key.
	 */
	public static final String KEY_LONGITUDE = "longitude";
	
	
	private long latitude;
	private long longitude;
	private String address;
	
	/**
	 * use the double constructor
	 * @param lat
	 * @param lon
	 */
	public LocationInfo( double lat, double lon){
		this((long) (lat * 1e6), (long) (lon * 1e6));
	}
	
	public LocationInfo( long lat, long lon){
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public LocationInfo( String add){
		this.address = add;
	}
	
	public LocationInfo( String add, long lat, long lon){
		this(add);
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	/**
	 * Get the Latitude info
	 * @return
	 */
	public long getLatitude(){
		return latitude;
	}
	
	/**
	 * Get the Longitude info
	 * @return
	 */
	public long getLongitude(){
		return longitude;
	}
	
	
	public void setLatitude(long lat){
		this.latitude = lat;
	}
	
	public void setLongitude(long lon){
		this.longitude = lon;
	}
	

	@Override
	public JSONObject getJSON() {
		JSONObject jso = new JSONObject();
		try {
			jso.put(KEY_STRING_ADDRESS, address);
			jso.put(KEY_LATITUDE, latitude);
			jso.put(KEY_LONGITUDE, longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jso;
	}

	@Override
	public LocationInfo parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.address = jsonobj.getString(KEY_STRING_ADDRESS);
			this.latitude = jsonobj.getLong(KEY_LATITUDE);
			this.longitude = jsonobj.getLong(KEY_LONGITUDE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
}
