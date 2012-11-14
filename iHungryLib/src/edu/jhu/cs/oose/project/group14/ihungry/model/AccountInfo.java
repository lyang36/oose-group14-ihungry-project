package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;

/**
 * An account contains the username, password
 * It has the functionality of checking valid account and 
 * returning the valid Person back to either the Customer or Restaurant
 * @author Group 14
 *
 */

public class AccountInfo implements JSONHandler<AccountInfo>{
	//account
	public static final String KEY_ID = "nameId"; //24 length hex string
	public static final String KEY_UNAME = "uName";
	public static final String KEY_PASSWD = "PassWord";
	
	private String id = "";
	private String uname = "";
	private String passwd = "";
	
	public AccountInfo(){
		
	}
	
	/**
	 * uname and password are plain text
	 * @param username
	 * @param password
	 */
	public AccountInfo( String username, String password){
		this.uname = username;
		this.id = MD5.getNameMd5(username);
		this.passwd = MD5.getMd5(password);
	}
	
	public String getId(){
		return id;
	}
	
	public String getUname(){
		return uname;
	}
	
	public String getPasswd(){
		return passwd;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_ID, this.id);
			retObj.put(KEY_UNAME, this.uname);
			retObj.put(KEY_PASSWD, this.passwd);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public AccountInfo parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.id = jsonobj.getString(KEY_ID);
			this.passwd = jsonobj.getString(KEY_PASSWD);
			this.uname = jsonobj.getString(KEY_UNAME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this;
	}
}
