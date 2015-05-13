package com.xiaozhi.beautygallery.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.fragment.FragmentMain;
import com.xiaozhi.beautygallery.view.MorePopWindow;

public class MainActivity extends SingleFragmentActivity {

	private Toolbar mToolbar;
	private MorePopWindow mMorePopWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	protected void initViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mToolbar.setVisibility(View.VISIBLE);
		setSupportActionBar(mToolbar);
		mMorePopWindow = new MorePopWindow(this);
		mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_more:
					showMorePopWindow();
					break;
				default:
					break;
				}
				return false;
			}
		});

	}

	protected void showMorePopWindow() {
		// 显示窗口
		mMorePopWindow.showAsDropDown(mToolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_more) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new FragmentMain();
	}
}
