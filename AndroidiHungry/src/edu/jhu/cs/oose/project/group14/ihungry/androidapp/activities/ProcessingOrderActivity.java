package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;
import java.util.*;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.CustomerAccountInfoCreator;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.OrderWithCancelListViewAdapter;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ListView;

/**
 * This view is used to show the processing orders of the user's.
 * 
 * @author SuNFloWer
 * 
 */
public class ProcessingOrderActivity extends Activity {
	private ListView m_ListView;
	private OrderWithCancelListViewAdapter list_adapter;
	private List<Order> process_orders = new ArrayList<Order>();
	
	//private WebView webView;
	private AndroidClientModel clientModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_processing_order);

		m_ListView = (ListView) findViewById(R.id.list);
		
		clientModel = new AndroidClientModelImpl(
				CustomerAccountInfoCreator.createAccountInfo(
						FileHandler.username_stored, FileHandler.pwd_stored));
		
		process_orders = clientModel.retrieveOrders(Order.STATUS_UNDERPROCING, -1);
		
		list_adapter = new OrderWithCancelListViewAdapter(this, process_orders);
		m_ListView.setAdapter(list_adapter);
		
		//webView = (WebView) findViewById(R.id.webView1);
		//webView.loadData(OrderHistoryTabLayoutActivity.makeOrderHistoryHTML(clientModel.retrieveOrders(Order.STATUS_UNDERPROCING, -1), clientModel),
		//		"text/html", "UTF-8");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_processing_order, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		this.getParent().onBackPressed();
	}

}
