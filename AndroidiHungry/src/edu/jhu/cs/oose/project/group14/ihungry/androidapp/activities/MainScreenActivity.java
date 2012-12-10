package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.util.Calendar;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.*;
import android.view.View.*;
import android.widget.*;

/**
 * This is the main screen of the app including four buttons: nearby, aboutme,
 * favourites, and orderhistory.
 * 
 * @author SuNFloWer
 * 
 */
public class MainScreenActivity extends Activity {

	private int tapBackCounter = 0;

	/* ......... Notify Service ........... */
	private PendingIntent pendingIntent;
	private AlarmManager alarmManager;
	private final int updateInterval = 20; //in seconds
	private final int MY_ALARM_SERVICE_PROCESS_ID = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);

		/* ........ Notify Service -- AlarmManager .......... */
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// stopNotifyService();
		// this.getApplicationContext().bindService(NotifyService.class, conn, flags)
		
		if( isMyServiceRunning() ){
			// Should reset the alarm
			ToastDisplay.DisplayToastOnScr(MainScreenActivity.this, "Service already running!");
		} else{
			ToastDisplay.DisplayToastOnScr(MainScreenActivity.this, "Service not running - start new one!");
			startNotifyService();
		}
		
		// Hook up button presses to the appropriate event handler.
		((ImageButton) findViewById(R.id.imgbtn_Nearby))
				.setOnClickListener(imgbtn_Nearby_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Aboutme))
				.setOnClickListener(imgbtn_Aboutme_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Favourite))
				.setOnClickListener(imgbtn_Favourite_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Orderhistory))
				.setOnClickListener(imgbtn_Orderhistory_Listener);

	}

	/**
	 * A call-back for when the user presses the nearby button. A signal to
	 * switch to Nearby Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Nearby_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.NEARBYSWH);
			finish();
		}
	};

	/**
	 * A call-back for when the user presses the aboutme button. A signal to
	 * switch to Aboutme Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Aboutme_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.ABOUTMESWH);
			finish();
		}
	};

	/**
	 * A call-back for when the user presses the favourite button. A signal to
	 * switch to Favourite Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Favourite_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.FAVOURITESSWH);
			finish();
		}
	};

	/**
	 * A call-back for when the user presses the orderhistory button. A signal
	 * to switch to Orderhistory Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Orderhistory_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.ORDERHISTORYSWH);
			finish();
		}
	};

	/**
	 * Check whether the notification service is running.
	 * @return
	 */
	public boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (MyAlarmService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Start the background notification service and executed every interval of time.
	 */
	public void startNotifyService() {

		Intent myIntent = new Intent(MainScreenActivity.this,
				MyAlarmService.class);
		pendingIntent = PendingIntent.getService(MainScreenActivity.this, MY_ALARM_SERVICE_PROCESS_ID,
				myIntent, 0);

		/*long currentTimeMillis = System.currentTimeMillis();
		// Notification Interval 
		long updateIntervalTimeMillis = updateInterval * DateUtils.SECOND_IN_MILLIS;
		long nextUpdateTimeMillis = currentTimeMillis
				+ updateIntervalTimeMillis;
		Time nextUpdateTime = new Time();
		nextUpdateTime.set(nextUpdateTimeMillis);
		 */
		
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTimeInMillis(System.currentTimeMillis());
		 //calendar.add(Calendar.SECOND, updateInterval);
		 
		// alarmManager.set(AlarmManager.RTC_WAKEUP, nextUpdateTimeMillis,
		// pendingIntent);

		/* Service is waken up and executed every interval of time */
		//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		//		nextUpdateTimeMillis, updateIntervalTimeMillis, pendingIntent);
		 
		 alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				 calendar.getTimeInMillis(), updateInterval*1000, pendingIntent);
		 
		// alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		// calendar.getTimeInMillis(), updateInterval*1000, pendingIntent);
	}

	/**
	 * When the application exits, stop the service.<p>
	 * Otherwise, when the client press home button as the application's running, the service would not be stopped.
	 */
	public void stopNotifyService() {
		/*
		 * Intent intent = new Intent(); intent.setAction(NotifyService.ACTION);
		 * intent.putExtra("RQS", NotifyService.STOP_SERVICE);
		 * sendBroadcast(intent);
		 */
		
		Intent intentstop = new Intent(MainScreenActivity.this,
				MyAlarmService.class);
		PendingIntent senderstop = PendingIntent.getService(MainScreenActivity.this, MY_ALARM_SERVICE_PROCESS_ID,
				intentstop, 0);
		alarmManager.cancel(senderstop);
		stopService(new Intent(MainScreenActivity.this, MyAlarmService.class));

		ToastDisplay.DisplayToastOnScr(MainScreenActivity.this, "Fetch STOP");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_controller, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			tapBackCounter++;

			if (tapBackCounter == 1) {
				ToastDisplay.DisplayToastOnScr(MainScreenActivity.this,
						"Tap again to quit iHungry");
			} else {
				/* Stop the service when exiting the application */
				stopNotifyService();

				setResult(ActivitySwitchSignals.QUIT);
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
