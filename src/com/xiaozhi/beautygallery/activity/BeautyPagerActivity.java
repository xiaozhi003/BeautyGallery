package com.xiaozhi.beautygallery.activity;

import java.util.List;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.fragment.BeautyFragment;
import com.xiaozhi.beautygallery.fragment.FragmentMain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class BeautyPagerActivity extends AppCompatActivity {
	
	public static final String POSITION = "position";
	
	private ViewPager mViewPager;
	private List<Image> mImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beauty_pager);
		
		initViews();
	}

	private void initViews() {
		mImages = FragmentMain.mListImages;
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return mImages.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				return BeautyFragment.newInstance(mImages.get(position).getUrl());
			}
		});
		int positon = getIntent().getIntExtra(POSITION, 0);
		mViewPager.setCurrentItem(positon);
	}
}
