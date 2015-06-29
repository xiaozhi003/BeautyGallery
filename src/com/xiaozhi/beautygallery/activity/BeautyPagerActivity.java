package com.xiaozhi.beautygallery.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.fragment.BeautyFragment;
import com.xiaozhi.beautygallery.util.VolleyUtil;

public class BeautyPagerActivity extends AppCompatActivity {

	public static final String POSITION = "position";

	@Bind(R.id.viewPager)
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beauty_pager);
		ButterKnife.bind(this);
		initViews();
	}

	private void initViews() {
		mViewPager.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return VolleyUtil.mImages.size();
			}

			@Override
			public Fragment getItem(int position) {
				return BeautyFragment.newInstance(VolleyUtil.mImages.get(position)
						.getUrl());
			}
		});
		int positon = getIntent().getIntExtra(POSITION, 0);
		mViewPager.setCurrentItem(positon);
	}
}
