package edu.jhu.cs.oose.fall2012.group14.ihungry.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperator;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtilImpl;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReplier;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReplierHandler;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.ServerModel;

class doComms implements Runnable {
	private Socket server;
    private String line,input;
    MessageReactor reactor;
    InternetUtilImpl internet;
    DBOperator dboperator;

    doComms(Socket server, MessageReactor reactor, DBOperator db) {
    	this.server = server;
    	this.reactor = reactor;
    	this.dboperator = db;
    	internet = new InternetUtilImpl();

    	try {
			internet.setSocket(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void run () {
    	input="";
    	try {
    		input = internet.receiveMessage();
    		System.out.println("Input Message: " + input);
    		reactor.reactToMsg(input, internet);
    		server.close();
    		dboperator.close();

    	} catch (Exception ioe) {
    		System.out.println("IOException on socket listen: " + ioe);
    		ioe.printStackTrace();
    	}   	
    	System.out.println("Threads Ended");
    	return;
    }
}


public class Server implements ServerModel{

	private static int port=4444, maxConnections=65535;
	MessageReactor msreactor;
	int threadNum = 0;
	
	
	/**
	 *  Listen for incoming connections and handle them
	 */
	@Override
	public void run() {
		ServerSocket listener = null;
		Socket server;
	    try{
	    	listener = new ServerSocket(CommunicationProtocol.SERVER_PORT);
	    	System.out.println("Start Listening...");
	    } catch (Exception ioe) {
		      System.out.println("Exception on socket listen: " + ioe);
		      ioe.printStackTrace();
		}
	      
	      while(true){
	    	  try{
	    		  DBOperator op = null;
		    	  server = listener.accept();
				  threadNum ++;
				  if(threadNum >= maxConnections){
					  threadNum = 0;
				  }
				  try {
					  msreactor = msreactor.getClass().newInstance();
					  op = null;
					  op = new DBOperator();
					  op.connectToDB();
					  msreactor.setOperater(op);
				  } catch (InstantiationException e) {
					  e.printStackTrace();
				  } catch (IllegalAccessException e) {
					  e.printStackTrace();
				  }
				  doComms conn_c= new doComms(server, msreactor, op);
				  Thread t = new Thread(conn_c);
				  t.start();
				  System.out.println("Connection " + threadNum +" starts...");
				  Thread.sleep(100);
	    	  }catch(Exception e){
	    		  e.printStackTrace();
	    	  }

	      }

	}


	@Override
	public void setMessageReactor(MessageReactor msl) {
		msreactor = msl;
	}
	
	
	public static void main(String args[]){
		Server server = new Server();
		MessageReactor mr= new MessageReactorImpl();
		//mr.setMessageReplierHandler();
		server.setMessageReactor(mr);
		server.run();
	}
}
