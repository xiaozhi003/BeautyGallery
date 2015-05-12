package com.xiaozhi.beautygallery.fragment;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.adapter.MyGridViewAdapter;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.util.Logs;
import com.xiaozhi.beautygallery.util.VolleyUtil;
/*
 * 显示图片列表Fragment
 */
public class FragmentMain extends Fragment {
	
	private static final String TAG = "FragmentMain";
	final String[] mStringList = {"Alibaba", "TMALL 11-11"};
	private int pn;// 加载的图片数量
	private String tag2 = "性感美女";
	
	private GridView mGridView;
	private MyGridViewAdapter mAdapter;
	private List<Image> mListImages = new ArrayList<Image>();
	private View view;
    private PtrFrameLayout mFrame;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container,false);
		
		initViews();
		return view;
	}

	private void initViews() {
		mGridView = (GridView) view.findViewById(R.id.gridView);
		mAdapter = new MyGridViewAdapter(getActivity(), mListImages);
		mGridView.setAdapter(mAdapter);
		mFrame = (PtrFrameLayout) view.findViewById(R.id.rotate_header_grid_view_frame);
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
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadItems();
            }
        });
	}
	
	/**
	 * 加载一页数据
	 */
	private void loadItems(){
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, VolleyUtil.getInstance().getUrl(pn, tag2), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				String jsonString = null;
				try {
					jsonString = new String(jsonObject.toString().getBytes(), "GBK");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Logs.i("json:" + jsonString);
				VolleyUtil.parseItems(mListImages, jsonObject);
				mFrame.refreshComplete();
				updateAdapter();
			}
		}, new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		VolleyUtil.getInstance().addToRequestQueue(request);
	}
	
	private void updateAdapter(){
		mAdapter.notifyDataSetChanged();
	}
}
