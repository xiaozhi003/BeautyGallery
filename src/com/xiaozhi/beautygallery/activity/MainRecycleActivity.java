package com.xiaozhi.beautygallery.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.fragment.MainFragment;
import com.xiaozhi.beautygallery.fragment.MainRecycleFragment;
import com.xiaozhi.beautygallery.view.MorePopWindow;
import com.xiaozhi.beautygallery.view.MorePopWindow.OnMorePopWindowItemClickListener;

public class MainRecycleActivity extends SingleFragmentActivity {

	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	private MorePopWindow mMorePopWindow;
	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);
		initViews();
	}

	protected void initViews() {
		mToolbar.setVisibility(View.VISIBLE);
		setSupportActionBar(mToolbar);
		mMorePopWindow = new MorePopWindow(this);
		mMorePopWindow
				.setOnMorePopWindowItemClickListener(new OnMorePopWindowItemClickListener() {

					@Override
					public void onItemClick(int position, String item) {
						if (mFragment instanceof MainRecycleFragment) {
							((MainRecycleFragment) mFragment)
									.changeOtherBeautyType(item);
						}
					}
				});
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
		mFragment = new MainRecycleFragment();
		return mFragment;
	}
}
