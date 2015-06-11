package com.xiaozhi.beautygallery.fragment;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiaozhi.beautygallery.R;
import com.xiaozhi.beautygallery.util.ImageLoaderUtil;
/**
 * 显示大图Fragment,可点击放大拖动等
 * @author Administrator
 *
 */
public class BeautyFragment extends Fragment {

	public static final String URL = "url";

	private String url;

	private ImageView mBeautyImg;
	private PhotoViewAttacher mAttacher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_beauty, container, false);
		mBeautyImg = (ImageView) v.findViewById(R.id.beautyImg);
		mAttacher = new PhotoViewAttacher(mBeautyImg);
		ImageLoaderUtil.displayImage(url, mBeautyImg,
				ImageLoaderUtil.getDefaultOptions(),new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				mAttacher.update();
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null)
			url = bundle.getString(URL);
	}

	public static BeautyFragment newInstance(String url) {
		Bundle bundle = new Bundle();
		bundle.putString(URL, url);
		BeautyFragment beautyFragment = new BeautyFragment();
		beautyFragment.setArguments(bundle);
		return beautyFragment;
	}
}
