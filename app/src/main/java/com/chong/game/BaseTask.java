package com.chong.game;

import com.chong.game.util.NetUtil;
import com.chong.game.vo.RequestVo;


public class BaseTask implements Runnable {
	private RequestVo reqVo;
	private String type;
	private DataCallback<Object> callBack;

	public BaseTask(RequestVo reqVo, String type,
			DataCallback<Object> callBack) {
		this.reqVo = reqVo;
		this.type = type;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		NetUtil.request(reqVo, type, callBack);
	}

}
