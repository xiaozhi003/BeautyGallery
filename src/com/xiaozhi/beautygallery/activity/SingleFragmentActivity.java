package com.xiaozhi.beautygallery.activity;

import com.xiaozhi.beautygallery.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
/**
 * 抽象Activity类
 * @author Xiaozhi
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取FragmentManager对象
		FragmentManager fm = getSupportFragmentManager();
		// 获取fragment
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

		// fragment事务
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment)
			.commit();
		}
	}
	
}
