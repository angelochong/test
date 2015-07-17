package com.chong.game;

import android.app.Application;

public class MobileApplication extends Application {

	private String token;
	private String userID;
	private String name;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
