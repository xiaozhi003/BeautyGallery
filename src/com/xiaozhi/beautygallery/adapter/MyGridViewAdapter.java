package com.xiaozhi.beautygallery.adapter;

import java.util.List;

import com.xiaozhi.beautygallery.domain.Image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 自定义Gridview Adapter
 * @author Xiaozhi
 */
public class MyGridViewAdapter extends BaseAdapter {
	
	private List<Image> mListImages;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public MyGridViewAdapter(Context context,List<Image> images) {
		this.mContext = context;
		this.mListImages = images;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListImages.size();
	}

	@Override
	public Image getItem(int position) {
		// TODO Auto-generated method stub
		return mListImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
