package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Customer;
import edu.jhu.cs.oose.project.group14.ihungry.model.Icon;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.LocationInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;
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
		
		String[][] restaurant_info = { 
				{ 	"r1000", 
					"123",
					"New China II", 
					"1030 WEST 41st St, Baltimore, MD 21211",
					"410-889-0600"
				}, 
				{
					"r1001", 
					"123", 
					"The Carlyle Club",
				  	"500 W University Pkwy, Baltimore, MD 21210", 
					"410-243-5454"
				}, 
				{ 	"r1002", 
					"123",
					"Miss Shirley's Cafe", 
					"513 W Cold Spring Ln,Baltimore, MD 21210",
					"410-528-5373"
				},
				{ 	"r1003", 
					"123" , 
					"One World Cafe",
					"100 W University Pkwy, Baltimore, MD 21210", 
					"410-235-5777"
				}, 
				{ 	"r1004",
					"123",
					"SanSoo Kab San", 
					"2101 Maryland Ave, Baltimore, MD 21218",
					"(773) 334-1589"
				}, 
				{
					"r1005",
					"123",
					"The Food Market", 
					"1017 W 36th St, Baltimore, MD 21211",
					"(410) 366-0606"
				},
				{ 	"r1006", 
					"123",
					"Tamber's Nifty Fifties Dining",
					"3327 St. Paul St, Baltimore, MD 21218", 
					"(410) 243-5777"
				}, 
				{ 	"r1007",
					"123",
					"Thai Restaurant", 
					"3316 Greenmount Ave, Baltimore, MD 21218",
					"(410) 727-7971"
				} 
				};

		int[][] restaurant_locations = {
				{ 39337482, -76634559 }, 
				{ 39337249, -76624322 },
				{ 39344429, -76631478 }, 
				{ 39334798, -76620687 },
				{ 39313321, -76617787 }, 
				{ 39330855, -76633269 },
				{ 39329058, -76615716 }, 
				{ 39328962, -76609548 } };
		
		Item item1 = new Item("i001", "Chicken with Broccoli", 4.5, new Rating(
				4.0, 10), new Album());
		Item item2 = new Item("i002", "Assorted Mixed Vegetable", 4.65,
				new Rating(4.4, 11), new Album());
		Item item3 = new Item("i003", "Shrimp with Lobster Sauce", 4.95,
				new Rating(4.3, 12), new Album());
		Item item4 = new Item("i004", "Chicken with Cashew Nuts", 5.05,
				new Rating(4.1, 13), new Album());
		Item item5 = new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(
				3.95, 14), new Album());
		Item item6 = new Item("i006", "Skewered Beef", 4.5,
				new Rating(4.8, 15), new Album());
		Item item7 = new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16),
				new Album());
		Item item8 = new Item("i008", "House Special Soup", 5.50, new Rating(
				4.7, 17), new Album());
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		items.add(item8);
		
		
		
		DBOperatorTestUnit.initializeDB();
		edu.jhu.cs.oose.project.group14.ihungry.model.Menu menu = new edu.jhu.cs.oose.project.group14.ihungry.model.Menu();
		Album album = new Album();
		Icon icon = new Icon();

		Item item1 = new Item("i001", "Chicken with Broccoli", 4.5, new Rating(
		4.0, 10), new Album());
		Item item2 = new Item("i002", "Assorted Mixed Vegetable", 4.65,
				new Rating(4.4, 11), new Album());
		Item item3 = new Item("i003", "Shrimp with Lobster Sauce", 4.95,
				new Rating(4.3, 12), new Album());
		Item item4 = new Item("i004", "Chicken with Cashew Nuts", 5.05,
				new Rating(4.1, 13), new Album());
		Item item5 = new Item("i005", "B-B-Q Spare Ribs", 5.25, new Rating(
				3.95, 14), new Album());
		Item item6 = new Item("i006", "Skewered Beef", 4.5,
				new Rating(4.8, 15), new Album());
		Item item7 = new Item("i007", "Wonton Soup", 1.5, new Rating(4.5, 16),
				new Album());
		Item item8 = new Item("i008", "House Special Soup", 5.50, new Rating(
				4.7, 17), new Album());
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		items.add(item8);
	
		for(int i = 0; i < 8; i++){
			Menu menuServer = new Menu(restaurant_info[i][0], items); 
			AccountInfo acc1 = new AccountInfo(restaurant_info[i][0], restaurant_info[i][1]);
			Menu menuServer = new Menu(acc1.getId(), items); 
			ContactInfo contact1 = new ContactInfo(restaurant_info[i][2],
					new LocationInfo(restaurant_info[i][3], 
							restaurant_locations[i][0], 
							restaurant_locations[i][1]), 
							restaurant_info[i][4], 
							"",
							"", 
							"", 
							icon);
			Restaurant rest1 = new Restaurant(menuServer, album);
			rest1.setAccountInfo(acc1);
			rest1.setContactInfo(contact1);
			dboperator.addBusiness(rest1);
		}

		
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
