package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents the detailed info of the Person
 * 
 * @author group14
 *
 */
public class ContactInfo implements JSONHandler<ContactInfo>{
	/**
	 * JSON key.
	 */
	public static final String KEY_ICON = "Icon";
	/**
	 * JSON key.
	 */
	public static final String KEY_REALNAME = "RealName";
	/**
	 * JSON key.
	 */
	public static final String KEY_EMAIL = "Email";
	/**
	 * JSON key.
	 */
	public static final String KEY_PRIMEPHONE = "PrimPhone";
	/**
	 * JSON key.
	 */
	public static final String KEY_SECPHONE = "SecPhone";
	/**
	 * JSON key.
	 */
	public static final String KEY_BIRTH = "Birth";
	/**
	 * JSON key.
	 */
	public static final String KEY_ADDRESS = "Address";
	
	private String realName = "";
	private LocationInfo address = null;
	private String primPhone = "";
	private String secPhone = "";
	private String email = "";
	private String birthDate = "";
	private Icon icon = null;
	
	public ContactInfo( LocationInfo address, String primPhone){
		this.address = address;
		this.primPhone = primPhone;
		this.icon = new Icon();

	}
	
	public ContactInfo( String realName,
			LocationInfo address, String primPhone, 
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
	
	public void setAddress(LocationInfo address){
		this.address = address;
	}
	
	public void setPrimPhone(String phone){
		this.primPhone = phone;
	}
	
	public void setEmail(String email){
		this.email = email;
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
	
	public LocationInfo getAddress(){
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
			//this.address.getJSON().toString();
			retObj.put(KEY_ADDRESS, this.address.getJSON());
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
	public ContactInfo parseFromJSONObject(JSONObject jsonobj) {
		try {
			this.address = (new LocationInfo(0, 0)).parseFromJSONObject(jsonobj.getJSONObject(KEY_ADDRESS));
			this.birthDate = jsonobj.getString(KEY_BIRTH);
			this.email = jsonobj.getString(KEY_EMAIL);
			this.icon = new Icon();
			this.icon.parseFromJSONObject(jsonobj.getJSONObject(KEY_ICON));
			this.primPhone = jsonobj.getString(KEY_PRIMEPHONE);
			this.realName = jsonobj.getString(KEY_REALNAME);
			this.secPhone = jsonobj.getString(KEY_SECPHONE);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return this;
	}
}
