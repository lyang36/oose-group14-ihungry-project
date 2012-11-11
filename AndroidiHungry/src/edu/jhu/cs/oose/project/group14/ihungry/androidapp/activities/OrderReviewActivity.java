package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.androidihungry.R;
import com.example.androidihungry.R.layout;
import com.example.androidihungry.R.menu;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ListMenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.*;

/**
 * This view shows the detailed ordered items.
 * 
 * @author SuNFloWer
 * 
 */
public class OrderReviewActivity extends Activity {

	private WebView webView;
	private Order_Test order_t;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_review);

		((Button) findViewById(R.id.btn_submit))
				.setOnClickListener(btn_submit_Listener);

		/* Get bundle info from OrderandRestaurantInfoActivity */
		Bundle bundle = this.getIntent().getExtras();
		order_t = (Order_Test) bundle.getSerializable("order_t");
		String rest_name = (String) bundle.getCharSequence("rest_name");
		order_t.printOrderItems();
		Log.v("OrderReview", rest_name + "");

		webView = (WebView) findViewById(R.id.webView1);
		/* Scale to fit HTML page */
		// webView.getSettings().setLoadWithOverviewMode(true);
		// webView.getSettings().setUseWideViewPort(true);
		// webView.getSettings().setBuiltInZoomControls(true);
		// fit the width of screen
		// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		// remove a weird white line on the right size
		// webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.loadData(makeOrderHTML(rest_name, order_t), "text/html",
				"UTF-8");

	}

	private String makeOrderHTML(String rest_name, Order_Test order_t) {
		// String HTML_str = "<html><body>Hello, world!</body></html>";
		String HTML_str = new String(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
						+ "<head>"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
						+ "<title>Order Review</title>"
						+ "</head>"
						+

						"<body>"
						+ "<table width=\"301\" border=\"1\">"
						+ "  <tr>"
						+ "    <td height=\"30\" width=\"214\">Item Name</td>"
						+ "    <td height=\"30\" width=\"35\">Price</td>"
						+ "    <td height=\"30\" width=\"30\">Qty.</td>"
						+ "  </tr>");

		ArrayList<ListMenuItem> orderitems = order_t.getOrderItems();
		for (int i = 0; i < orderitems.size(); i++) {
			ListMenuItem item = orderitems.get(i);
			HTML_str = HTML_str.concat(insertRowHTML(item));
		}
		HTML_str = HTML_str.concat(getTabletail());
		HTML_str = HTML_str.concat("<p>Total price: $"
				+ order_t.getTotalPrice() + "</p>");
		HTML_str = HTML_str.concat(getHTMLtail());

		return HTML_str;
	}

	/**
	 * Insert the HTML string of each row in table.
	 * 
	 * @param item
	 * @return
	 */
	private String insertRowHTML(ListMenuItem item) {

		return "  <tr>" + "    <td height=\"50\"><font size=\"2\">"
				+ item.getTitle() + "</font></td>" + "    <td height=\"50\">"
				+ item.getPrice() + "</td>" + "    <td>" + item.getQuantity()
				+ "</td>" + "  </tr>";

	}

	/**
	 * Return /table
	 * 
	 * @return
	 */
	private String getTabletail() {
		return "</table>";
	}

	/**
	 * Return /body /html
	 * 
	 * @return
	 */
	private String getHTMLtail() {
		return "</body>" + "</html>";
	}

	OnClickListener btn_submit_Listener = new OnClickListener() {
		public void onClick(View v) {
			/*
			 * Submit the order.
			 */

			setResult(ActivitySwitchSignals.ORDERREVIEWCLOSESWH);

			finish();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_order_review, menu);
		return true;
	}
}
