package com.xiaozhi.beautygallery.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xiaozhi.beautygallery.util.Logs;
import com.xiaozhi.beautygallery.util.VolleyUtil;

/**
 * app入口，用于初始化一些数据
 * @author Xiaozhi
 */
public class BeautyGalleryApp extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();
		Logs.setsApplicationTag(BeautyGalleryApp.class.getSimpleName().toString());
		Logs.setsIsLogEnabled(true);
		// 初始化Volley
		initVolley();
		// 初始化Universal-ImageLoader
		initImageLoader();
	}

	private void initImageLoader() {
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getApplicationContext());
		ImageLoader.getInstance().init(configuration);
	}

	private void initVolley() {
		VolleyUtil.init(getApplicationContext());
	}

}
