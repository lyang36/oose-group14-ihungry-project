package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents the detailed info of the Person
 * 
 * @author group14
 *
 */
public class ContactInfo implements JSONHandler{
	
	public static final String KEY_ICON = "Icon";
	public static final String KEY_REALNAME = "RealName";
	public static final String KEY_EMAIL = "Email";
	public static final String KEY_PRIMEPHONE = "PrimPhone";
	public static final String KEY_SECPHONE = "SecPhone";
	public static final String KEY_BIRTH = "Birth";
	public static final String KEY_ADDRESS = "Address";
	
	
	String realName = "";
	String address = "";
	String primPhone = "";
	String secPhone = "";
	String email = "";
	String birthDate = "";
	Icon icon = null;
	
	public ContactInfo( String address, String primPhone){
		this.address = address;
		this.primPhone = primPhone;

	}
	
	public ContactInfo( String realName,
			String address, String primPhone, 
			String secPhone, String email, 
			String birthdate,
			Icon icon ){
		this(address, primPhone);
		this.realName = realName;
		this.secPhone = secPhone;
		this.email = email;
		this.icon = icon;
		this.birthDate = birthdate;
	}
	
	public void setIcon(Icon icon){
		this.icon = icon;
	}
	
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setPrimPhone(String phone){
		this.primPhone = phone;
	}
	
	public void setSecPhone(String phone){
		this.secPhone = phone;
	}
	
	public void setBirthDate(String birthdate){
		this.birthDate = birthdate;
	}
	
	public Icon getIcon(){
		return this.icon;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPrimPhone(){
		return primPhone;
	}
	
	public String getSecPhone(){
		return secPhone;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getRealName(){
		return realName;
	}
	
	public String getBirthDate(){
		return birthDate;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject retObj = new JSONObject();
		try {
			retObj.put(KEY_ADDRESS, this.address);
			retObj.put(KEY_BIRTH, this.birthDate);
			retObj.put(KEY_EMAIL, this.email);
			retObj.put(KEY_PRIMEPHONE, this.primPhone);
			retObj.put(KEY_REALNAME, this.realName);
			retObj.put(KEY_SECPHONE, this.secPhone);
			retObj.put(KEY_ICON, this.icon.getJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.address = jsonobj.getString(KEY_ADDRESS);
			this.birthDate = jsonobj.getString(KEY_BIRTH);
			this.email = jsonobj.getString(KEY_BIRTH);
			this.icon = new Icon();
			this.icon.parseFromJSONObject(jsonobj.getJSONObject(KEY_ICON));
			this.primPhone = jsonobj.getString(KEY_PRIMEPHONE);
			this.realName = jsonobj.getString(KEY_REALNAME);
			this.secPhone = jsonobj.getString(KEY_SECPHONE);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
	}
}
