package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * An Alarm Service that starts NotifyService.
 * @author SuNFloWer
 *
 */
public class MyAlarmService extends Service {
	
	@Override
	public void onCreate() {
		ToastDisplay.DisplayToastOnScr(MyAlarmService.this,
				"Fetching Changed Orders Every 10 seconds");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Intent intent2 = new Intent(MyAlarmService.this, NotifyService.class);
		MyAlarmService.this.startService(intent2);		
	}
		
	@Override
	public void onDestroy() {

		Intent intent2 = new Intent();
		intent2.setAction(NotifyService.ACTION);
		intent2.putExtra("RQS", NotifyService.STOP_SERVICE);
		sendBroadcast(intent2);
		
		stopSelf();
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
}
