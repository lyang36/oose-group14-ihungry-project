package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.io.Serializable;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Album contains a list of Icons ( photos )
 * @author group14
 *
 */

@SuppressWarnings("serial")
public class Album implements JSONHandler<Album>, Serializable{
	
	public static final String KEY_ICONLIST = "IconList";
	
	private ArrayList<Icon> iconList = new ArrayList<Icon>();
	
	public Album() {}
	
	public Album(ArrayList<Icon> iconList){
		this.iconList = iconList;
	}
	
	public void addPhoto(Icon icon){
		iconList.add(icon);
	}
	
	public void removePhoto(Icon icon){
		if(iconList.contains(icon))
			iconList.remove(icon);
	}
	
	public void removePhotoAt(int i){
		if(i >= 0 && i < iconList.size())
			iconList.remove(i);
	}

	
	@Override
	public JSONObject getJSON() {
		JSONObject iconListObject = new JSONObject();
		ArrayList<String> str = new ArrayList<String>();
		JSONArray list = new JSONArray();
		try{
			for(int i=0;i<iconList.size();i++){
				str.add(i,iconList.get(i).imgToString());
				list.put(iconList.get(i).imgToString());
			}
			iconListObject.put(KEY_ICONLIST, list);
		
		
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		return iconListObject;
	}

	
		
	@Override
	public Album parseFromJSONObject(JSONObject jsonobj){
		
		ArrayList<Icon> newIconList =new ArrayList<Icon>(); 
		
		try {
			JSONArray list =  (JSONArray) jsonobj.get(KEY_ICONLIST);
			for(int i =0; i< list.length();i++){
				Icon ic =new Icon();
				ic.setImage(Icon.stringToImg(list.getString(i)));
				newIconList.add(ic);
				
			}
			iconList = newIconList;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
	
}
