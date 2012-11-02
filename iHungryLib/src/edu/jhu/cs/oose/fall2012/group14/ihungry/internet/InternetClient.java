package edu.jhu.cs.oose.fall2012.group14.ihungry.internet;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;

/**
 * The Internet model for all the client (the android or the business side)
 * @author lyang
 *
 */
public class InternetClient{
	Socket requestSocket;
 	String message = null;
 	InternetUtil internet;
 	//exception flag
 	private boolean isException = false;
 	//the flag for message processing
	private boolean isSucceeded = false;
	Exception threadException = null;
 	
	public InternetClient(){
		
	}
	
 	/**
 	 * connect to the server
 	 * @return
 	 */
	private boolean connect() throws Exception{
		requestSocket = new Socket(CommunicationProtocol.SERVER_IP_ADDRESS, 
				CommunicationProtocol.SERVER_PORT);
		internet = new InternetUtilImpl();
		internet.setSocket(requestSocket);
		return true;
	}
	
	
	/**
	 * send a String, and get a string back
	 * timeout is in millisecond
	 * @param string
	 * @return
	 */
	public String sendAndGet(final String string, final long timeout) throws Exception{
		isSucceeded  = false;
		isException = false;
	    Calendar lCDateTime = Calendar.getInstance();
	    long startTime = lCDateTime.getTimeInMillis();
	    long processedTime = 0;
		(new Thread(){
			public void run(){
				threadException = null;
				try{
					connect();
					internet.sendMsg(string);
					message = internet.receiveMessage();
				}catch(Exception e){
					threadException = e;
					isSucceeded = false;
					try {
						if(requestSocket != null)
							requestSocket.close();
					} catch (IOException e1) {
					}
					isException = true;
					return;
				}
				try {
					if(requestSocket != null)
						requestSocket.close();
				} catch (IOException e) {
				}
				isSucceeded = true;
				threadException = null;
				isException = false;
				return;
			}
		}).start();
		
		
		do{
		    Calendar lCDateTime1 = Calendar.getInstance();
		    long nowtime = lCDateTime1.getTimeInMillis();
		    processedTime = nowtime - startTime;
		    try{
		    	Thread.sleep(100);
		    }catch(Exception e){
		    	
		    }
		}while(processedTime < timeout && !isSucceeded && !isException);
		
		if(processedTime > timeout){
			throw new Exception("Time Out");
		}
		
		if(!isSucceeded){
			throw threadException;
		}
		
		return message;
	}
}
