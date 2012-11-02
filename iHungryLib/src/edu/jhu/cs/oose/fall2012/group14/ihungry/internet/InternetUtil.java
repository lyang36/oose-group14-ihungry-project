package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import java.io.IOException;
import java.net.Socket;
/**
 * All the method used for internet communications
 * @author lyang
 *
 */
public interface InternetUtil {
	
	/**
	 * set the socket this util should have
	 * @param so
	 * @throws IOException 
	 */
	public void setSocket(Socket so) throws IOException;
	
	/**
	 * receive a file
	 * @return - the length of the file in bytes
	 */
	public long receiveFile(String filename) throws Exception;
	
	/**
	 * send a file
	 * @return - the length of the file sending in bytes
	 */
	public long sendFile(String filename) throws Exception;
	
	/**
	 * receive messages
	 */
	public String receiveMessage() throws Exception;
	
	/**
	 * send a message
	 */
	public void sendMsg(String msg) throws Exception;
}
