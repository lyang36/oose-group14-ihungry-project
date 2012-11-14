package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Rating class just include an double value indicating the score
 * @author group14
 *
 */

@SuppressWarnings("serial")
public class Rating implements JSONHandler<Rating>, Serializable{
	/**
	 * JSON key.
	 */
	public static final String KEY_RATING = "Rating";
	/**
	 * JSON key.
	 */
	public static final String KEY_NUMOFPEOPLE = "NumOfPeople";
	
	private double rating = 0.0;
	private int numOfPeople = 0;
	
	public Rating(){
		
	}
	
	public Rating(double rating, int numOfPeople){
		this.rating = rating;
		this.numOfPeople = numOfPeople;
	}
	
	public int getNumOfPeople(){
		return numOfPeople;
	}
	
	public void setNumOfPeople(int numOfPeople){
		this.numOfPeople = numOfPeople;
	}
	
	public double getRating(){
		return rating;
	}
	
	/**
	 * Update the rating with a new rating.
	 * @param rating
	 */
	public void updateRating(double rating){
		//weighted average
		this.rating = (this.rating*numOfPeople + rating)/(numOfPeople+1);
		//now increment the number of people who have given reviews
		numOfPeople = numOfPeople + 1;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_RATING, Double.toString(rating));
			retObj.put(KEY_NUMOFPEOPLE, Integer.toString(this.numOfPeople));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public Rating parseFromJSONObject(JSONObject jsonobj) {
		try {
			//to convert string into primitive double
			rating = Double.parseDouble(jsonobj.getString(KEY_RATING));
			this.numOfPeople = Integer.parseInt(jsonobj.getString(KEY_NUMOFPEOPLE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
}
