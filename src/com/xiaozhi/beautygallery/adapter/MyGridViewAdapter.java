package com.xiaozhi.beautygallery.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.activity.BeautyPagerActivity;
import com.xiaozhi.beautygallery.domain.Image;
import com.xiaozhi.beautygallery.util.ImageLoaderUtil;
import com.xiaozhi.beautygallery.util.Logs;
import com.xiaozhi.beautygallery.util.MeasureUtil;
import com.xiaozhi.beautygallery.view.FooterView;

/**
 * 自定义Gridview Adapter
 * 
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

	public MyGridViewAdapter(Context context, List<Image> images) {
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
		Log.i("Myadapter", "position:" + position);
		Log.i("Myadapter", "getChildCount:" + parent.getChildCount());
		if (convertView == null) {
			convertView = initConvertView(position, convertView, parent);
		}

		setDataToConvertView(position, convertView);
		
		return convertView;
	}

	/**
	 * 给convertView设置数据
	 * 
	 * @param position
	 * @param convertView
	 */
	private void setDataToConvertView(int position, View convertView) {
		if (getItemViewType(position) == VIEW_TYPE_ITEM) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			/**
			 * 此处的判断是为了避免调用notifyDataSetChanged所有的view都要重新setImageBitmap
			 * 导致闪动也就是做了我们的渐入动画
			 */
			if (!getItem(position).getUrl().equals(
					holder.mImageView.getTag(R.id.imageView))) {
				// 1displayImage 两种方式的比较
//				 displayImage(holder.mImageView, position);

				// 2loadImage
				holder.mImageView.setTag(R.id.imageView, getItem(position)
						.getUrl());
				holder.mImageView.setTag(R.id.gridView, position);
				loadImage(holder.mImageView, position);
			}
		} else if (getItemViewType(position) == VIEW_TYPE_FOOT && position != 0) {
			setFooterViewStatus(FooterView.MORE);
		}
	}

	/**
	 * 初始化convertView
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View initConvertView(final int position, View convertView,
			ViewGroup parent) {
		ViewHolder holder = null;
		if (getItemViewType(position) == VIEW_TYPE_FOOT) {// 初始化底部加载更多视图
			mFooterView = new FooterView(mContext);
			convertView = mFooterView;
			mFooterView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mFooterView.getStatus() == FooterView.MORE
							&& mLoadListener != null) {
						mLoadListener.onLoad();
					}
				}
			});
			GridView.LayoutParams pl = new GridView.LayoutParams(
					MeasureUtil.getScreenSize(mContext)[0],
					LayoutParams.WRAP_CONTENT);
			mFooterView.setLayoutParams(pl);

			setFooterViewStatus(FooterView.HIDE);

		} else {// 初始化图片视图
			convertView = mInflater.inflate(R.layout.view_gallery_item, parent,
					false);
			holder = new ViewHolder();

			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.imageView);
			holder.mImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							Intent intent = new Intent(mContext,
									BeautyPagerActivity.class);
							intent.putExtra(BeautyPagerActivity.POSITION,
									(Integer) v.getTag(R.id.gridView));
							mContext.startActivity(intent);
						}
					}, 200);
				}
			});
			convertView.setTag(holder);
		}
		return convertView;
	}

	/**
	 * universal-imagelader的displayImage默认已经处理图片快速滑动错位的感觉
	 * 但是加了默认的动画体验还是不太好，功能越完善性能就会下降
	 * 
	 * @param imageView
	 * @param position
	 */
	private void displayImage(ImageView imageView, int position) {
		ImageLoaderUtil.displayImage(getItem(position).getUrl(), imageView,ImageLoaderUtil.getDefaultOptions());
	}

	/**
	 * 自己去加载就得处理图片错位了
	 * 
	 * @param imageView
	 * @param position
	 */
	private void loadImage(final ImageView imageView, final int position) {
		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.anim_alpha);
		imageView.startAnimation(animation);
		Logs.i("width:" + imageView.getWidth() + ";height:"
				+ imageView.getHeight());
		ImageLoaderUtil.loadImage(getItem(position).getUrl(), new ImageSize(
				imageView.getWidth(), imageView.getHeight()), ImageLoaderUtil
				.getDefaultOptions(), new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				/**
				 * 如下判断就是处理图片在快速滑动异步线程不断执行setImageBitmap
				 * 通过比较当前的url是否过期，来给imageView设置一次也就是最新的Bitmap
				 * 这样就避免了多次重复的setImageBitmap，而且加了渐变动画体验就会更好了
				 */
				
				if (imageUri.equals(imageView.getTag(R.id.imageView))) {
					imageView.setImageBitmap(loadedImage);
				}
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				super.onLoadingStarted(imageUri, view);
				imageView.setImageResource(R.drawable.empty_photo);
			}
		});
	}

	class ViewHolder {
		ImageView mImageView;
	}

	/**
	 * 下拉加载回调接口
	 */
	public interface LoadListener {
		void onLoad();
	}

	private LoadListener mLoadListener;

	public void setLoadListener(LoadListener loadListener) {
		mLoadListener = loadListener;
	}

	public void setFooterViewStatus(int status) {
		if (mFooterView != null) {
			mFooterView.setStatus(status);
		}
	}

}
