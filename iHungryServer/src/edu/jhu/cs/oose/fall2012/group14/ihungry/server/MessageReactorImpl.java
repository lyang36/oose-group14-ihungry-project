package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import org.bson.BSONObject;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOKeyNames;
import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperator;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;




public class MessageReactorImpl implements MessageReactor{
	
	int threadnum = 0;
	boolean isStopping = false;
	DBOperator dboperator;
	String uname, passwd, supinfo, commandmesg;
	InternetUtil internet = null;
	DBObject supinfoObj;
	
	interface OnJudgeListener{
		public void onTrue();
		
		public void onFalse();
	}
	
	public MessageReactorImpl(){
		dboperator = new DBOperator();
		dboperator.connectToDB();
	}
	
	private DBObject getKeyObj(DBObject target, String key){
		DBObject returnobj = new BasicDBObject();
		if(target != null){
			Object obj = target.get(key);
			returnobj.put(key, obj);
			return returnobj;
		}else{
			return null;
		}
	}
	
	
	/**
	 * return the string
	 * @param judgeObj - if null, do something; if not do something
	 * @param returnObj - return this obj as the supinfo
	 * @param cmdSuccessStr - if not null, the return commandstr
	 * @param cmdFailStr - if null return commandstr
	 */
	private void returnStringInfo(boolean judge, String returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
		try{
			if(judge){
				if(onjudge != null){
					onjudge.onTrue();
				}
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdSuccessStr, returnObj));
			}else{
				if(onjudge != null){
					onjudge.onFalse();
				}
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdFailStr, ""));
			}
		}catch(Exception e){
			try {
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdFailStr, ""));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * call returnStringInfo(boolean judge, String returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
	 * @param judge
	 * @param returnObj
	 * @param cmdSuccessStr
	 * @param cmdFailStr
	 * @param onjudge
	 */
	private void returnStringInfo(boolean judge, DBObject returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
		returnStringInfo(judge, returnObj==null ? null : returnObj.toString(), 
				cmdSuccessStr, cmdFailStr, onjudge);
	}
	
	@Override
	public void reactToMsg(String input, final InternetUtil internet) {
		commandmesg = CommunicationProtocol.getRequestFromReceivedStr(input);
		uname = CommunicationProtocol.getUnameFromReceivedStr(input);
		passwd = CommunicationProtocol.getPasswdFromReceivedStr(input);
		supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(input);
		this.internet = internet;
		supinfoObj = (DBObject) JSON.parse(supinfo);
		try {
			System.out.println(commandmesg);
			
			//for business login
			if(commandmesg.contains(CommunicationProtocol.BUSI_LOGIN)){
				returnStringInfo(dboperator.getBusiness(uname, passwd) != null,
						new BasicDBObject(), CommunicationProtocol.LOGIN_SUCCESS, 
						CommunicationProtocol.LOGIN_ERROR, null);
			}
			
			
			//check whether business username is existed
			else if(commandmesg.contains(CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkBusiUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}
			
			
			//business signup
			else if(commandmesg.contains(CommunicationProtocol.BUSI_SIGNUP)){
				returnStringInfo(!dboperator.checkBusiUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public void onTrue() {
								DBObject bus;
								bus = supinfoObj;
								dboperator.addBusiness(bus);
							}

							@Override
							public void onFalse() {
							}
					
				});
			}
			
			
			//business update contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_CONTACT)){
				final DBObject bus = dboperator.getBusiness(uname, passwd);
				returnStringInfo(bus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								DBObject busa = dboperator.getBusiness(uname, passwd);
								busa.put(Restaurant.KEY_CONTACT, supinfoObj);
								dboperator.addBusiness(busa);
							}

							@Override
							public void onFalse() {
								
							}
					
				});
			}
			
			
			//business get contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_GET_CONTACT)){	
				returnStringInfo(dboperator.getBusiness(uname, passwd) != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public void onTrue() {
								
								Restaurant retbusi = new Restaurant();
								retbusi.parseFromJSONObject(
									 (JSONObject) JSON.parse(
											 dboperator
											 .getBusiness(uname, passwd)
											 .toString())
											 );
								supinfo = retbusi.getContactInfo().getJSON().toString();
							}
		
							@Override
							public void onFalse() {				
							}
					
						});
				
			}
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.BUSI_PROCESS_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}
			
			
			
			else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_ORDERS)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_UPLOAD_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkUserUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}else if(commandmesg.contains(CommunicationProtocol.CUS_FIND_RESTAURANT)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_CONTACT)){
				Customer retcus = new Customer();
				
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public void onTrue() {
								
								Customer retcus = new Customer();
								retcus.parseFromJSONObject(
									 (JSONObject) JSON.parse(
											 dboperator
											 .getCustomer(uname, passwd)
											 .toString())
											 );
								supinfo = retcus.getContactInfo().getJSON().toString();
							}
		
							@Override
							public void onFalse() {				
							}
					
						});
				
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SIGN_UP)){
				returnStringInfo(!dboperator.checkUserUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public void onTrue() {
								DBObject cus;
								cus = supinfoObj;
								dboperator.addCustomer(cus);
							}

							@Override
							public void onFalse() {
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SUBMIT_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_UPDATE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else{
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						CommunicationProtocol.getUnameFromReceivedStr(commandmesg), 
						CommunicationProtocol.getPasswdFromReceivedStr(commandmesg), 
						CommunicationProtocol.PROCESS_FAILED, ""));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void setOperater(DataBaseOperater operater) {
		
	}

}
