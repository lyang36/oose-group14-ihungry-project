package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONObject;

/**
 * The JSON handler needed for accunt, person
 * @author lyang
 *
 */
public interface JSONHandler<T> {
	/**
	 * Return the JSON object represent for this object
	 */
	public JSONObject getJSON();
	
	/**
	 * Parse an object from the represented object
	 * @param jsonobj
	 */
	public T parseFromJSONObject(JSONObject jsonobj);
}
