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
 * This view is used to show the finished orders of the user's.
 * 
 * @author SuNFloWer
 * 
 */
public class OrderHistoryActivity extends Activity {

	private WebView webView;
	private AndroidClientModel clientModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);

		clientModel = new AndroidClientModelImpl(
				CustomerAccountInfoCreator.createAccountInfo(
						FileHandler.username_stored, FileHandler.pwd_stored));

		webView = (WebView) findViewById(R.id.webView1);
		webView.loadData(OrderHistoryTabLayoutActivity.makeOrderHistoryHTML(clientModel.retrieveOrders(Order.STATUS_FINISHED, -1), clientModel),
				"text/html", "UTF-8");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_order_history, menu);
		return true;
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // do
	 * something on back. setResult(ActivitySwitchSignals.MAINSCREENSWH);
	 * finish(); return true; } return super.onKeyDown(keyCode, event); }
	 */

	@Override
	public void onBackPressed() {
		// Let OrderHistoryTabLayout handle this event.
		this.getParent().onBackPressed();
	}

}
