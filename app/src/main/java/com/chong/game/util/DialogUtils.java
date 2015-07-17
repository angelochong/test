package com.chong.game.util;

import com.chong.game.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DialogUtils {

	public static Dialog loading;
	public static LinearLayout bar;

	public interface DialogListener {
		public void onListDialogOK(int id, CharSequence[] items,
				int selectedItem);

		public void onListDialogCancel(int id, CharSequence[] items);
	}

	public static void startProgressDialog(Context context) {
		if (loading == null || !loading.isShowing()) {
			loading = createProgressDialog(context);
		}
	}

	public static void startProgressDialogLogin(Context context) {
		if (loading == null || !loading.isShowing()) {
			loading = createProgressDialog(context);
		}
	}

	public static void closeProgressDialogLogin() {
		if (loading != null && loading.isShowing())
			loading.dismiss();
	}

	private static Dialog createProgressDialog(Context context) {
		Dialog dlg = new AlertDialog.Builder(context).create();
		dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dlg.show();
		dlg.setCancelable(false);
		View layout = View.inflate(context, R.layout.loadingdialog, null);
		dlg.getWindow().setContentView(layout);
		return dlg;
	}

	public static void closeProgressDialog() {
		if (loading != null && loading.isShowing())
			loading.dismiss();
	}

	public static boolean isDialogShowing() {
		return (loading != null && loading.isShowing());
	}

	public interface AlertDialogListener {
		public void onDialogOK(int id);
		public void onDialogCancel(int id);
	}

	public static void showStandardWhiteDialog(Context context, String title,
			String content, String yes, String no,
			final View.OnClickListener listener,
			final View.OnClickListener cancle) {
		View layout = View.inflate(context, R.layout.standard_dialog, null);
		final Dialog dlg = new AlertDialog.Builder(context).create();
		dlg.setCancelable(false);
		TextView titleTv = (TextView) layout.findViewById(R.id.standard_title);
		if (!TextUtils.isEmpty(title)) {
			titleTv.setText(title);
		} 
		else {
			titleTv.setVisibility(View.GONE);
		}
		TextView contentTv = (TextView) layout
				.findViewById(R.id.standard_content);
		if (!TextUtils.isEmpty(content)) {
			contentTv.setText(content);
		} else {
			contentTv.setVisibility(View.GONE);
		}
		Button yesBt = (Button) layout.findViewById(R.id.standard_yes);
		if (!TextUtils.isEmpty(yes)) {
			yesBt.setText(yes);
		}
		yesBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != listener) {
					listener.onClick(v);
				}
				dlg.dismiss();
			}

		});
		Button noBt = (Button) layout.findViewById(R.id.standard_no);
		if (!TextUtils.isEmpty(no)) {
			noBt.setText(no);
		}
		noBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != cancle)
					cancle.onClick(v);
				dlg.dismiss();
			}

		});
		dlg.show();
		dlg.setContentView(layout);
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.width = CommonUtil.dip2px(context, 278);
		lp.height = CommonUtil.dip2px(context, 148);
		w.setAttributes(lp);
	}

}
