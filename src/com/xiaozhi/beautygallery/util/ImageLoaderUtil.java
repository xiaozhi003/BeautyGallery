package com.xiaozhi.beautygallery.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiaozhi.beautygallery.R;

/**
 * 图片加载工具类
 * @author Xiaozhi
 */
public class ImageLoaderUtil {
	private static final String TAG = "ImageUtil";

	private static ImageLoaderUtil loader = new ImageLoaderUtil();

	private static ImageLoader mImageLoader;

	private static ImageLoaderConfiguration configuration;


	private ImageLoaderUtil() {
	}

	public static ImageLoaderUtil getInstance() {
		return loader;
	}

	public void init(Context context) {
		if (configuration == null) {
			configuration = ImageLoaderConfiguration.createDefault(context);
		}
		ImageLoader.getInstance().init(configuration);
		mImageLoader = ImageLoader.getInstance();
	}
	
	public static void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options){
		mImageLoader.displayImage(uri, imageView, options);
	}

	public static void displayImage(String uri, ImageAware imageAware) {
		mImageLoader.displayImage(uri, imageAware);
	}

	public static void setConfiguration(ImageLoaderConfiguration configuration) {
		ImageLoaderUtil.configuration = configuration;
	}

	public static void loadImage(String uri, ImageLoadingListener listener) {
		mImageLoader.loadImage(uri, listener);
	}

	public static void loadImage(String uri, DisplayImageOptions options,
			ImageLoadingListener listener) {
		mImageLoader.loadImage(uri, options, listener);
	}

	public static void loadImage(String uri, ImageSize targetImageSize,
			DisplayImageOptions options, ImageLoadingListener listener) {
		mImageLoader.loadImage(uri, targetImageSize, options, listener);
	}

	public static void displayImage(String uri, ImageAware imageAware,
			DisplayImageOptions options) {
		mImageLoader.displayImage(uri, imageAware, options);
	}


	public static DisplayImageOptions getDefaultOptions() {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.empty_photo) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.empty_photo)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.empty_photo) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .delayBeforeLoading(100)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(300))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		return options;
	}
}
