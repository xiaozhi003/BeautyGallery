package com.xiaozhi.beautygallery.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

import com.enrique.stackblur.StackBlurManager;
import com.xiaozhi.beautygallery.R;

/**
 * 欢迎界面
 * 
 * @author Xiaozhi
 */
public class WelcomeActivity extends Activity {

	private WelcomeActivity mActivity;
	private ImageView mWelcomeImg;
	private View mBlurView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mActivity = this;

		initViews();
	}

	private void initViews() {
		mWelcomeImg = (ImageView) findViewById(R.id.welcomeImg);
		mBlurView = findViewById(R.id.blurView);

		mWelcomeImg.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						mWelcomeImg.getViewTreeObserver()
								.removeOnPreDrawListener(this);
						// 加载背景图构建Bitmap
						mWelcomeImg.buildDrawingCache();
						// 获取ImageView缓存的Bitmap
						Bitmap bmp = mWelcomeImg.getDrawingCache();
						// 在异步任务中执行模糊
						new BlurTask().execute(bmp);
						return true;
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		startAnimation();
	}

	/**
	 * 进场动画
	 */
	private void startAnimation() {
		// 图片缩放动画
		ObjectAnimator imageAnimator = ObjectAnimator.ofFloat(mWelcomeImg,
				"scaleX", 1.0f, 1.1f).setDuration(2000);
		ObjectAnimator imageAnimator1 = ObjectAnimator.ofFloat(mWelcomeImg,
				"scaleY", 1.0f, 1.1f).setDuration(2000);
		imageAnimator.start();
		imageAnimator1.start();
		imageAnimator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				startActivity(new Intent(mActivity, MainActivity.class));
				mActivity.finish();
			}
		});
		// 由于图片放大相应的模糊的View也需要同样放大
		mBlurView.setScaleX(1.1f);
		mBlurView.setScaleY(1.1f);
	}

	/**
	 * 模糊图像
	 */
	@SuppressLint("NewApi")
	private Bitmap blur(Bitmap bkg, View view) {

		float radius = 2;
		float scaleFactor = 8;

		// 创建需要模糊的Bitmap
		Bitmap overlay = Bitmap.createBitmap(
				(int) (view.getMeasuredWidth() / scaleFactor),
				(int) (view.getMeasuredHeight() / scaleFactor),
				Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
				/ scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);

		// 模糊Bitmap(StackBlur开源库实现)
		StackBlurManager stackBlurManager = new StackBlurManager(overlay);
		stackBlurManager.processNatively((int) radius);
		overlay = stackBlurManager.returnBlurredImage();

		bkg.recycle();
		return overlay;
	}

	/**
	 * 模糊异步任务
	 */
	private class BlurTask extends AsyncTask<Bitmap, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Bitmap... params) {
			return blur(params[0], mBlurView);
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// 将模糊的Bitmap设置到View上
			mBlurView.setBackground(new BitmapDrawable(getResources(), result));
		}
	}

}
