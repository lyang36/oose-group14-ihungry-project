package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;


/**
 * a message replier for a given message
 * @author lyang
 *
 */
public interface MessageReplier {
	/**
	 * get the command of this replier
	 */
	public String getCommand();
	
	public void replyMessage();
}
