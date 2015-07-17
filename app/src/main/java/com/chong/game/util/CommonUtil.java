package com.chong.game.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;


public class CommonUtil {
	private static long lastClickTime; // 避免控件被重复点击
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	// 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
	public static boolean hasBind(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");
		if ("ok".equalsIgnoreCase(flag)) {
			return true;
		}
		return false;
	}

	public static void setBind(Context context, boolean flag) {
		String flagStr = "not";
		if (flag) {
			flagStr = "ok";
		}
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("bind_flag", flagStr);
		editor.commit();
	}

	public static int getImageHeight(Activity act) {
		return getScreenWidth(act) * 3 / 4;
	}

	public static int getImageHeight1(Activity act) {
		return getScreenWidth(act) * 5 / 8;
	}

	public static int getScreenHeight(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static int getScreenWidth(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static float getDensity(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
	}

	public static String[] strToStringArray(String string) {
		if (string == null) {
			return null;
		}
		return string.split("\\|");
	}

	// 避免控件被重复点击

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 保存值到SharedPreferences中
	 * 
	 * @param context
	 * @param path
	 * @param key
	 * @param value
	 */
	public static void saveSettingValue(Context context, String path,
			String key, String value) {
		saveValue(context, context.getSharedPreferences(path, 0), key, value);
	}

	public static void saveSettingIntValue(Context context, String path,
			String key, int value) {
		saveIntValue(context, context.getSharedPreferences(path, 0), key, value);
	}

	public static void saveSettingLongValue(Context context, String path,
			String key, long value) {
		saveLongValue(context, context.getSharedPreferences(path, 0), key,
				value);
	}

	public static void saveSettingBooleanValue(Context context, String path,
			String key, boolean value) {
		saveBooleanValue(context, context.getSharedPreferences(path, 0), key,
				value);
	}

	public static void saveValue(Context context, SharedPreferences sp,
			String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	public static void saveBooleanValue(Context context, SharedPreferences sp,
			String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	public static void saveIntValue(Context context, SharedPreferences sp,
			String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	public static void saveLongValue(Context context, SharedPreferences sp,
			String key, long value) {
		sp.edit().putLong(key, value).commit();
	}



	/**
	 * 保存对象到SharedPreferences
	 * 
	 * @param context上下文
	 * @param path文件名
	 * @param key传入的key
	 * @param value传入的对象
	 * @throws IOException
	 * @throws Throwable
	 */
	public static void saveObj(Context context, String path, String key,
			Object value) throws IOException {
		saveObj(context,
				context.getSharedPreferences(path, Activity.MODE_PRIVATE), key,
				value);
	}

	public static void saveObj(Context context, SharedPreferences sp,
			String key, Object value) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(value);
		String objString = new String(Base64.encode(
				byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
		sp.edit().putString(key, objString).commit();
		objectOutputStream.close();
	}

	/**
	 * 获取sharedPreferences中的所有对象的集合
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static Map<String, ?> getObjAll(Context context, String path) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				path, Activity.MODE_PRIVATE);
		Map<String, ?> map = sharedPreferences.getAll();
		return map;

	}

	/**
	 * 获取sharedPreferences中的对象
	 * 
	 * @param context
	 * @param path
	 *            文件名称
	 * @param key
	 *            对应的key
	 * @return
	 * @throws IOException
	 * @throws StreamCorruptedException
	 * @throws ClassNotFoundException
	 */
	public static Object getObj(Context context, String path, String key)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		return getObj(context,
				context.getSharedPreferences(path, Activity.MODE_PRIVATE), key);
	}

	public static Object getObj(Context context, SharedPreferences sp,
			String key) throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		String str = sp.getString(key, "");
		if (str.length() <= 0)
			return null;
		Object obj = null;
		byte[] mobileBytes = Base64.decode(str.toString().getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				mobileBytes);
		ObjectInputStream objectInputStream;
		objectInputStream = new ObjectInputStream(byteArrayInputStream);
		obj = objectInputStream.readObject();
		objectInputStream.close();
		return obj;
	}

	// 点击空白区域 隐藏软键盘
	public static void hideSoft(Activity act, InputMethodManager imm,
			MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (act.getCurrentFocus() != null) {
				if (act.getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(act.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
	}

	/**
	 * 从SharedPreferences中取值
	 * 
	 * @param context
	 * @param path
	 * @param key
	 * @return
	 */
	public static String getSettingValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		String value = settingInfo.getString(key, null);
		return value;
	}

	public static String getSettingStringValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		String value = settingInfo.getString(key, "0");
		return value;
	}

	public static int getSettingIntValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		int value = settingInfo.getInt(key, 0);
		return value;
	}

	public static boolean getSettingBooleanValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		boolean value = settingInfo.getBoolean(key, true);
		return value;
	}

	public static boolean getSettingBooleanValue(Context context, String path,
			String key, boolean defaultvalue) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		boolean value = settingInfo.getBoolean(key, defaultvalue);
		return value;
	}

	public static long getSettingLongValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		long value = settingInfo.getLong(key, 0l);
		return value;
	}

	/**
	 * 删除SharedPreferences中的一个值
	 * 
	 * @param context
	 * @param path
	 * @param key
	 * @param value
	 */

	public static void deleteSettingValue(Context context, String path,
			String key) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		Editor editor = settingInfo.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 删除SharedPreferences中的全部的值
	 * 
	 * @param context
	 * @param path
	 * @param key
	 * @param value
	 */

	public static void deleteSettingAllValue(Context context, String path) {
		SharedPreferences settingInfo = context.getSharedPreferences(path, 0);
		settingInfo.edit().clear().commit();
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 判断是否为一个手机号
	 * 
	 * 
	 */

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		/*
		 * 可接受的电话格式有：
		 */
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		/*
		 * 可接受的电话格式有：
		 */
		String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param rStr源字符串
	 * @param rFix要查找替换的字符串
	 * @param rRep替换成的字符串
	 * @return
	 */
	public static String StrReplace(String rStr, String rFix, String rRep) {
		int l = 0;
		String gRtnStr = rStr;
		do {
			l = rStr.indexOf(rFix, l);
			if (l == -1)
				break;
			gRtnStr = rStr.substring(0, l) + rRep
					+ rStr.substring(l + rFix.length());
			l += rRep.length();
			rStr = gRtnStr;
		} while (true);
		return gRtnStr.substring(0, gRtnStr.length());
	}

	/**
	 * 获取系统版本号
	 * 
	 * @return String
	 */
	public static String getOs() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return String
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 获取手机厂商
	 * 
	 * @return String
	 */
	public static String getBrand() {
		return Build.MANUFACTURER;
	}

	/**
	 * 判断是否为英文字母
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isAlpha(String str) {
		final Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 访问联系人数据库.
	 * 
	 * @return List
	 */
	public static List<Map<String, String>> readContacts(Context context) {

		final List<Map<String, String>> contactsList = new ArrayList<Map<String, String>>();
		try {// 此处捕获异常仅针对miui4.2的几个有问题的版本
			final List<String> tempList = new ArrayList<String>();
			Map<String, String> contactsMap;
			Cursor cursor = null;
			final Uri primaryUri = Phone.CONTENT_URI;

			if (Build.VERSION.RELEASE != null
					&& Build.VERSION.RELEASE.contains("2.1")) {
				cursor = context.getContentResolver().query(
						primaryUri,
						new String[] { Phone.DISPLAY_NAME, Phone.NUMBER,
								Phone.RAW_CONTACT_ID, }, null, null, null);
			} else {
				cursor = context.getContentResolver().query(
						primaryUri,
						new String[] { Phone.DISPLAY_NAME, Phone.NUMBER,
								Phone.RAW_CONTACT_ID, "sort_key", }, null,
						null, "sort_key");
			}
			if (cursor != null) {
				String number = "";
				String name = "";
				String rawContactId = "";
				final int count = cursor.getCount();
				for (int i = 0; i < count; i++) {
					boolean nextRead = false;
					contactsMap = new HashMap<String, String>();
					cursor.moveToPosition(i);
					name = cursor.getString(0);
					number = cursor.getString(1);
					rawContactId = cursor.getString(2);
					for (int j = 0; j < tempList.size(); j++) {
						if (tempList.get(j).equals(rawContactId)) {
							nextRead = true;
						}
					}
					if (!nextRead) {
						tempList.add(rawContactId);
						contactsMap.put("name", name);
						contactsMap.put("number", number);
						contactsList.add(contactsMap);
					}
				}
				if (!cursor.isClosed()) {
					cursor.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactsList;

	}

	/**
	 * 获取某个文件夹的大小
	 */

	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024;
				return size;
			}
		} else {
			return 0.0;
		}

	}

	/**
	 * 返回当前系统时间的年月日
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static synchronized String getTime() {
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return timeformat.format(date);
	}


	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}
	
	public static String formatPhone(String number) {
		StringBuffer sb = new StringBuffer();
		char c;
		for (int i = 0; i < number.length(); i++) {
			c = number.charAt(i);
			if (48 <= c && c <= 57)
				sb.append(c);
		}
		return sb.toString();
	}
}
