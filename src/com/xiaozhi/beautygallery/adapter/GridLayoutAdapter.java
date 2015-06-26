package com.xiaozhi.beautygallery.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.util.ImageLoaderUtil;
import com.xiaozhi.beautygallery.util.MeasureUtil;

public class GridLayoutAdapter extends
		RecyclerView.Adapter<GridLayoutAdapter.MyViewHolder> {

	private static final String TAG = "MyRecyclerAdapter";

	private Context mContext;
	private List<Image> mListImages;
	private LayoutInflater mInflater;
	private int mCloumnWidth;

	public GridLayoutAdapter(Context context, List<Image> images) {
		this.mContext = context;
		this.mListImages = images;
		mInflater = LayoutInflater.from(mContext);
		mCloumnWidth = MeasureUtil.getScreenSize(mContext)[0] / 3 - 6;
		setHasStableIds(true);
	}

	@Override
	public int getItemCount() {
		return mListImages.size();
	}
	
	@Override
	public long getItemId(int position) {
		return mListImages.get(position).getUrl().hashCode();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		if (!mListImages.get(position).getUrl()
				.equals(holder.iv.getTag(R.id.imageView))) {
			holder.iv
					.setTag(R.id.imageView, mListImages.get(position).getUrl());
			displayImage(holder.iv, position);
		}
		if (mOnItemClickListener != null) {
			holder.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(v, position);
				}
			});
			holder.itemView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					mOnItemClickListener.onItemLongClick(v, position);
					return false;
				}
			});
		}
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Log.d(TAG, "");
		View view = mInflater
				.inflate(R.layout.view_gallery_item, parent, false);
		MyViewHolder holder = new MyViewHolder(view);

		return holder;
	}

	/**
	 * universal-imagelader的displayImage默认已经处理图片快速滑动错位 但是加了默认的动画体验还是不太好
	 * 
	 * @param imageView
	 * @param position
	 */
	private void displayImage(ImageView imageView, int position) {
		ImageLoaderUtil.displayImage(mListImages.get(position).getUrl(),
				imageView, ImageLoaderUtil.getDefaultOptions());
	}

	class MyViewHolder extends ViewHolder {

		ImageView iv;

		public MyViewHolder(View itemView) {
			super(itemView);
			iv = (ImageView) itemView.findViewById(R.id.imageView);
		}
	}

	public interface OnItemClickListener {
		public void onItemClick(View v, int position);

		public void onItemLongClick(View v, int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
}
