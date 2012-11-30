package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperator;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.ListedJSONObj;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.OrderQuerier;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;




public class MessageReactorImpl implements MessageReactor{
	
	int threadnum = 0;
	boolean isStopping = false;
	DataBaseOperater dboperator;
	String uname, passwd, supinfo, commandmesg;
	InternetUtil internet = null;
	JSONObject supinfoObj;
	AccountInfo inputAcc;
	
	//judge whether something is true or not
	interface OnJudgeListener{
		public String onTrue();
		
		public String onFalse();
		
		
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
	 * if returnObj == null, return the object from onTrue, or onFalse
	 * if all objects are null, return empty string
	 * @param judge - if true, do something; if not do something
	 * @param returnObj - return this obj as the supinfo
	 * @param cmdSuccessStr - if not null, the return commandstr
	 * @param cmdFailStr - if null return commandstr
	 */
	private void returnStringInfo(boolean judge, String returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
		if(returnObj == null){
			returnObj = "";
		}
		try{
			String temp = null;
			if(judge){
				if(onjudge != null){
					temp = onjudge.onTrue();
				}
				if(temp != null && (returnObj == "")){
					internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdSuccessStr, temp));
				}else{
					internet.sendMsg(CommunicationProtocol.construcSendingStr(
							uname, passwd, cmdSuccessStr, returnObj));
				}
				//System.out.println(cmdSuccessStr + temp);
			}else{
				if(onjudge != null){
					temp = onjudge.onFalse();
				}
				if(temp != null && (returnObj == "")){
					internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdFailStr, temp.toString()));
				}else{
					internet.sendMsg(CommunicationProtocol.construcSendingStr(
							uname, passwd, cmdFailStr, returnObj));
				}
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
	    inputAcc = new AccountInfo();
		inputAcc.setId(uname);
		inputAcc.setPasswd(passwd);
		//System.out.println("<<><>>" + inputAcc.getJSON().toString());
		this.internet = internet;
		try {
			if(supinfo!=null && supinfo.length() > 0)
				supinfoObj = new JSONObject(supinfo);
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		try {
			System.out.println(commandmesg);
			
			//for business login
			if(commandmesg.contains(CommunicationProtocol.BUSI_LOGIN)){
				
				returnStringInfo(dboperator.getBusiness(inputAcc) != null,
						"", CommunicationProtocol.LOGIN_SUCCESS, 
						CommunicationProtocol.LOGIN_ERROR, new OnJudgeListener(){

					@Override
					public String onTrue() {
						Restaurant bus = dboperator.getBusiness(inputAcc);
						return bus.getJSON().toString();
					}

					@Override
					public String onFalse() {
						return null;
					}
			
		});
			}
			
			
			//check whether business username is existed
			else if(commandmesg.contains(CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkBusiUnameExisted(inputAcc),
						supinfo, CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}
			
			
			//business signup
			else if(commandmesg.contains(CommunicationProtocol.BUSI_SIGNUP)){
				System.out.println(">>>>"+inputAcc.getJSON().toString());
				returnStringInfo(!dboperator.checkBusiUnameExisted(inputAcc),
						supinfo, CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public String onTrue() {
								Restaurant bus = new Restaurant(null, null);
								try {
									bus.parseFromJSONObject(new JSONObject(supinfoObj.toString()));
								} catch (JSONException e) {
									e.printStackTrace();
								}
								dboperator.addBusiness(bus);
								return null;
							}

							@Override
							public String onFalse() {
								return null;
							}
					
				});
			}
			
			
			//business update contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_CONTACT)){
				final Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo(bus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public String onTrue() {
								Restaurant busa = dboperator.getBusiness(inputAcc);
								busa.setContactInfo((new ContactInfo(null, null)).
										parseFromJSONObject(supinfoObj));
		
								dboperator.addBusiness(busa);
								return null;
							}

							@Override
							public String onFalse() {
								return null;	
							}
					
				});
			}
			
			
			
