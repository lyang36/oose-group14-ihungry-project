package edu.jhu.cs.oose.project.group14.ihungry.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The super class of a restaurant or the customer
 * 
 * @author group14
 * 
 */
abstract public class Person implements JSONHandler{
	public static final String KEY_CONTACT = "Contact";
	
	AccountInfo account = null;
	ContactInfo contactinfo= null;
	public Person() {

	}

	public Person(AccountInfo accinfo, ContactInfo ci) {
		this.account = accinfo;
		this.contactinfo = ci;
	}


	public ContactInfo getContactInfo() {
		return contactinfo;
	}

	public void setContactInfo(ContactInfo ci) {
		this.contactinfo = ci;
	}

	public AccountInfo getAccountInfo(){
		return account;
	}
	
	public void setAccountInfo(AccountInfo acc){
		this.account = acc;
	}
	
	/**
	 * the field of account must explicitly shown here 
	 */
	@Override
	public JSONObject getJSON() {
		JSONObject retobj = account.getJSON();
		try {
			retobj.put(KEY_CONTACT, contactinfo.getJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retobj;
	}

	@Override
	public void parseFromJSONObject(JSONObject jsonobj) {
		account = new AccountInfo();
		contactinfo = new ContactInfo("", "");
		try {
			account.parseFromJSONObject(jsonobj);
			contactinfo.parseFromJSONObject((JSONObject) jsonobj.get(KEY_CONTACT));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
