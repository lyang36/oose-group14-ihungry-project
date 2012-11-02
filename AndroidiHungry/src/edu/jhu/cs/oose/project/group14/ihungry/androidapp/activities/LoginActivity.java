package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.FileHandler;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

/**
 * This is the login screen - the first view that the user sees after entering the iHungry application.
 * @author SuNFloWer
 *
 */
public class LoginActivity extends Activity {

	private EditText et_username;
	private EditText et_pwd;
	private String username_stored = null;
	private String pwd_stored = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    
        et_username = (EditText) findViewById(R.id.editText1);
        et_pwd = (EditText) findViewById(R.id.editText2);
        //et_username.setHint("Please enter username");
        //et_pwd.setHint("Please enter password");
       
		String readStoredInfo = FileHandler.loadFile(this, FileHandler.f_userinfo);
		if(!readStoredInfo.equals(null)){
			String delims = "[|]+";
			String[] tokens = readStoredInfo.split(delims);
			for(int i=0; i<tokens.length; i++){
				Log.v("[Login]",tokens.length+" "+tokens[i]);

			}
			// Set the stored username & password in local variable
			username_stored = tokens[0];
			pwd_stored = tokens[1];
			
			et_username.setText(username_stored);
			et_pwd.setText(pwd_stored);
			
			
		}
    
    
		((Button) findViewById(R.id.button1)).setOnClickListener(btn_Login_Listener);
		((Button) findViewById(R.id.button2)).setOnClickListener(btn_Signup_Listener);

		
    }

	/**
	 * A call-back for when the user presses the login button. A signal to switch to main Screen is sent to ActivityController if the username and pwd are valid
	 */
	OnClickListener btn_Login_Listener = new OnClickListener() {
		public void onClick(View v) {
			if(et_username.getText().toString().equals(username_stored) && et_pwd.getText().toString().equals(pwd_stored)){
				ToastDisplay.DisplayToastOnScr(LoginActivity.this, "Login Successfully");				
				setResult(ActivitySwitchSignals.MAINSCREENSWH);
				finish();
			} else{
				ToastDisplay.DisplayToastOnScr(LoginActivity.this, "Login Failed");		
				
			}
			
		}
	};
	
	/**
	 * A call-back for when the user presses the signup button. An intent is create to start the signup activity
	 */
	OnClickListener btn_Signup_Listener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
			startActivity(intent);

		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			setResult(ActivitySwitchSignals.QUIT);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
 
}
