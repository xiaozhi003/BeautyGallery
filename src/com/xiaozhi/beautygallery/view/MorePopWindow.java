package com.xiaozhi.beautygallery.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.xiaozhi.beautygallery.R;

public class MorePopWindow extends PopupWindow {

	private View mMoreView;
	private LayoutInflater mInflater;
	private Context mContext;
	private GridView mGridView;
	private ArrayAdapter<String> mAdapter;
	private List<String> mList;
	private String[] mBeautyTypes;
	private PopupWindow mPopupWindow;
	private int mCurrentPosition = 0;

	public MorePopWindow(Context context) {
		super(context);
		this.mContext = context;
		mPopupWindow = this;
		mInflater = LayoutInflater.from(mContext);
		mMoreView = mInflater.inflate(R.layout.view_more_pop, null);
		mGridView = (GridView) mMoreView.findViewById(R.id.popGridView);
		initStrings();
		mAdapter = new ArrayAdapter<String>(mContext,
				R.layout.view_more_pop_item, mBeautyTypes);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mOnMorePopWindowItemClickListener != null) {
					mOnMorePopWindowItemClickListener.onItemClick(position,
							mBeautyTypes[position]);
				}
				parent.getChildAt(mCurrentPosition).setBackgroundColor(
						Color.TRANSPARENT);
				view.setBackgroundResource(R.drawable.shape_more_pop);
				mCurrentPosition = position;
				mPopupWindow.dismiss();
				Log.d("More", "onItemClick:" + position);
			}
		});

		// 设置SelectPicPopupWindow的View
		this.setContentView(mMoreView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		// this.setAnimationStyle(R.style.popwin_anim_style);
		// 实例化一个ColorDrawable颜色为透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}

	private void initStrings() {
		mBeautyTypes = mContext.getResources().getStringArray(
				R.array.beauty_array);
		mList = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			mList.add("item" + i);
		}
	}

	/**
	 * 重写该方法，让内部视图在现实的时候加入出场动画
	 */
	@Override
	public void showAsDropDown(View anchor) {
		super.showAsDropDown(anchor);
		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_top_in);
		mMoreView.startAnimation(animation);
	}

	/**
	 * 重写该方法，当内部视图动画结束的时候再调用父类的dismiss方法
	 */
	@Override
	public void dismiss() {
		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_top_out);
		mMoreView.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						cancel();
					}
				});
			}
		});
	}

	private void cancel() {
		super.dismiss();
	}

	public interface OnMorePopWindowItemClickListener {
		public void onItemClick(int position, String item);
	}

	private OnMorePopWindowItemClickListener mOnMorePopWindowItemClickListener;

	public void setOnMorePopWindowItemClickListener(
			OnMorePopWindowItemClickListener onMorePopWindowItemClickListener) {
		mOnMorePopWindowItemClickListener = onMorePopWindowItemClickListener;
	}
}
