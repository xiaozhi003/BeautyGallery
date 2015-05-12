package com.xiaozhi.beautygallery.view;

import com.xiaozhi.beautygallery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * 加载更多底部视图
 * @author asus
 *
 */
public class FooterView extends LinearLayout {
	
	private Context mContext;
	
	public static final int HIDE = 0;
	public static final int MORE = 1;
	public static final int LOADING = 2;
	public static final int BADENTWORK = 3;
	
	private ProgressBar mProgressBar;
	private TextView mTextView;
	private Button mButton;
	
	private int curStatus;
	
	public FooterView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	// 初始化FooterView
	private void init(){
		LayoutInflater.from(mContext).inflate(R.layout.view_footer, this, true);
		mProgressBar = (ProgressBar) findViewById(R.id.footer_loading);
		mTextView = (TextView) findViewById(R.id.footview_text);
		mButton = (Button) findViewById(R.id.footview_button);
		setStatus(MORE);
	}
	
	public void setStatus(int status){
		curStatus = status;
		switch(status){
		case HIDE:
			setVisibility(View.GONE);
			break;
		case MORE:
			mProgressBar.setVisibility(View.GONE);
			mButton.setVisibility(View.GONE);
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("点击加载更多");
			this.setVisibility(View.VISIBLE);
			break;
		case LOADING:
			mProgressBar.setVisibility(View.VISIBLE);
			mButton.setVisibility(View.GONE);
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("正在加载...");
			this.setVisibility(View.VISIBLE);
			break;
		case BADENTWORK:
			mProgressBar.setVisibility(View.GONE);
			mButton.setVisibility(View.VISIBLE);
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("网络连接有问题");
			this.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public int getStatus(){
		return curStatus;
	}

}