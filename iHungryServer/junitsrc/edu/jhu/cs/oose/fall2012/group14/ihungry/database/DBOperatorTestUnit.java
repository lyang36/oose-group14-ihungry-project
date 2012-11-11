package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;


import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;


public class DBOperatorTestUnit {

	public static void initializeDB(){
		
		DB myDB;
		Mongo mongodb = null;	//mongo db
		DBObject loginObj =null; 
		BasicDBObject query;
		DBCollection cusCollection = null;
		DBCollection busiCollection = null;
		DBCollection orderCollection = null;
	    
		try {
			mongodb = new Mongo( "localhost" );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myDB = mongodb.getDB(DBOKeyNames.DATABASE_NAME);
		cusCollection = myDB.getCollection(DBOKeyNames.CUS_COLLECTION_NAME);
		busiCollection = myDB.getCollection(DBOKeyNames.BUS_COLLECTION_NAME);
		orderCollection = myDB.getCollection(DBOKeyNames.ORDER_COLLECTION_NAME);
		DBCursor cur = cusCollection.find();
		while(cur.hasNext()){
			cusCollection.remove(cur.next());
		}
		
		cur = busiCollection.find();
		while(cur.hasNext()){
			busiCollection.remove(cur.next());
		}
		
		cur = orderCollection.find();
		while(cur.hasNext()){
			orderCollection.remove(cur.next());
		}
		
	}
	
	@Test
	public void test() {
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		String md5names = null;
		String md5passwd = null;
		DBObject busobj = new BasicDBObject();
		md5names = MD5.getNameMd5("No1Res");
		md5passwd = MD5.getMd5("123456");
		busobj.put(AccountInfo.KEY_ID, md5names);
		busobj.put(AccountInfo.KEY_UNAME, "No1Res");
		busobj.put(AccountInfo.KEY_PASSWD, md5passwd);
		
		try {
			assertEquals(dboperator.checkBusiUnameExisted(MD5.getNameMd5("fjlkjalk")), false);
			System.out.println("Check busi uname not existed succeeded");
			
			assertEquals(dboperator.checkUserUnameExisted(MD5.getNameMd5("fjlkjalk")), false);
			System.out.println("Check user uname not existed succeeded");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("check not existed");
		}
		
/*		
		dboperator.addBusiness(busobj);
		try {
			String gn = (String) dboperator.getBusiness(md5names, md5passwd).get(DBOKeyNames.BUS_KEY_ID); 
			System.out.println(gn);
			assertEquals(gn, md5names);
			System.out.println("Add and get Business succeeded!");
			
			assertEquals(dboperator.checkBusiUnameExisted(md5names), true);
			System.out.println("Check busi uname existed succeeded");
			
			busobj.put(DBOKeyNames.BUS_KEY_EMAIL, "11@2212.com");
			dboperator.addBusiness(busobj);
			 gn = (String) dboperator.getBusiness(md5names, md5passwd).get(DBOKeyNames.BUS_KEY_EMAIL); 
			System.out.println(gn);
			assertEquals(gn, "11@2212.com");
			System.out.println("Change Business succeeded!");
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("busi");
		}
		
		
		DBObject cusobj = new BasicDBObject();
		md5names = MD5.getNameMd5("lyang");
		md5passwd = MD5.getMd5("123456");
		cusobj.put(DBOKeyNames.CUS_KEY_ID, md5names);
		cusobj.put(DBOKeyNames.CUS_KEY_REALNAME, "Lin Yang");
		cusobj.put(DBOKeyNames.CUS_KEY_PASSWD, md5passwd);
		cusobj.put(DBOKeyNames.CUS_KEY_EMAIL, "333@ppp.com");
		cusobj.put(DBOKeyNames.CUS_KEY_PRIMEPHONE, "123-456-7890");
		cusobj.put(DBOKeyNames.CUS_KEY_UNAME, "lyang");
		dboperator.addCustomer(cusobj);
		
		try {
			String gn = (String) dboperator.getCustomer(md5names, md5passwd).get(DBOKeyNames.CUS_KEY_ID); 
			System.out.println(gn);
			assertEquals(gn, md5names);
			System.out.println("Add and get customer succeeded!");
			
			assertEquals(dboperator.checkUserUnameExisted(md5names), true);
			System.out.println("Check user uname existed succeeded");
			
			
			cusobj.put(DBOKeyNames.BUS_KEY_EMAIL, "888@2212.com");
			dboperator.addCustomer(cusobj);
			 gn = (String) dboperator.getCustomer(md5names, md5passwd).get(DBOKeyNames.CUS_KEY_EMAIL); 
			System.out.println(gn);
			assertEquals(gn, "888@2212.com");
			System.out.println("Change Customer succeeded!");
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("user");
		}
	*/ 	
	}
		
	

}
