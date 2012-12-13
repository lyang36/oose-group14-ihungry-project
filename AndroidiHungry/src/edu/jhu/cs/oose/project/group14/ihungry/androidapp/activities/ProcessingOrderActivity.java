package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.CustomerAccountInfoCreator;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

/**
 * This view is used to show the processing orders of the user's.
 * 
 * @author SuNFloWer
 * 
 */
public class ProcessingOrderActivity extends Activity {

	private WebView webView;
	private AndroidClientModel clientModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_processing_order);

		clientModel = new AndroidClientModelImpl(
				CustomerAccountInfoCreator.createAccountInfo(
						FileHandler.username_stored, FileHandler.pwd_stored));

		webView = (WebView) findViewById(R.id.webView1);
		webView.loadData(OrderHistoryTabLayoutActivity.makeOrderHistoryHTML(clientModel.retrieveOrders(Order.STATUS_UNDERPROCING, -1), clientModel),
				"text/html", "UTF-8");

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
