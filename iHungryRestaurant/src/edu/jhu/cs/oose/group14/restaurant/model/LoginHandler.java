package edu.jhu.cs.oose.group14.restaurant.model;


import java.security.MessageDigest;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.*;

import java.security.MessageDigest;
import java.math.BigInteger;

/**
 * Helper class for the model of ihungry Vendor application. Handles the error 
 * checking and connecting to server tasks.
 * 
 * @author parkavi
 *
 */
public class LoginHandler {
	
	private String username;
	private String password;
	private MessageDigest md;
	private InternetClient client;
	//Person r;
	
	/**
	 *Constructor for LoginHandler class. 
	 */
	
	public LoginHandler(String uname, String pwd){
		
		username = uname;
		password = pwd;
		try{
			md = MessageDigest.getInstance("MD5");
		}
		catch(Exception e){
			System.out.println("An exception occured");
		}
		client = new InternetClient();
	}
	
	/**
	 * Converts username and password into md5 hash string and connects to 
	 * server with a JSON format message.Retrievs the message sent back by
	 * server.
	 * 
	 * @return command string returned from the server
	 */
	
	public String attemptLogin(){
		
		String result ="";
		
		md.update(username.getBytes());
		String usernamemd5 = new BigInteger(1,md.digest()).toString(16);
		md.update(password.getBytes());
		String passwordmd5 = new BigInteger(1,md.digest()).toString(16);
		String cmdString = usernamemd5 + passwordmd5 + CommunicationProtocol.BUSI_LOGIN;
		//System.out.println(cmdString);
		String supplyInfo = "{\"usernamemd5\":" + usernamemd5 + "\";" +
						    "\"username\":" + username + "\";" +
						    "\"password\":" + password + "\"}";
		String message = CommunicationProtocol.packMsg(CommunicationProtocol.packStr(cmdString,supplyInfo));
		//System.out.println("original message is " + message);
		
		/*String a = CommunicationProtocol.construcSendingStr(
				CommunicationProtocol.FB_SIGN_NAME, CommunicationProtocol.FB_SIGN_PASSWD, 
				CommunicationProtocol.LOGIN, "{\"lyang\": \"123456\"}");
		try{
			result = CommunicationProtocol.getRequestFromReceivedStr(client.sendAndGet(a,30000));
			
		}
		catch(Exception e)
		{
			System.out.println("Exception occured while sending and getting");
		}*/
		
		//System.out.println("result is " + result);
		return CommunicationProtocol.NO_SUCH_COMMAND;
	}

}