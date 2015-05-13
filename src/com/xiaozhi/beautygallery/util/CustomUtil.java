package com.xiaozhi.beautygallery.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CustomUtil {

	public static final String APP_NAME = "beautygallery";

	private Context mContext;
	private static CustomUtil util;

	private CustomUtil() {
	}

	public void init(Context context) {
		this.mContext = context;
	}

	public static CustomUtil getInstance() {
		if (util == null) {
			util = new CustomUtil();
		}
		return util;
	}

	public void save(String key, boolean value) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				APP_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				APP_NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, true);
	}

	public void save(String key, String value) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				APP_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				APP_NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getString(key, null);
	}
}