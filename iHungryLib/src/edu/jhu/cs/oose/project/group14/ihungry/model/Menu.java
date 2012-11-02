package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * A menu contains a list of items
 * 
 * @author group14
 *
 */
public class Menu implements JSONHandler{
	public Menu(){
		
	}
	public Menu( ArrayList<Item> items){
		
	}
	
	public ArrayList<Item> getItems(){
		return null;
	}
	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		// TODO Auto-generated method stub
		
	}
}
