package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.*;

/**
 * The view that shows the personal information of the user including name,
 * gender, phone #, address, etc.
 * 
 * @author SuNFloWer
 * 
 */
public class AboutmeActivity extends Activity {
	private TextView text_name;
	private TextView text_gender;
	private TextView text_phone;
	private TextView text_address;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutme);

		text_name = (TextView) this.findViewById(R.id.txv_name);
		text_gender = (TextView) this.findViewById(R.id.txv_gender);
		text_phone = (TextView) this.findViewById(R.id.txv_phone);
		text_address = (TextView) this.findViewById(R.id.txv_address);

		String readStoredInfo = FileHandler.loadFile(this,
				FileHandler.f_userinfo);
		if (!readStoredInfo.equals(null)) {
			String delims = "[|]+";
			String[] tokens = readStoredInfo.split(delims);
			for (int i = 0; i < tokens.length; i++) {
				Log.v("[About me]", tokens.length + " " + tokens[i]);

			}

			text_name.setText(tokens[2]);

			text_gender.setText(tokens[3]);
			text_phone.setText(tokens[4]);
			text_address.setText(tokens[5]);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_aboutme, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			setResult(ActivitySwitchSignals.MAINSCREENSWH);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
