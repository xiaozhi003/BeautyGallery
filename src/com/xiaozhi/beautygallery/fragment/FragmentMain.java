package com.xiaozhi.beautygallery.fragment;

import java.util.ArrayList;
import java.util.List;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.adapter.MyGridViewAdapter;
import com.xiaozhi.beautygallery.domain.Image;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
/*
 * 显示图片列表Fragment
 */
public class FragmentMain extends Fragment {
	
	private static final String TAG = "FragmentMain";
	
	private GridView mGridView;
	private MyGridViewAdapter mAdapter;
	private List<Image> mListImages = new ArrayList<Image>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container,false);
		mGridView = (GridView) v.findViewById(R.layout.fragment_main);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
