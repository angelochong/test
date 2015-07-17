package com.chong.game;

import com.chong.game.util.CommonUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 共同的基类，所有activity
 * 
 * @author Sunshine
 * 
 */
public class BaseActivity extends Activity implements View.OnClickListener {
	protected Context context;
	private Toast mToast;
	private float moveFirstPoint;// 第一次滑动的X位置
	private boolean hasGetPoint = false;// 是否已经滑动过的标志位
	private float upPoint;// 抬起时的X位置
	private static Handler mHandler;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		context = this;
		initView();
	}

	@SuppressLint("ShowToast")
	private Toast getToast() {
		if (mToast == null) {
			mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		}
		return mToast;
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		loadViewLayout();
		findViewById();
		setViewData();
		setListener();
		processLogic();
	}

	public void showToast(int msg) {
		getToast().setText(msg);
		getToast().show();
	}

	public void showToast(String msg) {
		getToast().setText(msg);
		getToast().show();
	}

	// 所有activity通过此方法获取handler，不要自行创建
	public static Handler getHandler() {
		if (null == mHandler) {
			mHandler = new Handler(Looper.getMainLooper());
		}
		return mHandler;
	}

	public void onClick(View paramView) {
		onClickEvent(paramView);
	}

	/**
	 * 设置在onCreate中用到的参数
	 */
	protected void setDataBeforeSuper() {
	};

	/**
	 * 加载activity
	 */
	protected void loadViewLayout() {
	};

	/**
	 * 找到组件
	 */
	protected void findViewById() {
	};

	/**
	 * 为控件添加数据
	 */
	protected void setViewData() {
	};

	/**
	 * 给组件注册监听器
	 */
	protected void setListener() {
	};

	/**
	 * @param paramView
	 */
	protected void onClickEvent(View paramView) {
	};

	/**
	 * 向后台请求数据
	 */
	protected void processLogic() {
	};


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (!hasGetPoint) {
				moveFirstPoint = event.getX();
				hasGetPoint = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			upPoint = event.getX();
			if (upPoint - moveFirstPoint > CommonUtil.dip2px(this, 100)
					&& hasGetPoint) {
				finish();
				overridePendingTransition(0, R.anim.activity_finish_anim);
			}
			hasGetPoint = false;
			moveFirstPoint = 0;
			break;
		}
		return false;
	}

}