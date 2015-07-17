package com.chong.game.vo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class RequestVo {
	public String requestUrl;
	public String absUrl;
	public Context context;
	public HashMap<String, String> requestDataMap;
	public ArrayList<String> nameList;
	public ArrayList<File> fileList;
	public Class<?> obj;
}
