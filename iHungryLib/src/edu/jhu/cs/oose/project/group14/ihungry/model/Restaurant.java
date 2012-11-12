package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * One subclass of Person
 * 
 * @author group14
 * 
 */

public class Restaurant extends Person implements JSONHandler{
	public static final String KEY_MENU = "Menu";
	public static final String KEY_ALBUM = "Album";
	Menu menu;
	Album album;
	

	public Restaurant(Menu menu_in, Album album_in) {
	
		menu = menu_in;
		album = album_in;
	}

	public void setMenu(Menu m){
		this.menu = m;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public void setAlbum(Album a){
		this.album = a;
	}
	
	public Album getAlbum(){
		return album;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject jsonobj = super.getJSON();
		try {
			jsonobj.put(KEY_ALBUM, album.getJSON());
			jsonobj.put(KEY_MENU, menu.getJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		super.parseFromJSONObject(jsonobj);
		this.album = new Album(null);
		this.menu = new Menu();
		try {
			album.parseFromJSONObject(jsonobj.getJSONObject(KEY_ALBUM));
			menu.parseFromJSONObject(jsonobj.getJSONObject(KEY_MENU));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
}
