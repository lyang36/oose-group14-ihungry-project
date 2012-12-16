package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities.OrderReviewActivity;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModel;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModelImpl;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A customized ListViewAdapter for showing items on the list in the History Order Processing View.
 * @author SuNFloWer
 *
 */
public class OrderWithCancelListViewAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;

	private TextView tv_orderno;
	private TextView tv_restaurant_name;
	private TextView tv_order_date;
	private TextView tv_order_status;
	private TextView tv_total_price;
	private TextView tv_items;
	private TextView tv_item_prices;
	private TextView tv_item_quantities;
	private Button btn_cancel;
	
	private List<Order> orders;
	
	private AndroidClientModel a_model;
	private Context c;

	public OrderWithCancelListViewAdapter(Activity a, List<Order> orders_in) {
		activity = a;
		orders = orders_in;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		a_model = new AndroidClientModelImpl(CustomerAccountInfoCreator.createAccountInfo(
				FileHandler.username_stored, FileHandler.pwd_stored));
		
		c=a.getApplicationContext();
	}
	
	/**
	 * Return the total number of rows in the list (size of orders).
	 */
	public int getCount() {
		return orders.size();
	}

	/**
	 * Return the order at a specific position.
	 */
	public Object getItem(int position) {
		return orders.get(position);
	}

	
	/*
	 * Get the TextView for displaying price.
	 * @param position
	 * @return
	 
	public TextView getPriceView(int position){
		return price;
	}*/

	/**
	 * Display the List Item id at one specific position.
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * This function is responsible for displaying info on the list and handles button tapping events.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_order_item, null);
        
        tv_orderno = (TextView) vi.findViewById(R.id.tv_orderno); 
        tv_restaurant_name = (TextView) vi.findViewById(R.id.tv_restaurant_name);
        tv_order_date = (TextView) vi.findViewById(R.id.tv_order_date);
    	tv_order_status = (TextView) vi.findViewById(R.id.tv_order_status);
    	tv_total_price = (TextView) vi.findViewById(R.id.tv_total_price);
    	tv_items = (TextView) vi.findViewById(R.id.tv_items);
    	tv_item_prices = (TextView) vi.findViewById(R.id.tv_item_prices);
    	tv_item_quantities = (TextView) vi.findViewById(R.id.tv_item_quantities);		
		btn_cancel = (Button) vi.findViewById(R.id.btn_cancel); 

    	Order order_single = (Order)orders.get(position);
    	AccountInfo bus_accInfo = new AccountInfo();
		bus_accInfo.setId(order_single.getRestID());
		ContactInfo rest_contact = a_model
				.getRestaurantContactInfoSingle(bus_accInfo);
    	tv_orderno.setText(order_single.getOrderID());
    	tv_restaurant_name.setText(rest_contact.getRealName());
    	tv_order_date.setText(order_single.getDateString());
    	tv_order_status.setText(order_single.getStatusMeaning());
    	tv_total_price.setText("$"+order_single.getTotalPrice());
    	tv_items.setText("");
    	tv_item_prices.setText("");
    	tv_item_quantities.setText("");
    	
    	List<OrderItem> orderItems = (ArrayList<OrderItem>) order_single
				.getOrderItems();
    	int order_item_size = orderItems.size();
    	tv_items.setMaxLines(order_item_size);

    	for (int i = 0; i < order_item_size; i++) {
			OrderItem o_item = (OrderItem) orderItems.get(i);
			String tmp_text_names = tv_items.getText().toString();
			String tmp_text_prices = tv_item_prices.getText().toString();
			String tmp_text_quantities = tv_item_quantities.getText().toString();
			
			Item item = (Item) o_item.getItem();
			String item_name = item.getItemName();
			String item_price = item.getItemPrice()+"";
			String item_quantity = o_item.getQuantity()+"";
			if(i==0){
				tv_items.setText(item_name);
				tv_item_prices.setText(item_price);
				tv_item_quantities.setText(item_quantity);
			}else{
				tv_items.setText(tmp_text_names +"\n"+ item_name);
				tv_item_prices.setText(tmp_text_prices +"\n"+ item_price);
				tv_item_quantities.setText(tmp_text_quantities +"\n"+ item_quantity);
			}
    	}
    	
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				Order clickOrder = (Order) mListView.getAdapter().getItem(pos);
				
				Log.v("[Process orders listview adapter]","btn_order clicked:"+clickOrder.getOrderID());

				// Cancel the order
				clickOrder.setStatus(Order.STATUS_CANCELLED);
				
				
				//List<OrderItem> order_items = new ArrayList<OrderItem>();
				//order_items.add(clickItem);

				//String restID = "cfc1b016980afb5569daf3dc"; // New China II
				//Order order = a_model.createOrder(restID, Order.STATUS_UNDERPROCING, order_items);
				boolean cancel_result = a_model.updateOrder(clickOrder);
				
				if(cancel_result)
					ToastDisplay.DisplayToastOnScr(c, "Order Cancelled Successful !");
				else 
					ToastDisplay.DisplayToastOnScr(c, "Order Cancelled Failed !");
				
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				
			}
		});
				
		return vi;
	}

}
