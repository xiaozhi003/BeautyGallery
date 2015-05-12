package com.xiaozhi.beautygallery.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.xiaozhi.beautygallery.domain.Image;

/*
 * 网络访问工具类
 */
public class VolleyUtil {
	
	public static final int PAGE_SIZE = 14;
	public static final String PREF_SEARCH_QUERY = "searchQuery";
	public static final String PREF_LAST_RESULT_ID = "lastResultId";

	private static final String ENDPOINT = "http://image.baidu.com/channel/listjson";
	private static final String PN = "pn";
	private static final String RN = "rn";
	private static final String TAG1 = "tag1";
	private static final String TAG2 = "tag2";
	
	private String url = "";
	
	private static VolleyUtil mInstance;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static Context mCtx;

	private VolleyUtil(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();

		mImageLoader = new ImageLoader(mRequestQueue,
				new BitmapCache());
	}

	public static void init(Context context) {
		if (mInstance == null) {
			mInstance = new VolleyUtil(context);
		}
	}

	public static VolleyUtil getInstance() {
		if (mInstance == null) {
			throw new NullPointerException(
					"please call HttpUtil.init(),before getInstance()");
		}
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			// getApplicationContext() is key, it keeps you from leaking the
			// Activity or BroadcastReceiver if someone passes one in.
			mRequestQueue = Volley
					.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
	
	private class BitmapCache implements ImageCache{
		
		private int maxSize = 8 * 1024 * 1024;
		private LruCache<String, Bitmap> cache;
		
		public BitmapCache(){
			cache = new LruCache<String, Bitmap>(maxSize){
				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getByteCount();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return cache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			cache.put(url, bitmap);
		}
	}
	
	public String getUrl(int pn,String tag2){
		url = Uri.parse(ENDPOINT).buildUpon()
				.appendQueryParameter(PN, String.valueOf(pn))
				.appendQueryParameter(RN, PAGE_SIZE+"")
				.appendQueryParameter(TAG1, "美女")
				.appendQueryParameter(TAG2, tag2).build().toString();
		Logs.d("url:" + url);
		return url;
	}
	
	
	/**
	 * 解析json数据
	 * 
	 * @param listImages
	 * @param jo
	 */
	public static void parseItems(List<Image> listImages, JSONObject jo) {
		try {
			JSONArray jsonArray = jo.getJSONArray("data");
			int jsonLength = jsonArray.length();
			for (int i = 0; i < jsonLength - 1; i++) {
				JSONObject dataObject = (JSONObject) jsonArray.opt(i);
				if (dataObject != null) {
					String id = dataObject.getString("id");
					String caption = dataObject.getString("desc");
					String smalUrl = dataObject.getString("share_url");

					Image item = new Image();
					item.setId(id);
					item.setDescription(caption);
					item.setUrl(smalUrl);

					listImages.add(item);
				}
			}
		} catch (JSONException e) {
			Logs.e("Failed to parse items", e);
		}
	}
}
