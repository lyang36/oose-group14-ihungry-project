package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import java.util.ArrayList;

import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReplier;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReplierHandler;

public class MessageReplierHandlerImpl implements MessageReplierHandler{
	ArrayList<MessageReplier> messageReplierList;
	
	public MessageReplierHandlerImpl(){
		messageReplierList = new ArrayList<MessageReplier>();
	}
	
	@Override
	public MessageReplier getReplier(String commandString) {
		for(MessageReplier mp:messageReplierList){
			if(mp.getCommand().contains(commandString)){
				return mp;
			}
		}
		return null;
	}

	@Override
	public void addReplier(MessageReplier replier) {
		messageReplierList.add(replier);
		
	}

	@Override
	public void removeReplier(MessageReplier replier) {
		messageReplierList.remove(replier);
		
	}

	@Override
	public int length() {
		return messageReplierList.size();
	}

}
