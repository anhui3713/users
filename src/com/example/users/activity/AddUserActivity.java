package com.example.users.activity;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.users.R;
import com.example.users.model.UserModel;
import com.example.users.vo.User;

public class AddUserActivity extends BaseActivity {

	private EditText usernameEditText;
	private EditText passwordText;
	private EditText nameEditText;
	private RadioGroup genderRaioGroup;
	private EditText nickNameEditText;
	private EditText informationEditText;

	private UserModel um = new UserModel();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduser);

		usernameEditText = (EditText) findViewById(R.id.usernameEditText);
		passwordText = (EditText) findViewById(R.id.passwordText);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		genderRaioGroup = (RadioGroup) findViewById(R.id.genderRaioGroup);
		nickNameEditText = (EditText) findViewById(R.id.nickNameEditText);
		informationEditText = (EditText) findViewById(R.id.informationEditText);
	}

	public void saveUser(View view) {
		String username = usernameEditText.getText().toString();
		String password = passwordText.getText().toString();
		String name = nameEditText.getText().toString();
		String gender = ((RadioButton) findViewById(genderRaioGroup
				.getCheckedRadioButtonId())).getText().toString();
		String nickName = nickNameEditText.getText().toString();
		String information = informationEditText.getText().toString();

		User user = new User(username, password, name, nickName, gender,
				information);

		System.out.println(user);
		
		try {
			um.addUser(user);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
