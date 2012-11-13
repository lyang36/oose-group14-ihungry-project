package edu.jhu.cs.oose.project.group14.ihungry.model;

import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.junit.Test;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;

public class ModelTest {

	@Test
	public void testAccountInfo() {
		//AccountInfo test
		AccountInfo acc = new AccountInfo("abc", "efg");
		System.out.println(acc.getJSON().toString());
		acc.parseFromJSONObject(acc.getJSON());
		assertEquals(acc.getId(), MD5.getNameMd5("abc"));
		assertEquals(acc.getPasswd(), MD5.getMd5("efg"));
		assertEquals(acc.getUname(), "abc");
	}
	
	@Test
	public void testContact(){
		ContactInfo contact = new ContactInfo("abc dff", "123456687");
		contact.parseFromJSONObject(contact.getJSON());
		System.out.println(contact.getJSON().toString());
		
		assertEquals(contact.getAddress(), "abc dff");
	}

	@Test
	public void testCustomer(){
		ContactInfo contact = new ContactInfo("abc dff", "123456687");
		AccountInfo acc = new AccountInfo("abc", "efg");
		Customer cus = new Customer();
		cus.setAccountInfo(acc);
		cus.setContactInfo(contact);
		cus.parseFromJSONObject(cus.getJSON());
		System.out.println(cus.getJSON().toString());
		
		assertEquals(cus.getAccountInfo().getUname(), "abc");
	}
	
	
	@Test
	public void testRestaurant(){
		//not do menu
		ContactInfo contact = new ContactInfo("abc dff", "123456687");
		AccountInfo acc = new AccountInfo("abc", "efg");
		
		Item item = new Item("i001", "Pizza", 4.54, new Rating(5, 10), new Album());
		Item item2 = new Item("i002", "Soup", 3.67, new Rating(4, 20), new Album());

		List<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item2);
		Menu menu = new Menu("r001", items);
		
		Album album = new Album();
		
		Restaurant res = new Restaurant(menu, album);
		res.setAccountInfo(acc);
		res.setContactInfo(contact);
		res.parseFromJSONObject(res.getJSON());
		
		JSONObject obj1 = res.getJSON();
		System.out.println("Test Restaurant: "+obj1.toString());
		
		res.parseFromJSONObject(res.getJSON());
		assertEquals(obj1.toString(), res.getJSON().toString());
	}
	
	@Test
	public void testRating(){
		Rating newRating = new Rating(5, 10);
		
		newRating.updateRating(4);
		assertEquals(newRating.getRating()+"", 54.0/11+"");
		assertEquals(newRating.getNumOfPeople(), 11);
		
		System.out.println(newRating.getJSON());
		
		newRating.parseFromJSONObject(newRating.getJSON());
		assertEquals(newRating.getRating()+"", 54.0/11+"");
	}
	/*
	@Test
	public void testIcon(){
		Icon newIcon = new Icon();
		
		Image img = null;
		Image img1 = null;

		try {
			File file1 = new File("testSrc/edu/jhu/cs/oose/project/group14/ihungry/model/list_disclosure.png");
			System.out.println(file1.getAbsolutePath());
		    img = ImageIO.read(file1);
		    img1 = ImageIO.read(new File("testSrc/edu/jhu/cs/oose/project/group14/ihungry/model/marker3.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		newIcon.setImage(img);
		
//		assertEquals(newIcon.imgToString(),);
		System.out.println(newIcon.imgToString());
		
		/*		System.out.println(newIcon.getJSON());
		
		newIcon.setImage((Image)img1);
		System.out.println(newIcon.getJSON());
		
		newIcon.parseFromJSONObject(newIcon.getJSON());
		assertEquals(newIcon.getImage(), img1);
		
	}*/
	
	
	@Test
	public void testAlbum(){
		List<Icon> icons = new ArrayList<Icon>();
		Album album = new Album(icons);
	
		JSONObject jsonobj1 = album.getJSON();
		
		System.out.println(album.getJSON());
		album.parseFromJSONObject(album.getJSON());
		assertEquals(jsonobj1.toString(), album.getJSON().toString());
	
	}
	
	@Test
	public void testItem(){
		Item item = new Item("i001", "Pizza", 4.54, new Rating(5, 10), new Album());
		
		JSONObject jsonobj1 = item.getJSON();
		System.out.println(jsonobj1.toString());
		
		item.parseFromJSONObject(item.getJSON());
		
		assertEquals(jsonobj1.toString(), item.getJSON().toString());
		
	}
	
	@Test
	public void testOrderItem(){
		Item item = new Item("i001", "Pizza", 4.54, new Rating(5, 10), new Album());
		OrderItem orderItem = new OrderItem(item, 2);
		
		JSONObject jsonobj1 = orderItem.getJSON();
		System.out.println(jsonobj1.toString());
		
		orderItem.parseFromJSONObject(orderItem.getJSON());
		
		assertEquals(jsonobj1.toString(), orderItem.getJSON().toString());
	}
	
	@Test
	public void testOrder(){
		Item item = new Item("i001", "Pizza", 4.54, new Rating(5, 10), new Album());
		OrderItem orderItem = new OrderItem(item, 2);
		
		Item item2 = new Item("i002", "Soup", 3.67, new Rating(4, 20), new Album());
		OrderItem orderItem2 = new OrderItem(item2, 3);
		
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem);
		orderItems.add(orderItem2);
		Order order = new Order("o001", "c003", "r009", 1, orderItems);
		
		JSONObject job1 = order.getJSON();
		System.out.println(job1.toString());
		
		order.parseFromJSONObject(order.getJSON());
		assertEquals(job1.toString(), order.getJSON().toString());
	}
	
	@Test
	public void testMenu(){
		Item item = new Item("i001", "Pizza", 4.54, new Rating(5, 10), new Album());
		Item item2 = new Item("i002", "Soup", 3.67, new Rating(4, 20), new Album());

		List<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item2);
		Menu menu = new Menu("r001", items);
		JSONObject job1 = menu.getJSON();
		System.out.println(job1.toString());
		
		menu.parseFromJSONObject(menu.getJSON());
		assertEquals(job1.toString(), menu.getJSON().toString());
		
	}
}
