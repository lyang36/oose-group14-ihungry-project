package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities.OrderReviewActivity;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModel;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModelImpl;
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
 * A customized ListViewAdapter for showing items on the list in the Favourites View. It handles one buttons(Order) clicking event.
 * @author SuNFloWer
 *
 */
public class FavouriteListViewAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;

	private TextView title;
	private TextView rating;
	private TextView price;
	private ImageView thumb_image;
	private TextView quantity;
	private Button btn_order;
	private List<OrderItem> menu_order;
	
	private AndroidClientModel a_model;
	private Context c;

	public FavouriteListViewAdapter(Activity a, List<OrderItem> menu_order_in) {
		activity = a;
		menu_order = menu_order_in;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		a_model = new AndroidClientModelImpl(CustomerAccountInfoCreator.createAccountInfo(
				FileHandler.username_stored, FileHandler.pwd_stored));
		
		c=a.getApplicationContext();
	}
	
	/**
	 * Return the total number of rows in the list (size of menu_order).
	 */
	public int getCount() {
		return menu_order.size();
	}

	/**
	 * Return the item at a specific position.
	 */
	public Object getItem(int position) {
		return menu_order.get(position);
	}
	
	/**
	 * Get the TextView for displaying price.
	 * @param position
	 * @return
	 */
	public TextView getPriceView(int position){
		return price;
	}

	/**
	 * Display the Item id at one specific position.
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
            vi = inflater.inflate(R.layout.list_item_one_click, null);
        
        title = (TextView) vi.findViewById(R.id.title); // title
		rating = (TextView) vi.findViewById(R.id.rating); // rating
		price = (TextView) vi.findViewById(R.id.price); // price
		thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
		quantity = (TextView) vi.findViewById(R.id.quantity); // quantity
		btn_order = (Button) vi.findViewById(R.id.btn_one_click); // Order
		
		//title.setText(info[position]);
		OrderItem o_item = (OrderItem)menu_order.get(position);
		Item m_item = (Item)o_item.getItem();
		title.setText(m_item.getItemName());
		rating.setText("Rating "+m_item.getItemRating().getRating());
		price.setText("Price "+m_item.getItemPrice());
		quantity.setText("Quantity "+o_item.getQuantity());
		
		btn_order.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				OrderItem clickItem = (OrderItem) mListView.getAdapter().getItem(pos);
				
				Log.v("[Favourite listview adapter]","btn_order clicked:"+clickItem.getItem().getItemName());

				// Create a order
				List<OrderItem> order_items = new ArrayList<OrderItem>();
				order_items.add(clickItem);

				String restID = "cfc1b016980afb5569daf3dc"; // New China II
				Order order = a_model.createOrder(restID, Order.STATUS_UNDERPROCING, order_items);
				boolean submit_result = a_model.submitOrder(order);
				
				if(submit_result)
					ToastDisplay.DisplayToastOnScr(c, "Order Submitted Successful !");
				else 
					ToastDisplay.DisplayToastOnScr(c, "Order Submitted Failed !");
				
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				
			}
		});	
		
	
				
		return vi;
	}

}
