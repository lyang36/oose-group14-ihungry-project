package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import android.content.Context;
import android.widget.Toast;

/**
 * This class only contains one method to display Toast on screen.
 * @author SuNFloWer
 *
 */
public class ToastDisplay {

	/**
	 * Display toast on screen.
	 * @param str
	 */
	public static void DisplayToastOnScr(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
