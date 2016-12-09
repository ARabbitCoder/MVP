package com.liu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class DisplayManager {
	private static DisplayManager instance = new DisplayManager();
	private float value;

	public void init(Context context){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics = context.getResources().getDisplayMetrics();
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			//根据资源ID获取响应的尺寸值
			statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
		}
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;
		screenDpi = displayMetrics.densityDpi;
		value = displayMetrics.scaledDensity;
		Log.d("liu","DisplayManager dddddddddddddd-->"+screenWidth+"x"+screenHeight+",screenDpi:"+screenDpi);
	}
	
	private DisplayManager() {
	 
	}

	public static DisplayManager GetInstance() {
		return instance;
	}

	private static final int STANDARD_WIDTH = 1280;
	private static final int STANDARD_HEIGHT = 720;
	private static final int STANDARD_DENSITY = 160;

	private int screenWidth = 0;
	private int screenHeight = 0;
	private int screenDpi = 0;
	private int statusBarHeight = 0;

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenDpi() {
		return screenDpi;
	}


	public int changeWidthSize(int size) {
		float rate = (float) screenWidth / STANDARD_WIDTH;
		return (int) (size * rate);
	}

	public int changeHeightSize(int size) {
		float rate = (float) screenHeight / STANDARD_HEIGHT;
		return (int) (size * rate);
	}

	public int Dp2Px(int dp) {
		float beishu = screenDpi / 160f;
		int px = (int)(dp * beishu);
		return px;
	}

	public int getStatusBarHeight() {
		return statusBarHeight;
	}
}
