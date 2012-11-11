package edu.jhu.cs.oose.project.group14.ihungry.model;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;

public class ModelTest {

	@Test
	public void test() {
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
	}
	
	@Test
	public void testRestaurant(){
		//not do menu
		ContactInfo contact = new ContactInfo("abc dff", "123456687");
		AccountInfo acc = new AccountInfo("abc", "efg");
		Restaurant res = new Restaurant();
		res.setAccountInfo(acc);
		res.setContactInfo(contact);
		res.parseFromJSONObject(res.getJSON());
		System.out.println(res.getJSON().toString());
	}
}
