package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.CustomerAccountInfoCreator;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebView;

/**
 * This view is used to show the order history of the user's.
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
		
		clientModel = new AndroidClientModelImpl(CustomerAccountInfoCreator.createAccountInfo(
				FileHandler.username_stored, FileHandler.pwd_stored));
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadData(makeOrderHistoryHTML( clientModel.retrieveAllOrders() ), "text/html", "UTF-8");

	}

	private String makeOrderHistoryHTML(List<Order> orders_in) {
		// String HTML_str = "<html><body>Hello, world!</body></html>";

		Log.v("[orders size]",""+orders_in.size());
		
		String HTML_str = new String(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
						+ "<head>"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
						+ "<title>Order History</title>"
						+ "</head>"
						+

						"<body>"
						+ "<table width=\"301\" border=\"0\">");
		
		for(int i=0; i<orders_in.size(); i++){
			Order order_single = (Order)orders_in.get(i);
			HTML_str = HTML_str.concat( makeEachOrderHTML(order_single) );
		}
						
		HTML_str = HTML_str.concat(				
						"</table>" + 
						"</body>" + "</html>");

	//	Log.v("HTML", HTML_str+"");
		return HTML_str;
	}
	
	private String makeEachOrderHTML(Order order_single) {
		String HTML_str = new String("");
		AccountInfo bus_accInfo = new AccountInfo();
		bus_accInfo.setId(order_single.getRestID());
		ContactInfo rest_contact = clientModel.getRestaurantContactInfoSingle(bus_accInfo);
		
		HTML_str = HTML_str.concat( 
				"  <tr>"
		+ "    <td width=\"214\"><p><b>Order No.:</b> "+ order_single.getOrderID() +"</p>"
		+ "      <table width=\"294\" border=\"1\">"
		+ "       <tr>"
		+ "          <td width=\"229\" height=\"25\"><b>From:</b>  "+ rest_contact.getRealName() +"</td>"
		+ "        </tr>"
		+ "        <tr>"
		+ "          <td height=\"25\"><b>Order Placed:</b> "+ order_single.getDateString() +"</td>"
		+ "        </tr>"
		+ "        <tr>"
		+ "          <td height=\"25\"><b>Status:</b> "+ order_single.getStatusMeaning() +"</td>"
		+ "        </tr>"
		+ "        <tr>"
		+ "          <td height=\"25\"><b>Price:</b>  $"+ order_single.getTotalPrice() +"</td>"
		+ "        </tr>"
		+ "        <tr>"
		+ "          <td height=\"25\"><b>Item Name</b></td>"
		+ "          <td width=\"35\" height=\"25\"><b>Price</></td>"
		+ "          <td width=\"30\" height=\"25\"><b>Qty.</b></td>"
		+ "        </tr>");
		
		List<OrderItem> orderItems = (ArrayList<OrderItem>)order_single.getOrderItems();
		for(int i=0; i<orderItems.size(); i++){
			OrderItem o_item = (OrderItem)orderItems.get(i);
			HTML_str = HTML_str.concat( makeOrderItemHTML(o_item) );
		}
		
		HTML_str = HTML_str.concat(
		"      </table>"
		+ "    </td>"
		+ "  </tr>");
		
	//	Log.v("[makeEachOrderHTML]",""+HTML_str);
		return HTML_str;
	}
	
	private String makeOrderItemHTML(OrderItem orderItem) {
		Item item = (Item)orderItem.getItem();
		
		return
				"    <tr>"
		+ "          <td height=\"25\"><font size=\"2\">"+ item.getItemName() +"</font></td>"
		+ "          <td width=\"35\" height=\"25\"><font size=\"2\">"+ item.getItemPrice() +"</font></td>"
		+ "          <td width=\"30\" height=\"25\"><font size=\"2\">"+ orderItem.getQuantity() +"</font></td>"
		+ "        </tr>";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_order_history, menu);
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
