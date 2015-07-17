package com.chong.game.activity;

import java.util.HashMap;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chong.game.BaseActivity;
import com.chong.game.R;
import com.chong.game.util.NetUtil;
import com.chong.game.vo.RequestVo;

public class GameScence1Activity extends BaseActivity{
    //test1
	//test2
    //test3
    //test4
    //test5
    //test6
    //test7
    //test8
    //test9
    //test10
    //on develop test1
	private View loginButton;
	private View registerButton;
	private View usersButton;
	private View searchButton;
	private View sendButton;
	private View addFriendButton;
	private View getMessageButton;
	private View testButton;
	private View publisherButton;
	private View getMoodButton;
	private View getFriendsButton;
	private View agreeFriendButton;
	private View updateUserDataButton;
	private View writeDailyButton;
	private View readDailyButton;
	private View dynamicButton;
	private EditText usernameText;
	private EditText passwordText;
	//7b9c4c64b461de62feecb557ec2bb9
	
	@Override
	protected void loadViewLayout() {
		super.loadViewLayout();
		setContentView(R.layout.game_scence_activity1);
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		loginButton=findViewById(R.id.loginbutton);
		registerButton=findViewById(R.id.registbutton);
		usersButton=findViewById(R.id.getusersbutton);
		sendButton=findViewById(R.id.sendbutton);
		addFriendButton=findViewById(R.id.addfriendbutton);
		getMessageButton=findViewById(R.id.getmessagebutton);
		testButton=findViewById(R.id.testbutton);
		getMoodButton=findViewById(R.id.getmoodbutton);
		publisherButton=findViewById(R.id.publishmoodbutton);
		usernameText=(EditText)findViewById(R.id.username);
		passwordText=(EditText)findViewById(R.id.password);
		getFriendsButton=findViewById(R.id.getfriendbutton);
		agreeFriendButton=findViewById(R.id.agreefriendbutton);
		searchButton=findViewById(R.id.searchbutton);
		updateUserDataButton=findViewById(R.id.updateuserdatabutton);
		writeDailyButton=findViewById(R.id.writedailybutton);
		readDailyButton=findViewById(R.id.getdailybutton);
		dynamicButton=findViewById(R.id.getdynamicbutton);
	}

	@Override
	protected void setViewData() {
		super.setViewData();
	}

	@Override
	protected void setListener() {
		super.setListener();
		getFriendsButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		usersButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		getMessageButton.setOnClickListener(this);
		addFriendButton.setOnClickListener(this);
		testButton.setOnClickListener(this);
		publisherButton.setOnClickListener(this);
		getMoodButton.setOnClickListener(this);
		agreeFriendButton.setOnClickListener(this);
		searchButton.setOnClickListener(this);
		updateUserDataButton.setOnClickListener(this);
		writeDailyButton.setOnClickListener(this);
		readDailyButton.setOnClickListener(this);
		dynamicButton.setOnClickListener(this);
	}

	@Override
	protected void onClickEvent(View paramView) {
		super.onClickEvent(paramView);
		if(paramView.getId()==R.id.loginbutton){
			String username=usernameText.getEditableText().toString();
			String password=passwordText.getEditableText().toString();
			if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
				showToast("用户名或密码错误");
			}else{
				login(username,password);
			}
		}else if(paramView.getId()==R.id.registbutton){
			String username=usernameText.getEditableText().toString();
			String password=passwordText.getEditableText().toString();
			if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
				showToast("用户名或密码错误");
			}else{
				regist(username,password);
			}
		}else if(paramView.getId()==R.id.getusersbutton){
			getAllUsers();
		}else if(paramView.getId()==R.id.sendbutton){
			send();
		}else if(paramView.getId()==R.id.getmessagebutton){
			getMessage();
		}else if(paramView.getId()==R.id.addfriendbutton){
			addFriend();
		}else if(paramView.getId()==R.id.publishmoodbutton){
			publish();
		}else if(paramView.getId()==R.id.getmoodbutton){
			getMood();
		}else if(paramView.getId()==R.id.testbutton){
			test();
		}else if(paramView.getId()==R.id.getfriendbutton){
			getFriends();
		}else if(paramView.getId()==R.id.agreefriendbutton){
			agreeFriend();
		}else if(paramView.getId()==R.id.searchbutton){
			search();
		}else if(paramView.getId()==R.id.updateuserdatabutton){
			updataUserData();
		}else if(paramView.getId()==R.id.writedailybutton){
			writeDaily();
		}else if(paramView.getId()==R.id.getdailybutton){
			readDaily();
		}else if(paramView.getId()==R.id.getdynamicbutton){
			getDynamic();
		}
	}

	private void getDynamic(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/zone/getdynamic/getdynamic";
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "get");
	}
	
	private void readDaily(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/zone/readdaily/readdaily";
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "get");
	}
	
	private void writeDaily(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/zone/writedaily/writedaily";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("dailytitle", "fuck");
		vo.requestDataMap.put("dailydata", "hahahahhahahahahhahah");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void search(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/searchusers/searchusers";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("key", "che");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "get");
	}
	
	private void agreeFriend(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/friends/agree/agree";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("requestname", "haoshijie");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void login(String name,String password){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/login/login";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("username", name);
		vo.requestDataMap.put("password", password);
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void regist(String name,String password){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/register/register";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("username", name);
		vo.requestDataMap.put("password", password);
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void send(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/friends/sendchat/sendchat";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("chatto", "haoshijie");
		vo.requestDataMap.put("chatdata","hellword");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void updataUserData(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/setdata/setdata";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("sex", "1");
		vo.requestDataMap.put("address","beijing");
		vo.requestDataMap.put("selfintroduce", "haha");
		vo.requestDataMap.put("borndate","1988");
		vo.requestDataMap.put("occupation", "it");
		vo.requestDataMap.put("headicon","www.baidu.com");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void getAllUsers(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/allusers/allusers";
		vo.requestDataMap = new HashMap<String, String>();
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void getMessage(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/friends/getchat/getchat";
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void addFriend(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/friends/addfriends/addfriends";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("responsename","haoshijie");
		vo.requestDataMap.put("requestmessage","哈");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void getMood(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/zone/getmood/getmood";
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void publish(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/zone/sendmood/sendmood";
		vo.requestDataMap = new HashMap<String, String>();
		vo.requestDataMap.put("mooddata","哈啊啊啊");
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void getFriends(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/friends/getfriends/getfriends";
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
	
	private void test(){
		RequestVo vo = new RequestVo();
		vo.requestUrl = "chongroot/webgame/user/user/register?type=fuck";
		vo.requestDataMap = new HashMap<String, String>();
		vo.obj = Object.class;
		vo.context = context;
		NetUtil.getDataFromServer(vo, null, "post");
	}
}
