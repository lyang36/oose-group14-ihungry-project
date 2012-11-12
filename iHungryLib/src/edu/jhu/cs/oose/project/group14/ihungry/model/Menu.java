package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.util.*;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A menu contains a list of items
 * 
 * @author group14
 *
 */
public class Menu implements JSONHandler,Serializable{
	
	public static final String KEY_REST_ID = "restId";
	public static final String KEY_MENU_SIZE = "menuSize";

	
	private List<Item> items = new ArrayList<Item>();
	private String restId;
	private int menuSize;
	
	public Menu(){
		
	}
	
	public Menu( String restId,List<Item> items){
		this.restId = restId;
		this.items = items;
		
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public Item getItemAt(int i){
		return items.get(i);
	}
	
	public void addItems(List<Item> items){
		this.items.addAll(items);
	}
	
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public void setItem(int i, Item item){
		this.items.set(i,item);
	}
	
	public void replaceMenu(List<Item> items){
		this.items = items;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put(KEY_REST_ID, restId);
			jsonobj.put(KEY_MENU_SIZE, menuSize);
						
			for(int i=0; i<this.menuSize; i++){
				jsonobj.put(i+"", items.get(i).getJSON());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}
	
	
	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		try {
			restId = jsonobj.getString(KEY_REST_ID);
			menuSize = Integer.parseInt(jsonobj.getString(KEY_MENU_SIZE));
			
			items = new ArrayList<Item>();
			for(int i=0; i<menuSize; i++){
				Item item = new Item();
				item.parseFromJSONObject(jsonobj.getJSONObject(i+""));
				items.add(item);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}	
		
	}
		
}

