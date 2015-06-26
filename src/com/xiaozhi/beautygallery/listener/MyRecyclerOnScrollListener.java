package com.xiaozhi.beautygallery.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
/**
 * 自定义RecyclerView的OnScrollListener监听滚动到底部事件
 * @author Xiaozhi
 *
 */
public abstract class MyRecyclerOnScrollListener extends RecyclerView.OnScrollListener{
	
	private int mLastVisbleItem;
	
	/** 滚动到底部事件*/
	public abstract void onScrollBottom();
	
	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			int i[] = ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPositions(null);
			mLastVisbleItem = Math.max(Math.max(i[0], i[1]), i[2]);
		}else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
			mLastVisbleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
		}
	}
	
	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		if (newState == recyclerView.SCROLL_STATE_IDLE && mLastVisbleItem + 1 == recyclerView.getAdapter().getItemCount()) {
			onScrollBottom();
		}
	}
}
