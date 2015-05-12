package com.xiaozhi.beautygallery.activity;

import com.xiaozhi.beautygallery.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * 欢迎界面
 * @author Xiaozhi
 */
public class WelcomeActivity extends Activity {
	
	private WelcomeActivity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mActivity = this;
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(mActivity, MainActivity.class));
				mActivity.finish();
			}
		}, 2000);
	}

}
