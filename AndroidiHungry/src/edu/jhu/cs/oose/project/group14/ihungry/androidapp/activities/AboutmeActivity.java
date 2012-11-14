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
	private TextView text_phone;
	private TextView text_secphone;
	private TextView text_address;
	private TextView text_email;
	private TextView text_birthday;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutme);

		text_name = (TextView) this.findViewById(R.id.txv_name);
		text_phone = (TextView) this.findViewById(R.id.txv_phone);
		text_secphone = (TextView) this.findViewById(R.id.txv_secphone);
		text_address = (TextView) this.findViewById(R.id.txv_address);
		text_email = (TextView) this.findViewById(R.id.txv_email);
		text_birthday = (TextView) this.findViewById(R.id.txv_birthday);
		
		String readStoredInfo = FileHandler.loadFile(this,
				FileHandler.f_userinfo);
		if (!readStoredInfo.equals(null)) {
			String delims = "[|]+";
			String[] tokens = readStoredInfo.split(delims);
			for (int i = 0; i < tokens.length; i++) {
				Log.v("[About me]", tokens.length + " " + tokens[i]);

			}
			
			text_name.setText(tokens[3]);
			text_address.setText(tokens[4]);
			text_phone.setText(tokens[5]);
			text_secphone.setText(tokens[6]);
			text_email.setText(tokens[7]);
			text_birthday.setText(tokens[8]);

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
