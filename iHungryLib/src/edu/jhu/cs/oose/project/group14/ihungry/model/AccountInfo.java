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
	/**
	 * JSON key.
	 */
	public static final String KEY_ID = "nameId"; //24 length hex string
	/**
	 * JSON key.
	 */
	public static final String KEY_UNAME = "uName";
	/**
	 * JSON key.
	 */
	public static final String KEY_PASSWD = "PassWord";
	
	private String id = "";
	private String uname = "";
	private String passwd = "";
	
	public AccountInfo(){
		
	}
	
	/**
	 * uname and password are plain text
	 * the password will be MD5 hashed to store
	 * @param username
	 * @param password
	 */
	public AccountInfo( String username, String password){
		this.uname = username;
		this.id = MD5.getNameMd5(username);
		this.passwd = MD5.getMd5(password);
	}
	
	
	/**
	 * get the id of this account. The id is the nameMd5 of the uname
	 * @return
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * 
	 * @return the plaintext of the uname
	 */
	public String getUname(){
		return uname;
	}
	
	/**
	 * 
	 * @return the MD5 hash of the passwd
	 */
	public String getPasswd(){
		return passwd;
	}
	
	/**
	 * set the md5 passwd, the passwd is directly set to the internal passwd field
	 * @param passwd
	 */
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	/**
	 * set the MD5 version of id
	 * @param id
	 */
	public void setId(String id){
		this.id = id;
	}


	/**
	 * set up the username -- plain text
	 * @param uname
	 */
	public void setUserName(String uname){
		this.uname = uname;
		this.id = MD5.getNameMd5(uname);
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