			//business update contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_ALBUM)){
				final Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo(bus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public String onTrue() {
								Restaurant busa = dboperator.getBusiness(inputAcc);
								busa.setAlbum((new Album()).
										parseFromJSONObject(supinfoObj));
								dboperator.addBusiness(busa);
								return null;
							}

							@Override
							public String onFalse() {
								return null;	
							}
					
				});
			}
			
			//business update contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_MENU)){
				final Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo(bus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public String onTrue() {
								Restaurant busa = dboperator.getBusiness(inputAcc);
								busa.setMenu((new Menu()).
										parseFromJSONObject(supinfoObj));
								dboperator.addBusiness(busa);
								return null;
							}

							@Override
							public String onFalse() {
								return null;	
							}
					
				});
			}
			
			//business get contact
			else if(commandmesg.contains(CommunicationProtocol.BUSI_GET_CONTACT)){	
				Restaurant bus = dboperator.getBusiness(inputAcc);
				//System.out.println(bus);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								String bus = dboperator.getBusiness(inputAcc).getJSON().toString();
								Restaurant retbusi = new Restaurant(new Menu(), new Album());
								System.out.println(bus);
								JSONObject jbus = null;
								try{
									 jbus = new JSONObject(bus);
								}catch(Exception e){
									e.printStackTrace();
								}	
								retbusi.parseFromJSONObject(jbus);
								return retbusi.getContactInfo().getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			else if(commandmesg.contains(CommunicationProtocol.BUSI_GET_ALBUM)){	
				Restaurant bus = dboperator.getBusiness(inputAcc);
				//System.out.println(bus);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								String bus = dboperator.getBusiness(inputAcc).getJSON().toString();
								Restaurant retbusi = new Restaurant(new Menu(), new Album());
								JSONObject jbus = null;
								try{
									 jbus = new JSONObject(bus);
								}catch(Exception e){
									e.printStackTrace();
								}	
								retbusi.parseFromJSONObject(jbus);
								return retbusi.getAlbum().getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			else if(commandmesg.contains(CommunicationProtocol.BUSI_GET_MENU)){	
				Restaurant bus = dboperator.getBusiness(inputAcc);
				//System.out.println(bus);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								String bus = dboperator.getBusiness(inputAcc).getJSON().toString();
								Restaurant retbusi = new Restaurant(new Menu(), new Album());
								JSONObject jbus = null;
								try{
									 jbus = new JSONObject(bus);
								}catch(Exception e){
									e.printStackTrace();
								}	
								retbusi.parseFromJSONObject(jbus);
								return retbusi.getMenu().getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			else if(commandmesg.contains(CommunicationProtocol.CUS_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkUserUnameExisted(inputAcc),
						new BasicDBObject(), CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}
			
			
			
			//business signup
			else if(commandmesg.contains(CommunicationProtocol.CUS_SIGN_UP)){
				returnStringInfo(!dboperator.checkUserUnameExisted(inputAcc),
						new BasicDBObject(), CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public String onTrue() {
								Customer cus = new Customer();
								cus.parseFromJSONObject(supinfoObj);
								dboperator.addCustomer(cus);
								return null;
							}

							@Override
							public String onFalse() {
								return null;
							}
					
				});
			}
			
			else if(commandmesg.contains(CommunicationProtocol.CUS_LOGIN)){
				returnStringInfo(dboperator.getCustomer(inputAcc) != null,
						"", CommunicationProtocol.LOGIN_SUCCESS, 
						CommunicationProtocol.LOGIN_ERROR, new OnJudgeListener(){

					@Override
					public String onTrue() {
						Customer cus = dboperator.getCustomer(inputAcc);
						return cus.getJSON().toString();
					}

					@Override
					public String onFalse() {
						return null;
					}
			
		});
			}
			
			
			
			//business update contact
			else if(commandmesg.contains(CommunicationProtocol.CUS_UPDATE_CONTACT)){
				final Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public String onTrue() {
								Customer cusa = dboperator.getCustomer(inputAcc);
								//cusa.put(Customer.KEY_CONTACT, supinfoObj);
								cusa.setContactInfo(new ContactInfo(null,null).parseFromJSONObject(
											supinfoObj));

								dboperator.addCustomer(cusa);
								return null;
							}

							@Override
							public String onFalse() {
								return null;	
							}
					
				});
			}
			
			
			else if(commandmesg.contains(CommunicationProtocol.CUS_GET_CONTACT)){	
				Customer cus = dboperator.getCustomer(inputAcc);
				//System.out.println(bus);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								String cus = dboperator.getCustomer(inputAcc).getJSON().toString();
								Customer retcus = new Customer();
								JSONObject jcus = null;
								try{
									 jcus = new JSONObject(cus);
								}catch(Exception e){
									e.printStackTrace();
								}	
								retcus.parseFromJSONObject(jcus);
								return retcus.getContactInfo().getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_FIND_RESTAURANT_IDS)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								LocationInfo loc = new LocationInfo("");
									//System.out.println(supinfo);
								loc.parseFromJSONObject(supinfoObj);
								JSONObject jret = dboperator.findBusinessById(loc).getJSON();
								return jret.toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_GET_RES_CONTACT)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								AccountInfo acc = (new AccountInfo()).
									parseFromJSONObject(supinfoObj);
								return dboperator.getBusinessContactInfo(acc).getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
	
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_GET_RES_ALBUM)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								AccountInfo acc = (new AccountInfo()).
									parseFromJSONObject(supinfoObj);
								return dboperator.getBusinessAlbum(acc).getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_GET_MENU)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								AccountInfo acc = (new AccountInfo()).
									parseFromJSONObject(supinfoObj);
								return dboperator.getBusinessMenu(acc).getJSON().toString();

							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_SUBMIT_ORDER)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								dboperator.submitOrder(new Order(supinfoObj));
								return "";
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.CUS_UPDATE_ORDER)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								dboperator.userUpdateOrder(new Order(supinfoObj));
								return "";
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			//retrive orders
			else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_ORDER)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								OrderQuerier querier = new OrderQuerier();
								querier.parseFromJSONObject(supinfoObj);
								ListedJSONObj obj = null;
								System.out.println(querier.getJSON().toString());
								if(querier.getStatus() == -1){
									obj = dboperator.getUserOrders(inputAcc, querier.getStartIndex()
										, querier.getEndIndex());
								}else{
									obj = dboperator.getUserOrders(inputAcc, querier.getStatus(), 
											querier.getStartIndex(), querier.getEndIndex());
								}
								return obj.getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
						
			//retrive changed orders
			else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER)){
				Customer cus = dboperator.getCustomer(inputAcc);
				returnStringInfo((cus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								ListedJSONObj obj = null;
								obj = dboperator.getChangedUserOrders(inputAcc);
								return obj.getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//retrive orders
			else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_ORDERS)){
				Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								OrderQuerier querier = new OrderQuerier();
								querier.parseFromJSONObject(supinfoObj);
								ListedJSONObj obj = null;
								System.out.println(querier.getJSON().toString());
								if(querier.getStatus() == -1){
									obj = dboperator.getBusiOrders(inputAcc, querier.getStartIndex()
										, querier.getEndIndex());
								}else{
									obj = dboperator.getBusiOrders(inputAcc, querier.getStatus(), 
											querier.getStartIndex(), querier.getEndIndex());
								}
								return obj.getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.BUSI_GET_CUS_CONTACT)){
				Restaurant res = dboperator.getBusiness(inputAcc);
				returnStringInfo((res != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								Order odr = (new Order(supinfoObj));
								return dboperator.busiGetCusContactInfo(odr).getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			//retrive changed orders
			else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS)){
				Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								ListedJSONObj obj = null;
								obj = dboperator.getChangedBusiOrders(inputAcc);
								System.out.println("order:" + obj.getJSON().toString());
								return obj.getJSON().toString();
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			
			//business process a order
			else if(commandmesg.contains(CommunicationProtocol.BUSI_PROCESS_ORDER)){
				Restaurant bus = dboperator.getBusiness(inputAcc);
				returnStringInfo((bus != null),
						"", 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){
							@Override
							public String onTrue() {
								dboperator.busiUpdateOrder(new Order(supinfoObj));
								return "";
							}
		
							@Override
							public String onFalse() {	
								return null;
							}
					
						});
				
			}
			
			
			else{
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				//System.out.println("dfafadf>>>>>" + commandmesg);
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, 
						passwd, 
						CommunicationProtocol.PROCESS_FAILED, ""));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void setOperater(DataBaseOperater operater) {
		this.dboperator = operater;
	}

}
