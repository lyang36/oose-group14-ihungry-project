package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

/**
 * handle the message repliers
 * @author lyang
 *
 */
public interface MessageReplierHandler {
	
	/**
	 * return the message replier by the command string
	 * @param commandString
	 * @return
	 */
	public MessageReplier getReplier(String commandString);
	
	/**
	 * add a message replier
	 */
	public void addReplier(MessageReplier replier);
	
	
	/**
	 * remove a replier
	 */
	public void removeReplier(MessageReplier replier);
	
	/**
	 * how many repliers are there?
	 */
	public int length();
}
