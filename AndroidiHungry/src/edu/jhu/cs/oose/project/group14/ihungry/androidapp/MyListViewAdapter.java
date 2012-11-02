package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.androidihungry.R;

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

	private String[] info;
	
	private TextView title;
	private TextView rating;
	private TextView price;
	private ImageView thumb_image;
	private TextView quantity;
	private Button btn_add;
	private Button btn_minus;
	private ArrayList<ListMenuItem> menu_items; 
	

/*	public MyListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}
*/
	public MyListViewAdapter(Activity a, String[] info_in) {
		activity = a;
		info = info_in;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public MyListViewAdapter(Activity a, ArrayList<ListMenuItem> menu_items_in){
		activity  = a;
		menu_items = menu_items_in;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return menu_items.size();
		// return data.size();
	}

	public Object getItem(int position) {
	//	return position;
		return menu_items.get(position);
	}
	
	public TextView getPriceView(int position){
		return price;
	}

	public long getItemId(int position) {
		return position;
	}

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
		ListMenuItem m_item = (ListMenuItem)menu_items.get(position);
		title.setText(m_item.title);
		rating.setText("Rating "+m_item.rating);
		price.setText("Price "+m_item.price);
		quantity.setText("Quantity "+m_item.quantity);
		
		btn_add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				ListMenuItem clickItem = (ListMenuItem) mListView.getAdapter().getItem(pos);
			//	clickItem.setPrice(888);
				clickItem.addQuantity(1);
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				
	
			}
		});	
		
		btn_minus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListView mListView = (ListView) v.getParent().getParent();
				final int pos = mListView.getPositionForView((View) v.getParent());
				ListMenuItem clickItem = (ListMenuItem) mListView.getAdapter().getItem(pos);
			//	clickItem.setPrice(888);
				clickItem.minusQuantity(1);
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				
	
			}
		});	
				
		/*	View vi = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_item_simple, null);
			holder = new ViewHolder();
		
			holder.title = (TextView) vi.findViewById(R.id.title); // title
			holder.rating = (TextView) vi.findViewById(R.id.rating); // rating
			holder.price = (TextView) vi.findViewById(R.id.price); // price
			holder.thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
			holder.btn_quantity = (Button) vi.findViewById(R.id.btn_quantity); // quantity
			vi.setTag(holder);																
		}else{
			holder = (ViewHolder)vi.getTag();
		}

		// HashMap<String, String> song = new HashMap<String, String>();
		// song = data.get(position);
		
		Log.v("[Adapter getView]", position + " ");
		// Setting all values in listview
		
		holder.title.setText(info[position]);
	//	holder.btn_quantity.setOnTouchListener(new OnTouchListener() {
		holder.btn_quantity.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			//	ListView mListView = (ListView) v.getParent().getParent();
			//	final int pos = mListView.getPositionForView((View) v.getParent());
				final int pos = (Integer)v.getTag();
				Log.v("[Adapter getView on click] clicked", pos + "");
				ToastDisplay.DisplayToastOnScr(v.getContext(), "Tap " + pos);
		
				ViewHolder holder_new = (ViewHolder)mListView.getTag(pos);
				TextView price_found = holder_new.price;
			//	TextView price_found = (TextView) con_view.findViewById(R.id.price);
				price_found.setText("9999");
				
				//ViewHolder holdernew = (ViewHolder)getItem(pos);
	//			ViewHolder holder_new = (ViewHolder)v.getTag();
			//	ViewHolder holdernew = (ViewHolder) mListView.getTag();
			//	TextView price_found = holdernew.price;
			//	price_found.setText("9999");
	//			holder_new.price.setText("9999");
	
				
			//	holder.price.setText("9999");
			}
		});		
		
		holder.btn_quantity.setTag(position);
		holder.price.setTag(position);
		
		//	holder.btn_quantity.setTag(info.)
		
		*/
		
		
		return vi;
	}

}

/*		int firstPosition = mListView.getFirstVisiblePosition()
- mListView.getHeaderViewsCount();
int wantedChild = pos - firstPosition;
if (wantedChild < 0 || wantedChild >= mListView.getChildCount()) {
Log.w("[Adapter getView on click]child",
	"Unable to get view for desired position, because it's not being displayed on screen.");
} else {

// View con_view = (View)mListView.getItemAtPosition(pos);
View con_view = (View) mListView.getChildAt(pos);
// View con_view =
// (View)mListView.getAdapter().getItem(pos);

TextView price_found = (TextView) con_view
	.findViewById(R.id.price);
price_found.setText("9999");
}*/

// View con_view = (View)mListView.getItemAtPosition(pos);
//View con_view = (View) mListView.getChildAt(pos-mListView.getHeaderViewsCount());
// View con_view =
// (View)mListView.getAdapter().getItem(pos);

//View con_view = (View) mListView.getChildAt(pos);
// title.setText(song.get(CustomizedListView.KEY_TITLE));
// artist.setText(song.get(CustomizedListView.KEY_ARTIST));
// duration.setText(song.get(CustomizedListView.KEY_DURATION));
// imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL),
// thumb_image);
