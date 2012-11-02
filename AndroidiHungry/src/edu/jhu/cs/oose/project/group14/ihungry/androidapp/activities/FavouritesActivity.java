package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

/**
 * This view is for showing the user's favourite items/restaurants.
 * @author SuNFloWer
 *
 */
public class FavouritesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favourites, menu);
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
