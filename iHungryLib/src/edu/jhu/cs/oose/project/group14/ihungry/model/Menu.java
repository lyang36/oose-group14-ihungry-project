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
@SuppressWarnings("serial")
public class Menu implements JSONHandler<Menu>,Serializable{
	/**
	 * JSON key.
	 */
    public static final String KEY_REST_ID = "restId";
    /**
	 * JSON key.
	 */
    public static final String KEY_MENU_SIZE = "menuSize";

    
    private List<Item> items = new ArrayList<Item>();
    private String restId;
    private int menuSize;
    
    public Menu(){
        
    }
    
    public Menu( String restId,List<Item> items){
        this.restId = restId;
        this.items = items;
        this.menuSize = items.size();
        
    }
    
    public void setRestId(String restId){
    	this.restId = restId;
    }
    
    /**
     * Get all the items in the menu.
     * @return all items.
     */
    public List<Item> getItems(){
        return items;
    }
    
    /**
     * Get a item at a specific position.
     * @param i index
     * @return item
     */
    public Item getItemAt(int i){
        return items.get(i);
    }
    
    /**
     * Add a list of items to the current menu.
     * @param items list of items
     */
    public void addItems(List<Item> items){
        this.items.addAll(items);
    }
    
    /**
     * Add an item to the menu.
     * @param item
     */
    public void addItem(Item item){
        this.items.add(item);
    }
    
    /**
     * Set the item at a specific position.
     * @param i index
     * @param item
     */
    public void setItem(int i, Item item){
        this.items.set(i,item);
    }
    
    /**
     * Replace the whole menu with a new menu (a list of items).
     * @param items new menu (a list of items)
     */
    public void replaceMenu(List<Item> items){
        this.items = items;
    }
    
    @Override
    public JSONObject getJSON() {
        JSONObject jsonobj = new JSONObject();
        try {
            jsonobj.put("restId", this.restId);
            jsonobj.put(KEY_MENU_SIZE, this.menuSize);
                        
            for(int i=0; i<this.menuSize; i++){
                jsonobj.put(i+"", this.items.get(i).getJSON());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobj;
    }
    
    
    @Override
    public Menu parseFromJSONObject(JSONObject jsonobj) {
        try {
            //this.restId = jsonobj.getString("restId");
            this.restId = jsonobj.getString(KEY_REST_ID);
            this.menuSize = jsonobj.getInt(KEY_MENU_SIZE);
            
            this.items = new ArrayList<Item>();
            for(int i=0; i<menuSize; i++){
                Item item = new Item();
                item.parseFromJSONObject(jsonobj.getJSONObject(i+""));
                this.items.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }    
        return this;
    }
        
}
