package com.xiaozhi.beautygallery.activity;

import android.app.Fragment;

import com.xiaozhi.beautygallery.fragment.BeautyFragment;

public class BeautyActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return BeautyFragment.newInstance(getIntent().getStringExtra(BeautyFragment.URL));
	}
}
