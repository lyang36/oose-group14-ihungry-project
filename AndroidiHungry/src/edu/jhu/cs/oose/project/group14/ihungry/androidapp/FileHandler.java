package edu.jhu.cs.oose.project.group14.ihungry.androidapp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.util.Log;

/**
 * This class handles reading from and writing to the file.
 * 
 * @author SuNFloWer
 * 
 */
public class FileHandler {
	/**
	 * File name for "userinfo.txt".
	 */
	public static String f_userinfo = "userinfo.txt";
	/**
	 * File name for "restLocationCache.txt".
	 */
	public static String f_rest_location_cache = "restLocationCache.txt";
	
	/**
	 * The Username stored in Android phone.
	 */
	public static String username_stored = "";
	
	/**
	 * The MD5 version of password stored in Android phone.
	 */
	public static String pwd_stored = "";

	/**
	 * Save input string into a file and store in internal storage in Android
	 * 
	 * @param fileName
	 * @param str
	 * @return a boolean value whether the saving process succeeds.
	 */
	public static boolean saveFile(Context context, String fileName, String str) {
		boolean saveSucceed = false;
		try {
			FileOutputStream outStream = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			outStream.write(str.getBytes());
			outStream.close();
			Log.v("[Save file]", "Saved " + str);
			// ToastDisplay.DisplayToastOnScr(context, "Saved"+str);
			saveSucceed = true;
		} catch (Exception e) {
			Log.e("[Exception]", "Error saving file");
			e.printStackTrace();
		}
		return saveSucceed;
	}

	/**
	 * Read content according to the file name from the internal storage of
	 * Android.
	 * 
	 * @param fileName
	 * @return a String containing the file content.
	 */
	public static String loadFile(Context context, String fileName) {
		String readStr = null;
		try {
			FileInputStream inStream = context.openFileInput(fileName);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.close();
			inStream.close();
			readStr = stream.toString();
			// ToastDisplay.DisplayToastOnScr(context, "Loaded " + readStr);
			Log.v("[Load file]", "Loaded " + readStr);
		} catch (Exception e) {
			Log.v("[Exception]", "Error reading file");
		}
		return readStr;
	}

	/*
	public static void setUnamePwd() {
		String readStoredInfo = FileHandler.loadFile(null,
				FileHandler.f_userinfo);
		if (readStoredInfo != null) {
			String delims = "[|]+";
			String[] tokens = readStoredInfo.split(delims);
			for (int i = 0; i < tokens.length; i++) {
				Log.v("[Login]", tokens.length + " " + tokens[i]);

			}
			username_stored = tokens[0];
			pwd_stored = tokens[1];
		}
	}*/
	
	public static void setUname(String uname){
		username_stored = uname;
	}
	
	public static void setPwd(String pwd){
		pwd_stored = pwd;
	}
	
}
