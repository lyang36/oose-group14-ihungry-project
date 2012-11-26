package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;

/**
 * This activity is responsible for view/activity transition.
 * 
 * @author SuNFloWer
 * 
 */
public class ActivityController extends Activity {

	private Intent intent_i;
	private SparseArray<ActivitySwitcher> switchArray;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		switchArray = new SparseArray<ActivitySwitcher>();
		//	Map<Integer, ActivitySwitcher> map = new HashMap<Integer, ActivitySwitcher>();
		switchArray.append( ActivitySwitchSignals.QUIT, 			new FinishSwitcher() );
		switchArray.append( ActivitySwitchSignals.LOGINSWH, 		new LoginSwitcher() );
		switchArray.append( ActivitySwitchSignals.MAINSCREENSWH, 	new MainscreenSwitcher() );
		switchArray.append( ActivitySwitchSignals.NEARBYSWH, 		new NearbySwitcher() );
		switchArray.append( ActivitySwitchSignals.ABOUTMESWH, 		new AboutmeSwitcher() );
		switchArray.append( ActivitySwitchSignals.FAVOURITESSWH, 	new FavouriteSwitcher() );
		switchArray.append( ActivitySwitchSignals.ORDERHISTORYSWH, 	new OrderhistorySwitcher() );
	
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

		ActivitySwitcher switcher = (ActivitySwitcher)(switchArray.get(resultCode));
		switcher.switchOnCode(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_controller, menu);
		return true;
	}
	
	
	/**
	 * An abstract class containing one only method switchOnCode().
	 * @author SuNFloWer
	 *
	 */
	abstract class ActivitySwitcher{
		abstract void switchOnCode(Activity act);
	}
	class FinishSwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			Log.v("[ActivityController]", "Finish");
			act.finish();
		}
	}
	class LoginSwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), ActivitySwitchSignals.LOGIN);
		}
	}
	class MainscreenSwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), MainScreenActivity.class), ActivitySwitchSignals.MAINSCREEN);
		}
	}
	class NearbySwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), NearbyActivity.class), ActivitySwitchSignals.NEARBY);		
		}
	}
	class AboutmeSwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), AboutmeActivity.class), ActivitySwitchSignals.ABOUTME);		
		}
	}
	class FavouriteSwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), FavouritesActivity.class), ActivitySwitchSignals.FAVOURITES);
		}
	}
	class OrderhistorySwitcher extends ActivitySwitcher{
		@Override
		void switchOnCode(Activity act) {
			startActivityForResult(new Intent(getApplicationContext(), OrderHistoryActivity.class), ActivitySwitchSignals.ORDERHISTORY);
		}
	}
}

