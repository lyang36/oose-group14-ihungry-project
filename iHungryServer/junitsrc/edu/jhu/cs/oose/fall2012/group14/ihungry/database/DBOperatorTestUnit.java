package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;
import java.util.*;
import java.util.Iterator;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.ListedJSONObj;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
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
	public void testCustomerFuncitons(){
		initializeDB();
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		ContactInfo contact = new ContactInfo(new LocationInfo("good place"), "1234");
		AccountInfo acc = new AccountInfo("lyang", "abc");
		Customer cus = new Customer();
		cus.setContactInfo(contact);
		cus.setAccountInfo(acc);
		dboperator.addCustomer(cus);
		
		assertEquals(dboperator.checkUserUnameExisted(acc), true);
		System.out.println("Check cus uname not existed succeeded");
		
		System.out.println("Testing Add customer!");
		cus = dboperator.getCustomer(acc);
		assertEquals(cus.getAccountInfo().getUname(), acc.getUname());

		dboperator.close();
		
	}
	
	
	@Test
	public void testBusnessFunctions(){
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		ContactInfo contact = new ContactInfo(new LocationInfo("good place"), "1234");
		AccountInfo acc = new AccountInfo("No1Res", "abc");
		Restaurant bus = new Restaurant(new Menu(), new Album());
		bus.setContactInfo(contact);
		bus.setAccountInfo(acc);
		dboperator.addBusiness(bus);
		
		assertEquals(dboperator.checkBusiUnameExisted(acc), true);
		System.out.println("Check busi uname not existed succeeded");
		
		System.out.println("Testing Add Restaurant!");
		bus = dboperator.getBusiness(acc);
		assertEquals(bus.getAccountInfo().getUname(), acc.getUname());
		dboperator.close();
	}
	
	@Test
	public void testOrderFunctions(){
		Order order = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		
		
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		AccountInfo acc = new AccountInfo("lyang", "abc");
		AccountInfo busiAcc = new AccountInfo("No1Res", "abc");
		
		
		System.out.println("Testing submit an order!");
		dboperator.submitOrder(order);
		
		
		System.out.println("Retrive new order by business");
		ListedJSONObj retord = dboperator.getChangedBusiOrders(busiAcc);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_RES)), 
					true);
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_CUS)), 
					false);
		} catch (JSONException e1) {
			fail();
			e1.printStackTrace();
		}
		
		//business update orders
		System.out.println("Testing update an order by business!");
		order = new Order(MD5.getMd5("order1"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.busiUpdateOrder(order);
		Order order11 = new Order(MD5.getMd5("order11"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order11);
		Order order12 = new Order(MD5.getMd5("order12"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order12);
		Order order13 = new Order(MD5.getMd5("order13"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order13);
		try{
			Thread.sleep(10000);
		}
		catch(Exception e){
			
		}
		Order order14 = new Order(MD5.getMd5("order14"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order14);
		try{
			Thread.sleep(10000);
		}
		catch(Exception e){
			
		}
		Order order15 = new Order(MD5.getMd5("order15"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("No1Res"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order15);
		
		try{
			Thread.sleep(10000*3);
		}
		catch(Exception e){
			
		}
		Order order16 = new Order(MD5.getMd5("order16"), MD5.getNameMd5("lyang"),
				MD5.getNameMd5("grp14"), Order.STATUS_UNDERPROCING, new ArrayList<OrderItem>());
		dboperator.submitOrder(order16);
		
		retord = dboperator.getBusiOrders(busiAcc, 0, 10);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getInt(Order.KEY_STATUS)), 
					Order.STATUS_UNDERPROCING);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
	
		}
		
		
		retord = dboperator.getBusiOrders(busiAcc, 0, 10);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getInt(Order.KEY_STATUS)), 
					Order.STATUS_UNDERPROCING);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
	
		}
		
		//user get new orders
		retord = dboperator.getChangedUserOrders(acc);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getInt(Order.KEY_STATUS)), 
					Order.STATUS_CONFIRMED);
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_CUS)), 
					true);
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_RES)), 
				false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();

		}
		
		
		System.out.println("Testing update an order by user!");
		dboperator.userUpdateOrder(order);
		retord = dboperator.getUserOrders(acc, 0, 10);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getInt(Order.KEY_STATUS)), 
					Order.STATUS_CONFIRMED);
			
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_CUS)), 
					false);
			
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_RES)), 
					true);
		} catch (JSONException e) {
			fail();
			e.printStackTrace();
		}
		
		
		System.out.println("Retrive new order by business");
		retord = dboperator.getChangedBusiOrders(busiAcc);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_RES)), 
					true);
			assertEquals((retord.getJSON().getJSONObject("0").getBoolean(Order.IS_NEW_TO_CUS)), 
					false);
		} catch (JSONException e1) {
			fail();
			e1.printStackTrace();
		}
		
		System.out.println("Retrive new order by business again -- should be none");
		retord = dboperator.getChangedBusiOrders(busiAcc);
		System.out.println(retord.getJSON().toString());
		try {
			assertEquals(retord.getJSON().getInt("Count"), 0);

		} catch (JSONException e1) {
			fail();
			e1.printStackTrace();
		}
		
		
		dboperator.close();
		
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
		AccountInfo acc = new AccountInfo("No1Res", "123456");
		busobj.put(AccountInfo.KEY_ID, md5names);
		busobj.put(AccountInfo.KEY_UNAME, "No1Res");
		busobj.put(AccountInfo.KEY_PASSWD, md5passwd);
		
		try {
			assertEquals(dboperator.checkBusiUnameExisted( new AccountInfo("1111", "123456")), false);
			System.out.println("Check busi uname not existed succeeded");
			
			assertEquals(dboperator.checkUserUnameExisted(acc), false);
			System.out.println("Check user uname not existed succeeded");
		} catch (Exception e) {
			e.printStackTrace();
			fail("check not existed");
		}
		dboperator.close();
		
	}
		
	

}
