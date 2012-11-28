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
    
    //new a query objec
    private void newQuery(){
    	query = new BasicDBObject();
    }
    
    private void newQuery(String uname){
    	newQuery();
    	query.put(AccountInfo.KEY_ID, uname);
    }
    
    private void newQuery(AccountInfo acc){
    	newQuery(acc.getId());
    	query.put(AccountInfo.KEY_PASSWD, acc.getPasswd());
    }
    
    
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
	 * @param coll - collection
	 * @return the first object of this query
	 */
	private DBObject getFirstObjPriv(DBCollection coll){
		DBCursor cur = queryOnCollection(coll, query);
		if(cur.hasNext()){
			return cur.next();
		}else{
			return null;
		}
	}
	
	/**
	 * @param coll - collection
	 * @param uname
	 * @return the first object of this query
	 */
	private DBObject getFirstObjPriv(DBCollection coll, String uname){
		newQuery(uname);
		return getFirstObjPriv(coll);
	}
	
	/**
	 * @param coll - collection
	 * @param acc
	 * @return the first object of this query
	 */
	private DBObject getFirstObjPriv(DBCollection coll, AccountInfo acc){
		newQuery(acc);
		return getFirstObjPriv(coll);
	}
	
	/**
	 * get the business without the password
	 * @return
	 */
	private DBObject getBusiness_priv(String uname){
		return getFirstObjPriv(busiCollection, uname);
	}
	
	/**
	 * get the customer without the password
	 * @return
	 */
	private DBObject getCustomer_priv(String uname){
		return getFirstObjPriv(cusCollection, uname);
	}
	
	@Override
	public boolean checkUserUnameExisted(AccountInfo acc) {
		return (getCustomer_priv(acc.getId()) != null);
	}


	
	@Override
	public boolean checkBusiUnameExisted(AccountInfo acc) {
		return (getBusiness_priv(acc.getId()) != null);
	}

	private JSONObject dbObj2JSONObj(DBObject dbo){
		if(dbo == null) 
			return null;
		JSONObject retj = null;
		try {
			retj = new JSONObject(dbo.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retj;
	}
	
	@Override
	public Customer getCustomer(AccountInfo acc) {
		DBObject cusdb = getFirstObjPriv(cusCollection, acc);
		Customer cus = new Customer();
		JSONObject jobj = dbObj2JSONObj(cusdb);
		if(jobj != null){
			cus.parseFromJSONObject(jobj);
			return cus;	
		}else{
			return null;
		}
	}
	
	private boolean checkValidUser(AccountInfo acc){
		return (getCustomer(acc) != null);
	}
	
	
	private boolean checkValidBusi(AccountInfo acc){
		return (getBusiness(acc) != null);
	}

	@Override
	public Restaurant getBusiness(AccountInfo acc) {
		DBObject cusdb = getFirstObjPriv(busiCollection, acc);
		Restaurant bus = new Restaurant(null, null);
		JSONObject jobj = dbObj2JSONObj(cusdb);
		if(jobj != null){
			bus.parseFromJSONObject(jobj);
			return bus;	
		}else{
			return null;
		}
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
			//System.out.println(acc.getJSON().toString());
			busiAccs.add(acc.getJSON());
		}	
		//System.out.println(busiAccs.getJSON().toString());
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
