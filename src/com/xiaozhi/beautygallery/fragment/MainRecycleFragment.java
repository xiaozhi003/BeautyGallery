package com.xiaozhi.beautygallery.fragment;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.activity.BeautyPagerActivity;
import com.xiaozhi.beautygallery.adapter.StaggeredAdapter;
import com.xiaozhi.beautygallery.adapter.StaggeredAdapter.OnItemClickListener;
import com.xiaozhi.beautygallery.listener.MyRecyclerOnScrollListener;
import com.xiaozhi.beautygallery.util.CustomUtil;
import com.xiaozhi.beautygallery.util.VolleyUtil;

/*
 * 显示图片列表Fragment
 */
public class MainRecycleFragment extends Fragment {

	private static final String TAG = "FragmentMain";
	/** 从第几项开始加载图片 */
	private int pn;
	private String tag2;
	private String oldTag2;

	/** 正在加载数据 */
	private boolean isLoading = true;
	/** 是否是下拉刷新 */
	private boolean isDropDownRefresh = true;

	private RecyclerView mRecyclerView;
	private StaggeredAdapter mAdapter;
	private View view;
	private PtrFrameLayout mFrame;
	private LayoutManager mLayoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.fragment_recyle_main, container, false);

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

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
		mAdapter = new StaggeredAdapter(getActivity(), VolleyUtil.mImages);
		mRecyclerView.setAdapter(mAdapter);
		mLayoutManager = new StaggeredGridLayoutManager(3,
				StaggeredGridLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemLongClick(View v, int position) {
			}

			@Override
			public void onItemClick(View v, int position) {
				startBeautyPagerActivity(position);
			}
		});
		mRecyclerView.setOnScrollListener(new MyRecyclerOnScrollListener() {

			@Override
			public void onScrollBottom() {
				Log.i("Last", "onScrollBottom");
				if (!isLoading) {
					isLoading = true;
					loadNextPage();
				}
			}
		});

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
				VolleyUtil.mImages.clear();
				pn = 0;
				if (!oldTag2.equals(tag2)) {
					CustomUtil.getInstance().save(VolleyUtil.TAG2, tag2);
					oldTag2 = tag2;
				}
				isDropDownRefresh = true;
				loadItems();
			}
		});
	}

	/**
	 * 启动图片详情Activity
	 * 
	 * @param position
	 */
	private void startBeautyPagerActivity(int position) {
		Intent intent = new Intent(getActivity(), BeautyPagerActivity.class);
		intent.putExtra(BeautyPagerActivity.POSITION, position);
		getActivity().startActivity(intent);
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
						VolleyUtil.parseItems(jsonObject);
						updateView();
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
		}
		pn += VolleyUtil.PAGE_SIZE;
		loadItems();
		isDropDownRefresh = false;
	}

	/**
	 * 更新视图
	 */
	private void updateView() {
		mFrame.refreshComplete();
		if (isDropDownRefresh) {
			notifyDataSetChanged();
//			notifyItemRangeChanged();
			mRecyclerView.scrollToPosition(0);
		} else {
			notifyItemRangeChanged();
		}
		isLoading = false;
	}

	/**
	 * 局部刷新
	 */
	private void notifyItemRangeChanged() {
		mAdapter.notifyItemRangeChanged(VolleyUtil.mImages.size()
				- VolleyUtil.PAGE_SIZE, VolleyUtil.PAGE_SIZE);
	}

	/**
	 * 全局刷新
	 */
	private void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 切换美女图片类型
	 * 
	 * @param tag2
	 */
	public void changeOtherBeautyType(String tag2) {
		this.tag2 = tag2;
		mFrame.autoRefresh();
	}
}
