package com.example.users.model;

import org.apache.http.HttpException;
import org.json.JSONException;

import com.example.users.utils.JSONUtils;
import com.example.users.vo.User;

public class UserModel {
	
	public void addUser(User user) throws HttpException, JSONException {
		String json = JSONUtils.object2JSON(user);
		System.out.println("json:" + json);
		
//			HttpUtils.postJSON("addUser.action", JSONUtils.object2JSON(user));
	}
	
}
