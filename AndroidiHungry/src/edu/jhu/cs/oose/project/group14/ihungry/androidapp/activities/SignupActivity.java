package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;
import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ToastDisplay;
import edu.jhu.cs.oose.project.group14.ihungry.androidclientmodel.*;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

/**
 * This view is for user-registration (sign-up).
 * 
 * @author SuNFloWer
 * 
 */
public class SignupActivity extends Activity {
	private EditText et_username;
	private EditText et_password;
	private EditText et_confirmpwd;
	private EditText et_realname;
	private EditText et_address;
	private EditText et_primphone;
	private EditText et_2ndphone;
	private EditText et_email;
	private EditText et_birthday;

	private String username;
	private String password;
	private String confirmpwd;
	private String realname;
	private String address;
	private String primphone;
	private String secphone;
	private String email;
	private String birthday;

	private AndroidClientModel clientModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		et_username = (EditText) this.findViewById(R.id.et_username);
		et_password = (EditText) this.findViewById(R.id.et_password);
		et_confirmpwd = (EditText) this.findViewById(R.id.et_confirmpwd);

		et_realname = (EditText) this.findViewById(R.id.et_realname);
		et_address = (EditText) this.findViewById(R.id.et_address);
		et_primphone = (EditText) this.findViewById(R.id.et_primphone);
		et_2ndphone = (EditText) this.findViewById(R.id.et_2ndphone);
		et_email = (EditText) this.findViewById(R.id.et_email);
		et_birthday = (EditText) this.findViewById(R.id.et_birthday);

		/* Initialize client model */
		clientModel = new AndroidClientModelImpl();

		((Button) this.findViewById(R.id.btn_signupinsup))
				.setOnClickListener(btn_signupinsup_Listener);

	}

	OnClickListener btn_signupinsup_Listener = new OnClickListener() {
		public void onClick(View v) {
			username = handleEditText(et_username.getText().toString());
			password = handleEditText(et_password.getText().toString());
			confirmpwd = handleEditText(et_confirmpwd.getText().toString());

			realname = handleEditText(et_realname.getText().toString());
			address = handleEditText(et_address.getText().toString());
			primphone = handleEditText(et_primphone.getText().toString());
			secphone = handleEditText(et_2ndphone.getText().toString());
			email = handleEditText(et_email.getText().toString());
			birthday = handleEditText(et_birthday.getText().toString());

			if (password.equals(confirmpwd)) {
				Log.v("[Customer info]", username + "||" + password + "||"
						+ realname + "||" + address + "||" + primphone + "||"
						+ secphone + "||" + email + "||" + birthday);

				/* Call client model to sign up for a new user */
				boolean signup_result = clientModel.signupForNewUser(username,
						password, realname, address, primphone, secphone,
						email, birthday, new Icon());

				if (signup_result) {
					ToastDisplay.DisplayToastOnScr(SignupActivity.this,
							"Signup successfully!");
					finish();

				} else {
					ToastDisplay.DisplayToastOnScr(SignupActivity.this,
							"Connection Error or Signup failed!");
				}
				

			} else{
				ToastDisplay.DisplayToastOnScr(SignupActivity.this, "Please re-enter your password");
			}

			// setResult(ActivitySwitchSignals.ORDERREVIEWCLOSESWH);

		}
	};

	/**
	 * Return a string with one space if the input string is null or empty
	 * 
	 * @param text_in
	 * @return
	 */
	protected static String handleEditText(String text_in) {
		if (text_in == null || text_in.equals("")) {
			return " ";
		} else {
			return text_in;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_signup, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back => Just finish
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
