package com.example.users.activity;

import com.example.users.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void toAdd(View view) {
	    Intent i = new Intent();
	    i.setAction("com.user.ADD_USER");
	    i.addCategory("android.intent.category.DEFAULT");
	    
	    startActivity(i);
	}
}
