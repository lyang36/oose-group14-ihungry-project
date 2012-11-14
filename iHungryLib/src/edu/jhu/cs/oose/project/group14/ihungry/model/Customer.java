package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOKeyNames;

/**
 * One subclass of Person
 * @author group14
 *
 */
public class Customer extends Person{
	public Customer(){
		
	}

	@Override
	public JSONObject getJSON() {
		return super.getJSON();
	}

	@Override
	public Customer parseFromJSONObject(JSONObject jsonobj) {
		super.parseFromJSONObject(jsonobj);
		return this;
	}
}
