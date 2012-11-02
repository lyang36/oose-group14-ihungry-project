package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.ArrayList;

import com.example.androidihungry.R;
import com.example.androidihungry.R.layout;
import com.example.androidihungry.R.menu;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ListMenuItem;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.MyListViewAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This activity shows the detailed information about the specific restaurant
 * and the menu is listed. Customer could also select food items on the menu.
 * 
 * 
 * @author SuNFloWer
 * 
 */
public class OrderandRestaurantInfoActivity extends Activity {

	static final private String[][] menu_info = {
			{ "i1001", "Chicken with Broccoli", "4.5" },
			{ "i1002", "Assorted Mixed Vegetable", "4.65" },
			{ "i1003", "Shrimp with Lobster Sauce", "4.95" },
			{ "i1004", "Chicken with Cashew Nuts", "5.05" },
			{ "i1005", "B-B-Q Spare Ribs", "5.25" },
			{ "i1006", "Skewered Beef", "4.5" },
			{ "i1007", "Wonton Soup", "1.5" },
			{ "i1008", "House Special Soup", "5.50" }

	};
	static final private String[] menu_info2 = { "Chicken with Broccoli",
			"Assorted Mixed Vegetable", "Shrimp with Lobster Sauce",
			"Chicken with Cashew Nuts", "B-B-Q Spare Ribs", "Skewered Beef",
			"Wonton Soup", "House Special Soup" };

	private TextView tv_rest_name;
	private TextView tv_rest_addr;

	private ListView m_ListView;

	private MyListViewAdapter list_adapter;
	private ArrayList<ListMenuItem> menu_t;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderand_restaurant_info);

		tv_rest_name = (TextView) findViewById(R.id.tv_restaurant);
		tv_rest_addr = (TextView) findViewById(R.id.tv_address);

		/* Get information passed from MyitemizedOverlay */
		Bundle bundle = this.getIntent().getExtras();
		// tv_rest_name.setText(
		// bundle.getCharSequence("Title") + "@\n"
		// + bundle.getInt("LatE6") + " : " + bundle.getInt("LonE6"));

		String rest_id = (String) bundle.getCharSequence("rest_id");
		String rest_name = (String) bundle.getCharSequence("rest_name");
		String rest_addr = (String) bundle.getCharSequence("rest_addr");
		tv_rest_name.setText(rest_name);
		tv_rest_addr.setText(rest_addr);

		m_ListView = (ListView) findViewById(R.id.list);
		// m_ListView.setAdapter(new
		// ArrayAdapter<String>(this,R.layout.list_item_simple, R.id.title,
		// menu_info2));
		// list_adapter = new MyListViewAdapter(this, menu_info2);

		ListMenuItem item1 = new ListMenuItem("Chicken with Broccoli", 4.0,
				4.5, 0);
		ListMenuItem item2 = new ListMenuItem("Assorted Mixed Vegetable", 4.4,
				4.65, 0);
		ListMenuItem item3 = new ListMenuItem("Shrimp with Lobster Sauce", 4.3,
				4.95, 0);
		ListMenuItem item4 = new ListMenuItem("Chicken with Cashew Nuts", 4.1,
				5.05, 0);
		ListMenuItem item5 = new ListMenuItem("B-B-Q Spare Ribs", 3.95, 5.25, 0);
		ListMenuItem item6 = new ListMenuItem("Skewered Beef", 4.8, 4.5, 0);
		ListMenuItem item7 = new ListMenuItem("Wonton Soup", 4.5, 1.5, 0);
		ListMenuItem item8 = new ListMenuItem("House Special Soup", 4.7, 5.50,
				0);
		menu_t = new ArrayList<ListMenuItem>();
		menu_t.add(item1);
		menu_t.add(item2);
		menu_t.add(item3);
		menu_t.add(item4);
		menu_t.add(item5);
		menu_t.add(item6);
		menu_t.add(item7);
		menu_t.add(item8);

		list_adapter = new MyListViewAdapter(this, menu_t);
		m_ListView.setAdapter(list_adapter);

		// Click event for single list row
		m_ListView.setOnItemClickListener(m_ListViewOnItemClickListener);

		((Button) findViewById(R.id.bt_reviewItem))
				.setOnClickListener(bt_reviewItem_Listener);

	}

	OnClickListener bt_reviewItem_Listener = new OnClickListener() {
		public void onClick(View v) {
			for (int i = 0; i < menu_t.size(); i++) {
				ListMenuItem item_t = (ListMenuItem)menu_t.get(i);
				Log.v("[Order]",
						item_t.getTitle()+" "+item_t.getQuantity() + "");
			}
			Intent intent = new Intent(OrderandRestaurantInfoActivity.this, OrderReviewActivity.class);
			/*
			 * Send the order object as JSON to the next activity.
			 */
			
			
			
			
			OrderandRestaurantInfoActivity.this.startActivityForResult(intent, ActivitySwitchSignals.ORDERREVIEW);
			
			
			
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == ActivitySwitchSignals.ORDERREVIEWCLOSESWH){
		//	setResult(ActivitySwitchSignals.RESTAURANTINFOCLOSESWH);
			this.finish();
		}
	}

	/**
	 * A call-back for when the user tap on a single row of the list => nothing should happen.
	 */
	OnItemClickListener m_ListViewOnItemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			ListMenuItem clickItem = (ListMenuItem) list_adapter.getItem(position);
			// clickItem.setPrice(999);
			// list_adapter.notifyDataSetChanged();

			/*
			 * // ((ImageButton)
			 * findViewById(R.id.imgbtn_Orderhistory)).setOnClickListener
			 * (imgbtn_Orderhistory_Listener); Button btn_quantity = (Button)
			 * view.findViewById(R.id.btn_quantity);
			 * Log.v("[List View Click Event]"
			 * ,position+" "+btn_quantity.getText().toString());
			 * 
			 * TextView tv_price = list_adapter.getPriceView(position); //
			 * TextView tv_price = (TextView)view.findViewById(R.id.price);
			 * tv_price.setText("1111");
			 * 
			 * list_adapter.notifyDataSetChanged();
			 */
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_orderand_restaurant_info,
				menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back => Just finish
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
