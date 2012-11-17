package edu.jhu.cs.oose.fall2012.group14.ihungry.database;



import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.ListedJSONObj;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

public class DBOperator implements DataBaseOperater{
	
	DB myDB;
    private Mongo mongodb = null;	//mongo db
    private DBObject loginObj =null; 
    private BasicDBObject query;
    private DBCollection cusCollection = null;
    private DBCollection busiCollection = null;
    private DBCollection orderCollection = null;
    
	@Override
	public boolean connectToDB() {
		try {
			mongodb = new Mongo( "localhost" );
			myDB = mongodb.getDB(DBOKeyNames.DATABASE_NAME);
			cusCollection = myDB.getCollection(DBOKeyNames.CUS_COLLECTION_NAME);
			busiCollection = myDB.getCollection(DBOKeyNames.BUS_COLLECTION_NAME);
			orderCollection = myDB.getCollection(DBOKeyNames.ORDER_COLLECTION_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean authenticate(String myUserName, String myPassword) {
		return true;
	}

	/**
	 * query the query on the collection
	 * @param coll
	 * @param query
	 * @return
	 */
	private DBCursor queryOnCollection(DBCollection coll, BasicDBObject query){
    	DBCursor cur = coll.find(query);
    	return cur;	
	}
	
	
	/**
	 * get the business without the password
	 * @return
	 */
	private DBObject getBusiness_priv(String uname){
		query = new BasicDBObject();
		query.put(AccountInfo.KEY_ID, uname);
		DBCursor cur = queryOnCollection(busiCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}
	
	/**
	 * get the customer without the password
	 * @return
	 */
	private DBObject getCustomer_priv(String uname){
		query = new BasicDBObject();
		query.put(AccountInfo.KEY_ID, uname);
		DBCursor cur = queryOnCollection(cusCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}
	
	@Override
	public boolean checkUserUnameExisted(AccountInfo acc) {
		if(getCustomer_priv(acc.getId()) != null){
			return true;
		}
		return false;
	}


	
	@Override
	public boolean checkBusiUnameExisted(AccountInfo acc) {
		if(getBusiness_priv(acc.getId()) != null){
			return true;
		}
		return false;
	}

	@Override
	public Customer getCustomer(AccountInfo acc) {
		String uname = acc.getId();
		String passwd = acc.getPasswd();
		query = new BasicDBObject();
		query.put(AccountInfo.KEY_ID, uname);
		query.put(AccountInfo.KEY_PASSWD, passwd);
		DBCursor cur = queryOnCollection(cusCollection, query);
		if(cur.hasNext()){
			JSONObject retj = null;
			try {
				retj = new JSONObject(cur.next().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Customer cus = new Customer();
			cus.parseFromJSONObject(retj);
			return cus;
		}		
		return null;
	}
	
	private boolean checkValidUser(AccountInfo acc){
		if(getCustomer(acc) == null){
			return false;
		}else{
			return true;
		}
	}
	
	
	private boolean checkValidBusi(AccountInfo acc){
		if(getBusiness(acc) == null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Restaurant getBusiness(AccountInfo acc) {
		query = (BasicDBObject) JSON.parse(acc.getJSON().toString());
		query.removeField(AccountInfo.KEY_UNAME);
		DBCursor cur = queryOnCollection(busiCollection, query);
		if(cur.hasNext()){
			JSONObject retj = null;
			try {
				retj = new JSONObject(cur.next().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			Restaurant bus = new Restaurant(null, null);
			bus.parseFromJSONObject(retj);
			return bus;
		}		
		return null;
	}


	@Override
	public ListedJSONObj getUserOrders(AccountInfo acc, int startInd,
			int endInd) {	
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidUser(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_CUSTID, acc.getId());
			DBCursor cur =orderCollection.find(query);
			cur.skip(startInd).limit(endInd - startInd + 1);
			
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToCus()){
						corder.flipToCusStatus();
						cusCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}
		return retjson;
	}

	@Override
	public ListedJSONObj getUserOrders(AccountInfo acc, int status,
			int startInd, int endInd) {
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidUser(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_CUSTID, acc.getId());
			query.put(Order.KEY_STATUS, status);
			DBCursor cur =orderCollection.find(query);
			cur.skip(startInd).limit(endInd - startInd + 1);
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToCus()){
						corder.flipToCusStatus();
						cusCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return retjson;
	}

	@Override
	public ListedJSONObj getChangedUserOrders(AccountInfo acc) {
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidUser(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_CUSTID, acc.getId());
			query.put(Order.IS_NEW_TO_CUS, true);
			DBCursor cur =orderCollection.find(query);
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToCus()){
						corder.flipToCusStatus();
						cusCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return retjson;
	}

	@Override
	public ListedJSONObj getBusiOrders(AccountInfo acc, int startInd,
			int endInd) {
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidBusi(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_RESTID, acc.getId());
			DBCursor cur =orderCollection.find(query);
			cur.skip(startInd).limit(endInd - startInd + 1);
			
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToRes()){
						corder.flipToResStatus();
						cusCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}
		return retjson;
	}

	
	@Override
	public ListedJSONObj getBusiOrders(AccountInfo acc, int status,
			int startInd, int endInd) {
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidBusi(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_RESTID, acc.getId());
			query.put(Order.KEY_STATUS, status);
			DBCursor cur =orderCollection.find(query);
			cur.skip(startInd).limit(endInd - startInd + 1);
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToRes()){
						corder.flipToResStatus();
						orderCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return retjson;
	}

	@Override
	public ListedJSONObj getChangedBusiOrders(AccountInfo acc) {
		ListedJSONObj retjson = new ListedJSONObj();
		if(checkValidBusi(acc)){
			query = new BasicDBObject();
			query.put(Order.KEY_RESTID, acc.getId());
			query.put(Order.IS_NEW_TO_RES, true);
			DBCursor cur =orderCollection.find(query);
			while(cur.hasNext()){
				try {
					DBObject ordobj = cur.next();
					
					JSONObject jordr = new JSONObject(ordobj.toString());
					Order corder = new Order(jordr);
					corder.parseFromJSONObject(jordr);
					retjson.add(jordr);
					if(corder.checkIsNewToRes()){
						corder.flipToResStatus();
						System.out.println("Order Changed to" + corder.getJSON().toString());
						orderCollection.findAndModify(ordobj, 
								(DBObject)JSON.parse(corder.getJSON().toString()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return retjson;
	}

	@Override
	public void userUpdateOrder(Order o) {
		query = new BasicDBObject();
		query.put(Order.KEY_ORDERID, o.getOrderID());
		o.setToResStatus(true);
		o.setToCusStatus(false);
		DBObject neworder = new BasicDBObject();
		neworder = (DBObject) JSON.parse(o.getJSON().toString());
		orderCollection.findAndModify(query, neworder);
	}

	@Override
	public void busiUpdateOrder(Order o) {
		o.setToCusStatus(true);
		o.setToResStatus(false);
		query = new BasicDBObject();
		query.put(Order.KEY_ORDERID, o.getOrderID());
		DBObject neworder = new BasicDBObject();
		neworder = (DBObject) JSON.parse(o.getJSON().toString());
		orderCollection.findAndModify(query, neworder);
	}

	@Override
	public void submitOrder(Order o) {
		o.setToCusStatus(false);
		o.setToResStatus(true);
		DBObject neworder = new BasicDBObject();
		neworder = (DBObject) JSON.parse(o.getJSON().toString());
		orderCollection.insert(neworder);
	}

	

	
	@Override
	public void addCustomer(Customer cus) {
		DBObject newcus = new BasicDBObject();
		newcus = (DBObject) JSON.parse(cus.getJSON().toString());
		
		if(checkUserUnameExisted(cus.getAccountInfo())){
			DBObject origin = getCustomer_priv(cus.getAccountInfo().getId());
			ObjectId id = (ObjectId) origin.get(DBOKeyNames.OBJ_KEY_ID);
			newcus.put(DBOKeyNames.OBJ_KEY_ID, id);
			cusCollection.update(origin, newcus);
		}else{
			cusCollection.insert(newcus);
		}
	}

	@Override
	public void addBusiness(Restaurant bus) {
		DBObject newbus = new BasicDBObject();
		newbus = (DBObject) JSON.parse(bus.getJSON().toString());
		if(checkBusiUnameExisted(bus.getAccountInfo())){
			DBObject origin = getBusiness_priv(bus.getAccountInfo().getId());
			ObjectId id = (ObjectId) origin.get(DBOKeyNames.OBJ_KEY_ID);
			newbus.put(DBOKeyNames.OBJ_KEY_ID, id);
			busiCollection.update(origin, newbus);
		}else{
			busiCollection.insert(newbus);
		}
	}

	
	@Override
	public ListedJSONObj findBusinessById(LocationInfo loc) {
		//TODO write the true algorithm for location search 
		ListedJSONObj busiAccs = new ListedJSONObj();
		query = new BasicDBObject();
		DBCursor cur = queryOnCollection(busiCollection, query);
		while(cur.hasNext()){
			JSONObject jbus = null;
			try {
				jbus = new JSONObject(cur.next().toString());
			} catch (MongoException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			AccountInfo acc = new AccountInfo();
			acc = (new Restaurant(null, null)).parseFromJSONObject(jbus).getAccountInfo();
			acc.setPasswd("");		//remove the password
			busiAccs.add(acc.getJSON());
		}	
		return busiAccs;
	}
	
	
	@Override
	public void close() {
		mongodb.close();
	}

	@Override
	public ContactInfo getBusinessContactInfo(AccountInfo acc) {
		ContactInfo rc = null;
		try {
			rc =  ((new Restaurant(null, null)).parseFromJSONObject(new JSONObject(
					this.getBusiness_priv(acc.getId()).toString()))).getContactInfo();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rc;
	}

	@Override
	public ContactInfo getCustomerContactInfo(AccountInfo acc) {
		ContactInfo rc = null;
		try {
			rc =  ((new Customer()).parseFromJSONObject(new JSONObject(
					this.getBusiness_priv(acc.getId()).toString()))).getContactInfo();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rc;
	}

	@Override
	public Album getBusinessAlbum(AccountInfo acc) {
		Album ra = null;
		try {
			ra =  ((new Restaurant(null, null)).parseFromJSONObject(new JSONObject(
					this.getBusiness_priv(acc.getId()).toString()))).getAlbum();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ra;
	}
	
	@Override
	public Menu getBusinessMenu(AccountInfo acc) {
		Menu rm = null;
		try {
			rm =  ((new Restaurant(null, null)).parseFromJSONObject(new JSONObject(
					this.getBusiness_priv(acc.getId()).toString()))).getMenu();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rm;
	}

}
