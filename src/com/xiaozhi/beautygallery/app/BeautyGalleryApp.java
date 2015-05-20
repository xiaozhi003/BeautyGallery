package com.xiaozhi.beautygallery.app;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xiaozhi.beautygallery.util.CustomUtil;
import com.xiaozhi.beautygallery.util.ImageLoaderUtil;
import com.xiaozhi.beautygallery.util.Logs;
import com.xiaozhi.beautygallery.util.VolleyUtil;

/**
 * app入口，用于初始化一些数据
 * @author Xiaozhi
 */
public class BeautyGalleryApp extends Application{

	/** 图片Disk缓存目录 */
	private static final String CACHE_DIR = Environment
			.getExternalStorageDirectory().getPath()
			+ "/beautygallery/image_cache";

	@Override
	public void onCreate() {
		super.onCreate();
		Logs.setsApplicationTag(BeautyGalleryApp.class.getSimpleName().toString());
		Logs.setsIsLogEnabled(true);
		// 初始化Volley
		initVolley();
		// 初始化Universal-ImageLoader
		initImageLoader();
		
		initCustomUtil();
	}

	private void initCustomUtil() {
		CustomUtil.getInstance().init(getApplicationContext());
	}

	private void initImageLoader() {
		File cacheDir = new File(CACHE_DIR);
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
		.memoryCacheExtraOptions(480, 800)
		// max width, max height，即保存的每个缓存文件的最大长宽
		.threadPoolSize(3)
		// 线程池内加载的数量
		.threadPriority(Thread.NORM_PRIORITY - 2)
		//				.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new LruMemoryCache(8 * 1024 * 1024))
		// You can pass your own memory cache
		// implementation/你可以通过自己的内存缓存实现
		.memoryCacheSize(8 * 1024 * 1024)
		.discCacheSize(50 * 1024 * 1024)
		.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密  
		.tasksProcessingOrder(QueueProcessingType.FIFO)
		.discCacheFileCount(500)
		// 缓存的文件数量
		.discCache(new UnlimitedDiskCache(cacheDir))
		// 自定义缓存路径
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
		.imageDownloader(
				new BaseImageDownloader(getApplicationContext(),
						5 * 1000, 30 * 1000))//.writeDebugLogs()
						.build();// 开始构建
		ImageLoaderUtil.setConfiguration(configuration);
		ImageLoaderUtil.getInstance().init(getApplicationContext());
	}

	private void initVolley() {
		VolleyUtil.init(getApplicationContext());
	}

}
