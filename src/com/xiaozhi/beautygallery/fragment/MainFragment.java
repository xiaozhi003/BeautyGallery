package com.xiaozhi.beautygallery.fragment;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.adapter.MyGridViewAdapter;
import com.xiaozhi.beautygallery.adapter.MyGridViewAdapter.LoadListener;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.util.CustomUtil;
import com.xiaozhi.beautygallery.util.VolleyUtil;
import com.xiaozhi.beautygallery.view.FooterView;

/*
 * 显示图片列表Fragment
 */
public class MainFragment extends Fragment {

	private static final String TAG = "FragmentMain";
	/** 从第几项开始加载图片*/
	private int pn;
	private String tag2;
	private String oldTag2;

	private GridView mGridView;
	private MyGridViewAdapter mAdapter;
	public static List<Image> mListImages = new ArrayList<Image>();
	private View view;
	private PtrFrameLayout mFrame;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);

		initViews();
		return view;
	}

	private void initViews() {
		tag2 = CustomUtil.getInstance().getString(VolleyUtil.TAG2);
		if (tag2 == null) {
			tag2 = VolleyUtil.TAG2_DEFAULT;
			CustomUtil.getInstance().save(VolleyUtil.TAG2, tag2);
		}
		oldTag2 = tag2;
		mGridView = (GridView) view.findViewById(R.id.gridView);
		mAdapter = new MyGridViewAdapter(getActivity(), mListImages);
		mAdapter.setLoadListener(new LoadListener() {

			@Override
			public void onLoad() {
				loadNextPage();
			}
		});
		mGridView.setAdapter(mAdapter);
		mFrame = (PtrFrameLayout) view
				.findViewById(R.id.rotate_header_grid_view_frame);
		StoreHouseHeader header = new StoreHouseHeader(getActivity());
		header.setPadding(0, 15, 0, 15);
		header.initWithString("Loading...");

		mFrame.setDurationToCloseHeader(1500);
		mFrame.setHeaderView(header);
		mFrame.addPtrUIHandler(header);
		mFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				mFrame.autoRefresh(false);
			}
		}, 100);
		mFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				mListImages.clear();
				pn = 0;
				if (!oldTag2.equals(tag2)) {
					CustomUtil.getInstance().save(VolleyUtil.TAG2, tag2);
					oldTag2 = tag2;
				}
				loadItems();
			}
		});
	}

	/**
	 * 加载一页数据
	 */
	private void loadItems() {
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
				VolleyUtil.getInstance().getUrl(pn, tag2), null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						VolleyUtil.parseItems(mListImages, jsonObject);
						mFrame.refreshComplete();
						updateAdapter();
						mAdapter.setFooterViewStatus(FooterView.MORE);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getActivity(), R.string.loading_error,
								Toast.LENGTH_SHORT).show();
						mFrame.refreshComplete();
					}
				});
		VolleyUtil.getInstance().addToRequestQueue(request);
	}

	/**
	 * 加载下一页数据
	 */
	private void loadNextPage() {
		if (mAdapter != null) {
			mAdapter.setFooterViewStatus(FooterView.LOADING);
		}
		pn += VolleyUtil.PAGE_SIZE;
		loadItems();
	}

	private void updateAdapter() {
		mAdapter.notifyDataSetChanged();
	}

	public void changeOtherBeautyType(String tag2) {
		this.tag2 = tag2;
		mFrame.autoRefresh();
	}
}
