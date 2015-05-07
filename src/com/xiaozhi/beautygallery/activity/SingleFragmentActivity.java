package com.xiaozhi.beautygallery.activity;

import com.xiaozhi.beautygallery.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
/**
 * 抽象Activity类
 * @author Xiaozhi
 */
public abstract class SingleFragmentActivity extends Activity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取FragmentManager对象
		FragmentManager fm = getFragmentManager();
		// 获取fragment
		Fragment fragment = fm.findFragmentById(R.id.fragmentContent);

		// fragment事务
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContent, fragment)
			.commit();
		}
	}
}
