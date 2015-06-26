package com.xiaozhi.beautygallery.domain;

/**
 * 图片信息实体类
 * 
 * @author Xiaozhi
 */
public class Image {

	private String mId;
	private String mUrl;
	private String mDescription;
	private int mWidth;
	private int mHeight;

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int width) {
		mWidth = width;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int height) {
		mHeight = height;
	}

}
