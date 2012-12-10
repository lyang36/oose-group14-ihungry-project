package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.List;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.CustomerAccountInfoCreator;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModel;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.AndroidClientModelImpl;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

/**
 * A Notification Service used to notify user their changed orders.
 * 
 * @author SuNFloWer
 * 
 */
public class NotifyService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;

	private NotifyServiceReceiver notifyServiceReceiver;

	private static final int MY_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification myNotification;

	//private int notifyCounter = 0;

	private AndroidClientModel clientModel;

	@Override
	public void onCreate() {
		notifyServiceReceiver = new NotifyServiceReceiver();

		/* Initialize AndroidClientModel */
		clientModel = new AndroidClientModelImpl(
				CustomerAccountInfoCreator.createAccountInfo(
						FileHandler.username_stored, FileHandler.pwd_stored));

		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//ToastDisplay.DisplayToastOnScr(NotifyService.this, "Notify Service: start command");

		List<Order> orders = clientModel.retrieveChangedOrders();

		if (orders.size() != 0) {

			// Get the first order's info
			Order order1 = (Order) orders.get(0);
			AccountInfo bus_accInfo = new AccountInfo();
			bus_accInfo.setId(order1.getRestID());
			ContactInfo rest_contact = clientModel
					.getRestaurantContactInfoSingle(bus_accInfo);
			String rest_name = rest_contact.getRealName();
			String changedStatus = order1.getStatusMeaning();

			// ToastDisplay.DisplayToastOnScr(NotifyService.this,
			// "Orders size: "
			// + orders.size());

			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(ACTION);
			registerReceiver(notifyServiceReceiver, intentFilter);

			// Send Notification
			notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			//notifyCounter++;
			myNotification = new Notification(R.drawable.ic_launcher,
					"iHungry Notification", System.currentTimeMillis());

			Context context = getApplicationContext();
			String notificationTitle = "Updated order available";
			String notificationText = "Your order from " + rest_name + ": "
					+ changedStatus;
			// Intent myIntent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse(myBlog));
			Intent myIntent = new Intent(NotifyService.this,
					OrderHistoryActivity.class);

			PendingIntent pendingIntent = PendingIntent.getActivity(
					getBaseContext(), 0, myIntent,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			myNotification.defaults |= Notification.DEFAULT_SOUND;
			myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
			myNotification.setLatestEventInfo(context, notificationTitle,
					notificationText, pendingIntent);
			notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
		}

		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		ToastDisplay.DisplayToastOnScr(NotifyService.this,
				"NotifyService.onDestroy");

		this.unregisterReceiver(notifyServiceReceiver);
		super.onDestroy();
	}

	/**
	 * A Service Receiver to get signals from other intents (or activities),
	 * such as STOP.
	 * 
	 * @author SuNFloWer
	 * 
	 */
	public class NotifyServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			int rqs = arg1.getIntExtra("RQS", 0);

			/* Stop the service */
			if (rqs == RQS_STOP_SERVICE) {
				stopSelf();
			}
		}
	}

}
