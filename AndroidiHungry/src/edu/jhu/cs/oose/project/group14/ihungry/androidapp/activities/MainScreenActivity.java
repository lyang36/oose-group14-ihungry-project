package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.view.View.*;
import android.widget.*;

/**
 * This is the main screen of the app including four buttons: nearby, aboutme, favourites, and orderhistory.
 * @author SuNFloWer
 *
 */
public class MainScreenActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);

		// Hook up button presses to the appropriate event handler.
		((ImageButton) findViewById(R.id.imgbtn_Nearby)).setOnClickListener(imgbtn_Nearby_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Aboutme)).setOnClickListener(imgbtn_Aboutme_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Favourite)).setOnClickListener(imgbtn_Favourite_Listener);
		((ImageButton) findViewById(R.id.imgbtn_Orderhistory)).setOnClickListener(imgbtn_Orderhistory_Listener);

	}
	

	/**
	 * A call-back for when the user presses the nearby button. A signal to switch to Nearby Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Nearby_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.NEARBYSWH);
			finish();
		}
	};
	
	/**
	 * A call-back for when the user presses the aboutme button. A signal to switch to Aboutme Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Aboutme_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.ABOUTMESWH);
			finish();
		}
	};
	
	/**
	 * A call-back for when the user presses the favourite button. A signal to switch to Favourite Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Favourite_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.FAVOURITESSWH);
			finish();
		}
	};
	
	/**
	 * A call-back for when the user presses the orderhistory button. A signal to switch to Orderhistory Screen is sent to ActivityController
	 */
	OnClickListener imgbtn_Orderhistory_Listener = new OnClickListener() {
		public void onClick(View v) {
			setResult(ActivitySwitchSignals.ORDERHISTORYSWH);
			finish();
		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_controller, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			
			setResult(ActivitySwitchSignals.QUIT);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
