package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import java.io.*;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.*;

/**
 * This activity is responsible for view/activity transition.
 * 
 * @author SuNFloWer
 * 
 */
public class ActivityController extends Activity {

	Intent intent_i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * First activity -> login page
		 * 
		 * If have username and pwd , then auto fill the blank Otherwise, leave
		 * the blank empty (User will prompt to sign up)
		 */
		FileHandler.saveFile( this,
				FileHandler.f_userinfo,
				"szhao12||12345||Shang Zhao||Male||911-911-9119||Johns Hopkins University, Baltimore, MD, 21218");

		// Load the first Screen / Activity
		intent_i = new Intent(getApplicationContext(), LoginActivity.class);
		startActivityForResult(intent_i, ActivitySwitchSignals.LOGIN);

	}

	/**
	 * Function to read the result from newly created activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.v("[ActivityController] Test", requestCode + " " + resultCode);

		this.switchSceenOnSwhCode(intent_i, resultCode);

		/*
		switch (requestCode) {
		case ActivitySwitchSignals.LOGIN:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		case ActivitySwitchSignals.MAINSCREEN:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		case ActivitySwitchSignals.NEARBY:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		case ActivitySwitchSignals.ABOUTME:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		case ActivitySwitchSignals.FAVOURITES:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		case ActivitySwitchSignals.ORDERHISTORY:
			this.switchSceenOnSwhCode(intent_i, resultCode);
			break;
		default:
			break;
		}*/
	}

	/**
	 * Switch to the specific activity based on the switch code.
	 * 
	 * @param i
	 * @param swhCode
	 */
	private void switchSceenOnSwhCode(Intent i, int swhCode) {
		switch (swhCode) {
		case ActivitySwitchSignals.QUIT:
			Log.v("[ActivityController]", "Finish");
			this.finish();
			break;
		case ActivitySwitchSignals.LOGINSWH:
			i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.LOGIN);
			break;
		case ActivitySwitchSignals.MAINSCREENSWH:
			i = new Intent(getApplicationContext(), MainScreenActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.MAINSCREEN);
			break;
		case ActivitySwitchSignals.NEARBYSWH:
			i = new Intent(getApplicationContext(), NearbyActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.NEARBY);
			break;
		case ActivitySwitchSignals.ABOUTMESWH:
			i = new Intent(getApplicationContext(), AboutmeActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.ABOUTME);
			break;
		case ActivitySwitchSignals.FAVOURITESSWH:
			i = new Intent(getApplicationContext(), FavouritesActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.FAVOURITES);
			break;
		case ActivitySwitchSignals.ORDERHISTORYSWH:
			i = new Intent(getApplicationContext(), OrderHistoryActivity.class);
			startActivityForResult(i, ActivitySwitchSignals.ORDERHISTORY);
			break;	
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_controller, menu);
		return true;
	}

}
