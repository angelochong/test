package com.chong.game.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class BaseParser {

	public static Object parseJSON(JSONObject json, Class<?> obj)
			throws JSONException {
		String data = json.getString("data");

		if (data != null && data.length() > 0) {
			if (data.startsWith("["))
				return JSON.parseArray(data, obj);
			else if (data.startsWith("{")) {
				return JSON.parseObject(data, obj);
			} else
				return null;
		}
		throw new JSONException("format error");
	}

	public static Object parseOutJSON(JSONObject json, Class<?> obj)
			throws JSONException {
		String data = json.toString();

		if (data != null && data.length() > 0) {
			if (data.startsWith("["))
				return JSON.parseArray(data, obj);
			else if (data.startsWith("{"))
				return JSON.parseObject(data, obj);
		}
		throw new JSONException("format error");
	}
}
