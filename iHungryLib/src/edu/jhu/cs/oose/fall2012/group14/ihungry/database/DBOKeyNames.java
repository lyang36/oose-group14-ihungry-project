package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

/**
 * The database Structure
 * @author lyang
 *
 */

public class DBOKeyNames {
	
	public static final String DATABASE_NAME = "iHungryDB";
	
	
	public static final String CUS_COLLECTION_NAME = "cCustomers";
	public static final String CUS_SUBCOLLECTION_INFO_NAME = "cCustomerInfo";
	
	//object
	public static final String OBJ_KEY_ID = "_id";
	
	public static final String CUS_KEY_ID = "usernameId"; //24 length hex string
	public static final String CUS_KEY_UNAME = "userName";
	public static final String CUS_KEY_PASSWD = "userPassWord";
	public static final String CUS_KEY_REALNAME = "userRealName";
	public static final String CUS_SUBKEY_FIRSTNAME = "userFirstName";
	public static final String CUS_SUBKEY_LASTNAME = "userLastName";
	public static final String CUS_KEY_EMAIL = "userEmail";
	public static final String CUS_KEY_PRIMEPHONE = "userPrimPhone";
	public static final String CUS_KEY_SECPHONE = "userSecPhone";
	public static final String CUS_KEY_BIRTH = "userBirth";
	public static final String CUS_KEY_ADDRESS = "userAddress";
	public static final String CUS_KEY_FACEBOOK = "userFacebook";
	
	public static final String BUS_COLLECTION_NAME = "cBusiness";
	public static final String BUS_KEY_ID = "businameId"; //24 length hex string
	public static final String BUS_KEY_UNAME = "busiName";
	public static final String BUS_KEY_PASSWD = "busiPassWord";
	public static final String BUS_KEY_NAME = "busiResName";
	public static final String BUS_KEY_EMAIL = "userEmail";
	public static final String BUS_KEY_PHONE = "userPhone";
	public static final String BUS_KEY_ADDRESS = "userAddress";
	public static final String BUS_KEY_MENU = "busiMenu";
	
	public static final String ORDER_COLLECTION_NAME = "cOrders";
	
}
