package com.chong.game;

import com.chong.game.util.DialogUtils;

import android.content.Context;
import android.widget.Toast;

public abstract class DataCallback<T> {
	
	public abstract void processData(T paramObject, boolean paramBoolean);

	public void processError(String error, String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		DialogUtils.closeProgressDialog();
	}
}
