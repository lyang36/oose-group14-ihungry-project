package edu.jhu.cs.oose.project.group14.ihungry.model;

import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOKeyNames;
import edu.jhu.cs.oose.fall2012.group14.ihungry.misc.ObjStringUtil;

/**
 * Represents a picture
 * 
 * @author group 14
 *
 */

@SuppressWarnings("serial")
public class Icon implements JSONHandler, Serializable{
	public static final String KEY_ICON = "icon";
	
	Image image = null;
	
	public void setImage(Image img){
		this.image = img;
	}
	
	public Image getImage(){
		return image;
	}
	
	/**
	 * convert the image to a string
	 * @return
	 */
	public String imgToString(){
		String str = "";
	/*	try {
			str = ObjStringUtil.toString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return str;
	}
	
	/**
	 * convert the src to a image
	 * @param src
	 * @return
	 */
	public static Image stringToImg(String src){
		Icon ic = null;
	/*	try {
			ic = (Icon) ObjStringUtil.fromString(src);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		return null;//ic.getImage();
	}

	@Override
	public JSONObject getJSON() {
		JSONObject imgobj = new JSONObject();
		try {
			imgobj.put(KEY_ICON, this.imgToString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imgobj;
	}

	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		try {
			String imgstr = jsonobj.getString(KEY_ICON);
			this.image = this.stringToImg(imgstr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
}
