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
        this.menuSize = items.size();
        
    }
    
    public void setRestId(String restId){
    	this.restId = restId;
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
