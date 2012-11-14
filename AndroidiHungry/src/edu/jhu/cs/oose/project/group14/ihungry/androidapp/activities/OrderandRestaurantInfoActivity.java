package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.*;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;

import android.os.Bundle;
import android.app.Activity;
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

	private TextView tv_rest_name;
	private TextView tv_rest_addr;

	private ListView m_ListView;

	private MyListViewAdapter list_adapter;
	
	/* Menu and an arraylist of OrderItems */
	private edu.jhu.cs.oose.project.group14.ihungry.model.Menu menu_original;
	private List<OrderItem> menu_order = new ArrayList<OrderItem>();
	private AndroidClientModel a_model;
	
	
	private String rest_id;
	private String rest_name;
	private String rest_addr;

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

		rest_id = (String) bundle.getCharSequence("rest_id");
		rest_name = (String) bundle.getCharSequence("rest_name");
		rest_addr = (String) bundle.getCharSequence("rest_addr");
		tv_rest_name.setText(rest_name);
		tv_rest_addr.setText(rest_addr);

		m_ListView = (ListView) findViewById(R.id.list);
		// m_ListView.setAdapter(new
		// ArrayAdapter<String>(this,R.layout.list_item_simple, R.id.title,
		// menu_info2));
		// list_adapter = new MyListViewAdapter(this, menu_info2);

		/* Call ClientModel to retrieve a Menu given the rest_id */
		a_model = new AndroidClientModelImpl();
		menu_original = a_model.retrieveMenu(rest_id);
		
		
		for(int i=0; i<menu_original.getItems().size(); i++){
			Item item = (Item)menu_original.getItemAt(i);
			OrderItem orderItem = new OrderItem(item, 0); // quantity => 0
			menu_order.add(orderItem);
		}
		list_adapter = new MyListViewAdapter(this, menu_order);
		m_ListView.setAdapter(list_adapter);

		
		// Click event for single list row
		m_ListView.setOnItemClickListener(m_ListViewOnItemClickListener);
		((Button) findViewById(R.id.bt_reviewItem))
				.setOnClickListener(bt_reviewItem_Listener);

	}

	OnClickListener bt_reviewItem_Listener = new OnClickListener() {
		public void onClick(View v) {
			/* Pass to model to create a Order object
			 * and Send the Order object to the next activity.
			 */
			
			// Now test: send Order_Test to OrderReview activity
			List<OrderItem> orderitems = new ArrayList<OrderItem>();
			for (int i = 0; i< menu_order.size(); i++){
				OrderItem order_item = (OrderItem) menu_order.get(i);
				if(order_item.getQuantity() != 0){
					orderitems.add(order_item);
				}
			}
			
			// NEED MODIFYING
			Order order_t = a_model.createOrder("o001","c003","r009", 1, orderitems);
			
			Intent intent = new Intent(OrderandRestaurantInfoActivity.this, OrderReviewActivity.class);

			intent.putExtra("order_t", order_t);
			intent.putExtra("rest_name", rest_name);
			
			OrderandRestaurantInfoActivity.this.startActivityForResult(intent, ActivitySwitchSignals.ORDERREVIEW);
			
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == ActivitySwitchSignals.ORDERREVIEWCLOSESWH){
			Log.v("[RestaurantInfo onActivityResult]", "RestaurantInfo!!");

			setResult(ActivitySwitchSignals.RESTAURANTINFOCLOSESWH);
			this.finish();
		}
	}

	/**
	 * A call-back for when the user tap on a single row of the list => nothing should happen.
	 */
	OnItemClickListener m_ListViewOnItemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			OrderItem clickItem = (OrderItem) list_adapter.getItem(position);
			// clickItem.setPrice(999);
			// list_adapter.notifyDataSetChanged();
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
