package com.chong.game.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;

public class HttpManager {
	static final String ENCODE = "UTF-8";
	static final String COOKIE = "Cookie";
	static final String SET_COOKIE = "Set-Cookie";
	static final String DIVERSION_VERSION = "DIVERSION-VERSION";
	static final String SESSION_TOKEN = "token";

	static DefaultHttpClient client;
//	public static String sessionToken = "a7e716d5b29068026b53c70bac98e642";
	public static String sessionToken = "cc7b9c4c64b461de62feecb557ec2bb9";

	static final String HTTP = "http";
	static final String HTTPS = "https";
	/** 设置链接�? */
	static final int CONN_PER_ROUTE_BEAN = 20;
	/** 设置�?大连接数 */
	static final int MAX_TOTAL_CONNECTIONS = 200;
	/** 设置链接超时时间 */
	static final int CONNECTION_TIME_OUT = 30 * 1000;
	/** 设置socket超时时间 */
	static final int SOCKET_TIME_OUT = 30 * 1000;
	/** 设置sokect缓存�?大字节数 */
	static final int SOCKET_BUFFER_SIZE = 8 * 1024;;
	/** http协议代理端口 */
	static final int HTTP_PROXY_PORT = 80;
	/** https协议代理端口 */
	static final int HTTPS_PROXY_PORT = 443;

	public static void init(Context context) {

	}

	/**
	 * 方法功能说明：初始化HttpClient对象
	 * 
	 */
	public synchronized static DefaultHttpClient getClient(Context context)
			throws Exception {
		if (client == null) {
			// 设置基础数据
			HttpParams params = new BasicHttpParams();// 设置参数列表对象
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);// 设置版本
			HttpProtocolParams.setContentCharset(params, ENCODE);// 设置编码类型
			HttpProtocolParams.setUseExpectContinue(params, false);// 设置异常是否继续
			HttpProtocolParams.setUserAgent(params, getUserAgent(context));
			// 设置超时
			HttpConnectionParams.setStaleCheckingEnabled(params, false);
			HttpConnectionParams.setConnectionTimeout(params,
					CONNECTION_TIME_OUT);// 设置链接超时时间
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIME_OUT);// 设置socket超时时间
			HttpConnectionParams
					.setSocketBufferSize(params, SOCKET_BUFFER_SIZE);// 设置sokect缓存�?大字节数
			// 设置客户端参�?
			HttpClientParams.setRedirecting(params, false);// 设置中是否重定向

			// 设置链接�?
			ConnManagerParams.setMaxConnectionsPerRoute(params,
					new ConnPerRouteBean(CONN_PER_ROUTE_BEAN));// 设置每个路由�?大链接数
			ConnManagerParams.setMaxTotalConnections(params,
					MAX_TOTAL_CONNECTIONS);

			// 设置http/https通讯模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme(HTTP, PlainSocketFactory
					.getSocketFactory(), HTTP_PROXY_PORT));

			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			CustomSocketFactory csf = new CustomSocketFactory(trustStore);
			csf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schReg.register(new Scheme(HTTPS, csf, HTTPS_PROXY_PORT));
			// 生成链接管理对象，把设置的参数和模式设置进去
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			client = new DefaultHttpClient(conMgr, params);
			client.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
				public boolean retryRequest(IOException exception,
						int executionCount, HttpContext context) {
					if (executionCount >= 3)
						return false;
					else
						return true;
				}
			});
		}
		return client;
	}

	private static String getUserAgent(Context context)
			throws NameNotFoundException {
		StringBuffer sb = new StringBuffer("众筹 ");
		sb.append(
				context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0).versionName)
				.append(" android").append(android.os.Build.VERSION.SDK_INT);
		return sb.toString();
	}

	/**
	 * 方法功能说明：发送get请求
	 * 
	 * @param get
	 *            HttpGet类的实例对象
	 * @return HttpResponse 对返回的流，响应头等信息的封�?
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @see BTCHttpConnection#httpSend（）
	 */
	@SuppressWarnings("deprecation")
	public static HttpResponse execute(HttpRequestBase request, Context con) throws Exception {
		request.addHeader(SESSION_TOKEN, sessionToken);//sessiontoken null?
		DefaultHttpClient client = getClient(con);
		// 获取默认通讯代理主机ip
		String proxyHost = android.net.Proxy.getDefaultHost();
		boolean needProxy = (proxyHost != null);
		if (((WifiManager) con.getSystemService(Context.WIFI_SERVICE))
				.isWifiEnabled()) {
			needProxy = false;
		}
		HttpResponse response;
		if (needProxy) {
			response = client.execute(
					new HttpHost(android.net.Proxy.getDefaultHost(),
							android.net.Proxy.getDefaultPort()), request);
		} else {
			response = client.execute(request);
		}
		return response;
	}

	public static void setToken(String token) {
		sessionToken = token;
	}
}
