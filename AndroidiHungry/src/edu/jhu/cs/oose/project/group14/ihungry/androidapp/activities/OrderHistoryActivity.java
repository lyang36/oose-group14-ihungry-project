package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

/**
 * This view is used to show the order history of the user's.
 * @author SuNFloWer
 *
 */
public class OrderHistoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
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
