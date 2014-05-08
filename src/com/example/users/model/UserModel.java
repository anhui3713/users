package com.example.users.model;

import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;

import com.example.users.utils.HttpUtils;
import com.example.users.utils.JSONUtils;
import com.example.users.vo.ResponseData;
import com.example.users.vo.User;

public class UserModel {
	
	/**
	 * ����û���Ϣ
	 * @param user
	 * @throws JSONException 
	 * @throws HttpException 
	 */
	public ResponseData addUser(User user) throws HttpException, JSONException {
		ResponseData rd = (ResponseData) HttpUtils.postJSON("users.save", 
				JSONUtils.object2JSON(user), 
				ResponseData.class);
		
		return rd;
	}
	
	/**
	 * ��ѯ�û���Ϣ�б�
	 * @param r
	 * @throws HttpException 
	 */
	public List listUser() throws HttpException {
		return HttpUtils.getList("users.list", null, User.class);
	}
}
