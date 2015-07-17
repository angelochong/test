package com.chong.game.util;

import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.chong.game.BaseActivity;
import com.chong.game.BaseTask;
import com.chong.game.DataCallback;
import com.chong.game.parser.BaseParser;
import com.chong.game.vo.RequestVo;

public class NetUtil {

	public static final String PASER_ERROR = "paser_error";
	public static final String SERVER_ERROR = "server_error";
	public static final String CLIENT_ERROR = "client_error";
	public static final String NET_ERROR = "net_error";

	private static HttpRequestBase initRequest(RequestVo vo, String type)
			throws Exception {
		String uri = Constant.SERVER_URL.concat(vo.requestUrl);
		if (type.equals("get")) {
			if (vo.requestDataMap != null) {
				if (uri.contains("&")) {
					uri += "&";
				} else if (!uri.endsWith("?")) {
					uri += "?";
				}
				for (Map.Entry<String, String> entry : vo.requestDataMap
						.entrySet()) {
					uri = uri + entry.getKey() + "=" + entry.getValue() + "&";
				}
				if (uri.length() > 0) {
					uri = uri.substring(0, uri.length() - 1);
				}
			}
			return new HttpGet(uri);
		} else if (type.equals("post")) {
			HttpPost post = new HttpPost(uri);
			if (vo.requestDataMap != null) {
				ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
				for (Map.Entry<String, String> entry : vo.requestDataMap
						.entrySet()) {
					BasicNameValuePair pair = new BasicNameValuePair(
							entry.getKey(), entry.getValue());
					pairList.add(pair);
				}
				HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
				post.setEntity(entity);
			}
			return post;
		} else {
			HttpPost post = new HttpPost(uri);
			MultipartEntity reqEntity = new MultipartEntity();
			if (vo.requestDataMap != null) {
				for (Map.Entry<String, String> entry : vo.requestDataMap
						.entrySet()) {
					reqEntity.addPart(
							entry.getKey(),
							new StringBody(entry.getValue(), Charset
									.forName("UTF-8")));
				}
			}
			if (vo.nameList != null) {
				for (int i = 0; i < vo.nameList.size(); i++) {
					reqEntity.addPart(vo.nameList.get(i), new FileBody(
							vo.fileList.get(i)));
				}
			}
			post.setEntity(reqEntity);
			return post;
		}
	}


	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getDataFromServer(RequestVo reqVo,
			DataCallback callBack, String type) {
		BaseTask taskThread = new BaseTask(reqVo, type, callBack);
		ThreadPoolManager.getInstance().addTask(taskThread);
	}

	public static void request(final RequestVo vo, String type,
			final DataCallback<Object> callBack) {
		if (NetUtil.hasNetwork(vo.context)) {
			try {
				final HttpResponse response = HttpManager.execute(
						initRequest(vo, type), vo.context);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					Log.e("response string->", result);
					result=result.replace("null","");
					try {
						JSONObject json = new JSONObject(result);
						final String code = json.getString("errno");
						if (code.equals("0")) {// 成功返回数据
							final Object data = BaseParser.parseJSON(json,
									vo.obj);
							BaseActivity.getHandler().post(new Runnable() {
								@Override
								public void run() {
									if(null!=callBack){
										callBack.processData(data, true);
									}
								}

							});
						} else {
							showErrorMessage(vo.context,callBack,code,json.getString("error"));
						}
					} catch (JSONException e) {
						showErrorMessage(vo.context,callBack,PASER_ERROR,"数据错误");
					}
				} else {
					showErrorMessage(vo.context,callBack,SERVER_ERROR,"服务器异:"
							+ response.getStatusLine().getStatusCode());
				}
			} catch (ConnectTimeoutException e) {
				showErrorMessage(vo.context,callBack,CLIENT_ERROR,"网络连接超时");
			} catch (SocketTimeoutException e) {
				showErrorMessage(vo.context,callBack,CLIENT_ERROR,"网络连接超时");
			} catch (Exception e) {
				showErrorMessage(vo.context,callBack,CLIENT_ERROR,"网络连接异常");
			}
		} else {
			showErrorMessage(vo.context,callBack,NET_ERROR,"当前无网络，请检查网络设置！");
		}

	}

	private static void showErrorMessage(final Context context,
			final DataCallback<Object> callBack, final String error,
			final String message) {
		BaseActivity.getHandler().post(new Runnable() {
			@Override
			public void run() {
				if(null!=callBack){
					callBack.processError(error, message, context);
				}
			}
		});
	}

}