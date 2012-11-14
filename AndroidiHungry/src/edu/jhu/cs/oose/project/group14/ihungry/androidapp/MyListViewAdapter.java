package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.Menu;
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
 * A customized ListViewAdapter for showing items on the list. It handles two buttons(+ -) clicking event.
 * @author SuNFloWer
 *
 */
public class MyListViewAdapter extends BaseAdapter {
/*
	static class ViewHolder{
		public TextView title; 
		public TextView rating;
		public TextView price;
//		public TextView quantity;
		public ImageView thumb_image;
		public Button btn_quantity;
	}
*/	
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	// public ImageLoader imageLoader;

	private TextView title;
	private TextView rating;
	private TextView price;
	private ImageView thumb_image;
	private TextView quantity;
	private Button btn_add;
	private Button btn_minus;
	//private List<ListMenuItem> menu_items; 
	private List<OrderItem> menu_order;


	public MyListViewAdapter(Activity a, List<OrderItem> menu_order_in) {
		activity = a;
		menu_order = menu_order_in;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            vi = inflater.inflate(R.layout.list_item_simple, null);
        
        title = (TextView) vi.findViewById(R.id.title); // title
		rating = (TextView) vi.findViewById(R.id.rating); // rating
		price = (TextView) vi.findViewById(R.id.price); // price
		thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
		quantity = (TextView) vi.findViewById(R.id.quantity); // quantity
		btn_add = (Button) vi.findViewById(R.id.btn_add); // quantity +
		btn_minus = (Button) vi.findViewById(R.id.btn_minus); // quantity -
		
		//title.setText(info[position]);
		OrderItem o_item = (OrderItem)menu_order.get(position);
		Item m_item = (Item)o_item.getItem();
		title.setText(m_item.getItemName());
		rating.setText("Rating "+m_item.getItemRating().getRating());
		price.setText("Price "+m_item.getItemPrice());
		quantity.setText("Quantity "+o_item.getQuantity());
		
		btn_add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				OrderItem clickItem = (OrderItem) mListView.getAdapter().getItem(pos);
				clickItem.addQuantity(1);
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				
	
			}
		});	
		
		btn_minus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				OrderItem clickItem = (OrderItem) mListView.getAdapter().getItem(pos);
				clickItem.minusQuantity(1);
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
			}
		});	
				
		return vi;
	}

}
