package com.example.users.activity;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.users.R;
import com.example.users.model.UserModel;
import com.example.users.vo.ResponseData;
import com.example.users.vo.User;

public class AddUserActivity extends BaseActivity {

	public static final int USER_ADDED = 201;
	
	private EditText usernameEditText;
	private EditText passwordText;
	private EditText nameEditText;
	private RadioGroup genderRaioGroup;
	private EditText nickNameEditText;
	private EditText informationEditText;

	private UserModel um = new UserModel();
	
//	private Handler handler;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduser);

//		handler = new Handler();
		intent = getIntent();
		
		usernameEditText = (EditText) findViewById(R.id.usernameEditText);
		passwordText = (EditText) findViewById(R.id.passwordText);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		genderRaioGroup = (RadioGroup) findViewById(R.id.genderRaioGroup);
		nickNameEditText = (EditText) findViewById(R.id.nickNameEditText);
		informationEditText = (EditText) findViewById(R.id.informationEditText);
	}

	/**
	 * 保存用户信息
	 * @param view
	 */
	public void saveUser(View view) {
		// 获取用户输入数据
		String username = usernameEditText.getText().toString();
		String password = passwordText.getText().toString();
		String name = nameEditText.getText().toString();
		String gender = ((RadioButton) findViewById(genderRaioGroup
				.getCheckedRadioButtonId())).getText().toString();
		String nickName = nickNameEditText.getText().toString();
		String information = informationEditText.getText().toString();

		// 封装数据到对象,并保存到数据库
		final User user = new User(username, password, name, nickName, gender,
				information);
		
		new Thread(){
			public void run() {
				try {
					ResponseData rd = um.addUser(user);
					if(rd.b) {
						intent.putExtra("newUser", user);
						
						setResult(USER_ADDED, intent);
						finishActivity(MainActivity.TO_ADD_USER);
					}
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							intent.putExtra("newUser", user);
//							
//							setResult(USER_ADDED, intent);
//							finishActivity(MainActivity.TO_ADD_USER);
//						}
//					});
					
				} catch (HttpException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
