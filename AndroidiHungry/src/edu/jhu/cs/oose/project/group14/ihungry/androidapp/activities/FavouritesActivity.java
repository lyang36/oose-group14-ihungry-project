package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FavouriteListViewAdapter;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModel;
import edu.jhu.cs.oose.project.group14.ihungry.model.Album;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;
import edu.jhu.cs.oose.project.group14.ihungry.model.Rating;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ListView;

/**
 * This view is for showing the user's favourite items/restaurants.
 * @author SuNFloWer
 *
 */
public class FavouritesActivity extends Activity {
	
	private ListView m_ListView;
	private FavouriteListViewAdapter list_adapter;
	private List<OrderItem> order_items = new ArrayList<OrderItem>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        
		m_ListView = (ListView) findViewById(R.id.list);
		
		Item item1 = new Item("i_001","Chicken with Broccoli", 4.5, new Rating(4.0, 10), new Album());
		OrderItem order_item1 = new OrderItem(item1, 1);
		
		Item item2 = new Item("i_002","B-B-Q Spare Ribs", 5.25, new Rating(3.95, 12), new Album());
		OrderItem order_item2 = new OrderItem(item2, 1);
		
		order_items.add(order_item1);
		order_items.add(order_item2);
		
		list_adapter = new FavouriteListViewAdapter(this, order_items);
		m_ListView.setAdapter(list_adapter);

    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favourites, menu);
        return true;
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			setResult(ActivitySwitchSignals.MAINSCREENSWH);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
