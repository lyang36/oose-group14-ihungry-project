package edu.jhu.cs.oose.project.group14.ihungry.androidapp.activities;

import com.example.androidihungry.R;
import com.example.androidihungry.R.layout;
import com.example.androidihungry.R.menu;

import edu.jhu.cs.oose.project.group14.ihungry.androidapp.ActivitySwitchSignals;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This view shows the detailed ordered items.
 * 
 * @author SuNFloWer
 *
 */
public class OrderReviewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);
        
        ((Button)findViewById(R.id.btn_submit)).setOnClickListener(btn_submit_Listener);
        
        
    }
    
    OnClickListener btn_submit_Listener = new OnClickListener() {
		public void onClick(View v) {
			/*
			 * Submit the order.
			 * 
			 * 
			 */
			
			setResult(ActivitySwitchSignals.ORDERREVIEWCLOSESWH);

			finish();
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order_review, menu);
        return true;
    }
}
