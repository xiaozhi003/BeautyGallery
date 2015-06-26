package com.xiaozhi.beautygallery.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecyclerView extends RecyclerView {

	public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyRecyclerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		
	}
	
	@Override
	public void onScrolled(int dx, int dy) {
		super.onScrolled(dx, dy);
	}
	
	@Override
	public void onScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		super.onScrollStateChanged(state);
	}

}
