package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Restaurant;

/**
 * inject testing data to the database
 * @author lyang
 *
 */
public class DataInject {
	@Test
	public void injectData(){
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		
		DBOperatorTestUnit.initializeDB();
		edu.jhu.cs.oose.project.group14.ihungry.model.Menu menu = new edu.jhu.cs.oose.project.group14.ihungry.model.Menu();
		Album album = new Album();
		Icon icon = new Icon();

		AccountInfo acc1 = new AccountInfo("NewChina1", "");
		ContactInfo contact1 = new ContactInfo("New China 1",
				new LocationInfo("100 WEST 41st St, Baltimore, MD 21211"), "445-685-1111", "",
				"", "", icon);
		Restaurant rest1 = new Restaurant(menu, album);
		rest1.setAccountInfo(acc1);
		rest1.setContactInfo(contact1);
		dboperator.addBusiness(rest1);
		
		
		acc1 = new AccountInfo("NewChina2", "");
		contact1 = new ContactInfo("New China 2",
				new LocationInfo("200 WEST 42st St, Baltimore, MD 21212"), "445-685-2222", "",
				"", "", icon);
		rest1 = new Restaurant(menu, album);
		rest1.setAccountInfo(acc1);
		rest1.setContactInfo(contact1);
		
		dboperator.addBusiness(rest1);
		
		
		
		acc1 = new AccountInfo("NewChina3", "");
		contact1 = new ContactInfo("New China 3",
				new LocationInfo("300 WEST 43st St, Baltimore, MD 21213"), "445-685-3333", "",
				"", "", icon);
		rest1 = new Restaurant(menu, album);
		rest1.setAccountInfo(acc1);
		rest1.setContactInfo(contact1);
		
		dboperator.addBusiness(rest1);
		
		ContactInfo contact = new ContactInfo(new LocationInfo("good place"), "111-222-1234");
		AccountInfo acc = new AccountInfo("lyang", "abc");
		Customer cus = new Customer();
		cus.setContactInfo(contact);
		cus.setAccountInfo(acc);
		dboperator.addCustomer(cus);
		
		
		contact = new ContactInfo(new LocationInfo("1111 place"), "444-333-1234");
		acc = new AccountInfo("szhao", "123");
		cus = new Customer();
		cus.setContactInfo(contact);
		cus.setAccountInfo(acc);
		dboperator.addCustomer(cus);
		
		contact = new ContactInfo(new LocationInfo("555 place"), "444-999-1234");
		acc = new AccountInfo("pkg", "123");
		cus = new Customer();
		cus.setContactInfo(contact);
		cus.setAccountInfo(acc);
		dboperator.addCustomer(cus);
		
	}
}
