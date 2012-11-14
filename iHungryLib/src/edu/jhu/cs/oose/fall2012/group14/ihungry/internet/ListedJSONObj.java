package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.project.group14.ihungry.model.JSONHandler;

/**
 * this class use JSON to store an object list
 * this could also return back an JSON Object for the array 
 * Used to replace the JSONArray
 * @author lyang
 *
 */
public class ListedJSONObj  implements JSONHandler<ListedJSONObj>,Iterable<JSONObject> {
	public static final String KEY_COUNT = "Count"; 
	ArrayList<JSONObject> array; 
	
	ListedJSONObj(){
		array = new ArrayList<JSONObject>();
	}
	
	public void add(JSONObject obj){
		array.add(obj);
	}
	
	public void remove(JSONObject obj){
		array.remove(obj);
	}
	
	public void remove(int index){
		array.remove(index);
	}
	
	

	@Override
	public JSONObject getJSON() {
		JSONObject jso = new JSONObject();
		try {
			jso.put(KEY_COUNT, array.size());
			Iterator<JSONObject> it = array.iterator();
			int n = 0;
			while(it.hasNext()){
				jso.put(Integer.toString(n), it.next());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ListedJSONObj parseFromJSONObject(JSONObject jsonobj) {
		array = new ArrayList<JSONObject>();
		int n;
		try {
			n = jsonobj.getInt(KEY_COUNT);
			for(int i = 0; i < n; i++){
				JSONObject e = (JSONObject) jsonobj.get(Integer.toString(i));
				array.add(e);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public Iterator<JSONObject> iterator() {
		return array.iterator();
	}

	
}
