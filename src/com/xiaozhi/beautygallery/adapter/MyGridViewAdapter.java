package com.xiaozhi.beautygallery.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.util.MeasureUtil;
import com.xiaozhi.beautygallery.view.FooterView;
/**
 * 自定义Gridview Adapter
 * @author Xiaozhi
 */
public class MyGridViewAdapter extends BaseAdapter {
	
	// 图片视图类型
	public static final int VIEW_TYPE_ITEM = 0;
	// 底部图片类型
	public static final int VIEW_TYPE_FOOT = 1;
	public static final int VIEW_TYPE_COUNT = 2;
	
	private List<Image> mListImages;
	private Context mContext;
	private LayoutInflater mInflater;
	private FooterView mFooterView;
	
	public MyGridViewAdapter(Context context,List<Image> images) {
		this.mContext = context;
		this.mListImages = images;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// 注意此处因为加了底部视图所以加1
		return mListImages.size() + 1;
	}

	@Override
	public Image getItem(int position) {
		return mListImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 复写该方法使GridView正确缓存不同视图
	 */
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}
	
	/**
	 * 复写该方法使GridView正确缓存不同视图
	 */
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {
			return VIEW_TYPE_FOOT;
		}
		return VIEW_TYPE_ITEM;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = initConvertView(position,convertView,parent);
		}
		
		setConvertViewData(position,convertView);
		
		return convertView;
	}
	
	/**
	 * 给convertView设置数据
	 * @param position
	 * @param convertView
	 */
	private void setConvertViewData(int position,View convertView) {
		if (getItemViewType(position) == VIEW_TYPE_ITEM) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ImageLoader.getInstance().displayImage(getItem(position).getUrl(), holder.mImageView);
		}
	}

	/**
	 * 初始化convertView
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View initConvertView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if (getItemViewType(position) == VIEW_TYPE_FOOT) {// 初始化底部加载更多视图
			mFooterView = new FooterView(mContext);
			convertView = mFooterView;
			mFooterView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mFooterView.getStatus() == FooterView.MORE) {
					}
				}
			});
			GridView.LayoutParams pl = new GridView.LayoutParams(
					MeasureUtil.getScreenSize(mContext)[0],
					LayoutParams.WRAP_CONTENT);
			mFooterView.setLayoutParams(pl);
			
		}else {// 初始化图片视图
			convertView = mInflater.inflate(R.layout.view_gallery_item, parent,false);
			holder = new ViewHolder();
			
			holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		}
		return convertView;
	}
	
	class ViewHolder{
		ImageView mImageView;
	}

}
