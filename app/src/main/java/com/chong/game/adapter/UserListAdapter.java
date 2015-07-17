package com.chong.game.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chong.game.vo.User;

public class UserListAdapter extends BaseAdapter{

	private ArrayList<User> userList;
	private Context context;
	
	public UserListAdapter(Context context,ArrayList<User> list){
		this.context=context;
		userList=list;
	}
	
	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			arg1 = new TextView(context);
			((TextView)arg1).setText(userList.get(arg0).getUsername());
		}
		return arg1;
	}

}
