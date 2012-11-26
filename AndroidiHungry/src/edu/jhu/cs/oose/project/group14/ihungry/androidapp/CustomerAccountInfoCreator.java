package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;

/**
 * Return an AccountInfo instance given the username and MD5 version of password.
 * @author SuNFloWer
 *
 */
public class CustomerAccountInfoCreator {
	
	/**
	 * Create an AccountInfo instance using the input username and MD5 version of password.
	 * @param username
	 * @param MD5pwd
	 * @return
	 */
	public static AccountInfo createAccountInfo( String username, String MD5pwd ){
		AccountInfo accinfo = new AccountInfo();
		accinfo.setUserName(FileHandler.username_stored);
		accinfo.setPasswd(FileHandler.pwd_stored);
		
		return accinfo;
	}

}
