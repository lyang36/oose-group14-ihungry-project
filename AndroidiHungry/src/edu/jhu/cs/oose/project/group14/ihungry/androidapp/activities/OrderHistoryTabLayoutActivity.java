package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.CustomerAccountInfoCreator;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModel;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModelImpl;
import edu.jhu.cs.oose.project.group14.ihungry.model.AccountInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.ContactInfo;
import edu.jhu.cs.oose.project.group14.ihungry.model.Item;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;
import edu.jhu.cs.oose.project.group14.ihungry.model.OrderItem;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * A TabLayout Activity acting a tab host that contains five tabs inside:<p>
 * Processing, Confirmed, Rejected, Finished, Cancelled Order History Tabs.
 * @author SuNFloWer
 *
 */
@SuppressWarnings("deprecation")
public class OrderHistoryTabLayoutActivity extends TabActivity {
	// private AndroidClientModel clientModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history_tab_layout);

		TabHost tabHost = getTabHost();

		// Tab for Processing Orders
		TabSpec processing_spec = tabHost.newTabSpec("Processing");
		processing_spec.setIndicator("Processing",
				getResources().getDrawable(R.drawable.processing));
		Intent i = new Intent(this, ProcessingOrderActivity.class);
		processing_spec.setContent(i);

		// Tab for Confirmed Orders
		TabSpec confirmed_spec = tabHost.newTabSpec("Confirmed");
		confirmed_spec.setIndicator("Confirmed",
				getResources().getDrawable(R.drawable.confirmed));
		i = new Intent(this, ConfirmedOrderActivity.class);
		confirmed_spec.setContent(i);

		// Tab for Rejected Orders
		TabSpec rejected_spec = tabHost.newTabSpec("Rejected");
		rejected_spec.setIndicator("Rejected",
				getResources().getDrawable(R.drawable.rejected));
		i = new Intent(this, RejectedOrderActivity.class);
		rejected_spec.setContent(i);

		// Tab for Finished Orders
		TabSpec finished_spec = tabHost.newTabSpec("Finished");
		finished_spec.setIndicator("Finished",
				getResources().getDrawable(R.drawable.finished));
		i = new Intent(this, OrderHistoryActivity.class);
		finished_spec.setContent(i);

		// Tab for Cancelled Orders
		TabSpec cancelled_spec = tabHost.newTabSpec("Cancelled");
		cancelled_spec.setIndicator("Cancelled",
				getResources().getDrawable(R.drawable.cancelled));
		i = new Intent(this, CancelledOrderActivity.class);
		cancelled_spec.setContent(i);

		// Adding all TabSpec to TabHost
		tabHost.addTab(processing_spec);
		tabHost.addTab(confirmed_spec);
		tabHost.addTab(rejected_spec);
		tabHost.addTab(finished_spec);
		tabHost.addTab(cancelled_spec);

	}

	/**
	 * Make the HTML webpage based on the information of input order.
	 * 
	 * @param orders_in
	 *            input Order instance.
	 * @param model
	 *            the client model
	 * @return
	 */
	public static String makeOrderHistoryHTML(List<Order> orders_in,
			AndroidClientModel model) {
		// String HTML_str = "<html><body>Hello, world!</body></html>";

		Log.v("[orders size]", "" + orders_in.size());

		String HTML_str = new String(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
						+ "<head>"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
						+ "<title>Order History</title>" + "</head>" +

						"<body>" + "<table width=\"301\" border=\"0\">");

		for (int i = 0; i < orders_in.size(); i++) {
			Order order_single = (Order) orders_in.get(i);
			HTML_str = HTML_str.concat(makeEachOrderHTML(order_single, model));
		}

		HTML_str = HTML_str.concat("</table>" + "</body>" + "</html>");

		// Log.v("HTML", HTML_str+"");
		return HTML_str;
	}

	/**
	 * Make the HTML code given one single order information.
	 * 
	 * @param order_single
	 * @param model
	 *            the client model
	 * @return
	 */
	public static String makeEachOrderHTML(Order order_single,
			AndroidClientModel model) {
		String HTML_str = new String("");
		AccountInfo bus_accInfo = new AccountInfo();
		bus_accInfo.setId(order_single.getRestID());
		ContactInfo rest_contact = model
				.getRestaurantContactInfoSingle(bus_accInfo);

		HTML_str = HTML_str.concat("  <tr>"
				+ "    <td width=\"214\"><p><b>Order No.:</b> "
				+ order_single.getOrderID()
				+ "</p>"
				+ "      <table width=\"294\" border=\"1\">"
				+ "       <tr>"
				+ "          <td width=\"229\" height=\"25\"><b>From:</b>  "
				+ rest_contact.getRealName()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr>"
				+ "          <td height=\"25\"><b>Order Placed:</b> "
				+ order_single.getDateString()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr>"
				+ "          <td height=\"25\"><b>Status:</b> "
				+ order_single.getStatusMeaning()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr>"
				+ "          <td height=\"25\"><b>Price:</b>  $"
				+ order_single.getTotalPrice()
				+ "</td>"
				+ "        </tr>"
				+ "        <tr>"
				+ "          <td height=\"25\"><b>Item Name</b></td>"
				+ "          <td width=\"35\" height=\"25\"><b>Price</></td>"
				+ "          <td width=\"30\" height=\"25\"><b>Qty.</b></td>"
				+ "        </tr>");

		List<OrderItem> orderItems = (ArrayList<OrderItem>) order_single
				.getOrderItems();
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem o_item = (OrderItem) orderItems.get(i);
			HTML_str = HTML_str.concat(makeOrderItemHTML(o_item));
		}

		HTML_str = HTML_str.concat("      </table>" + "    </td>" + "  </tr>");

		// Log.v("[makeEachOrderHTML]",""+HTML_str);
		return HTML_str;
	}

	/**
	 * Make the HTML code based on the single input OrderItem instance.
	 * 
	 * @param orderItem
	 * @return
	 */
	public static String makeOrderItemHTML(OrderItem orderItem) {
		Item item = (Item) orderItem.getItem();

		return "    <tr>" + "          <td height=\"25\"><font size=\"2\">"
				+ item.getItemName() + "</font></td>"
				+ "          <td width=\"35\" height=\"25\"><font size=\"2\">"
				+ item.getItemPrice() + "</font></td>"
				+ "          <td width=\"30\" height=\"25\"><font size=\"2\">"
				+ orderItem.getQuantity() + "</font></td>" + "        </tr>";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_order_history_tab_layout,
				menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// Called by children
		setResult(ActivitySwitchSignals.MAINSCREENSWH);
		finish();
	}

}
