package com.example.users.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.users.R;
import com.example.users.model.UserModel;
import com.example.users.vo.User;

/**
 * 主界面
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity{

	public static final int TO_ADD_USER = 101;
	
	/**
	 * 用户列表数据
	 */
	private List users;
	
	/**
	 * 显示用户列表的控件
	 */
	private ListView usersListView;
	
	/**
	 * 用于对用户信息管理的模型
	 */
	private UserModel um = new UserModel();
	/**
	 * 主线程中的handler
	 */
	private Handler handler;
	
	/**
	 * 界面创建事件
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();
		
		// 加载用户列表
		usersListView = (ListView) findViewById(R.id.userListView);
		
		users = new ArrayList();
		
		// 设置显示适配器
		usersListView.setAdapter(new BaseAdapter() {
			
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null) {
					convertView = LayoutInflater.from(context).inflate(R.layout.users_item, null);
				}
				
				TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
				TextView nickNameTextView = (TextView) convertView.findViewById(R.id.nickNameTextView);
				
				User user = (User) users.get(position);
				
				nameTextView.setText(user.getName() + "(" + user.getGender() + ")");
				nickNameTextView.setText(user.getNickName());
				
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return usersListView.getItemIdAtPosition(position);
			}
			
			@Override
			public Object getItem(int position) {
				return users.get(position);
			}
			
			@Override
			public int getCount() {
				return users.size();
			}
		});
		
		loadUsers();
	}

	/**
	 * 加载用户数据显示
	 */
	private void loadUsers() {
		new Thread() {
			public void run() {
				try {
					// 查询
					final List<?> userList = um.listUser();
					
					// 执行对界面的更新
					handler.post(new Runnable() {
						@SuppressWarnings("unchecked")
						public void run() {
							if(userList != null && userList.size() > 0)
							users.addAll(userList);
						}
					});
				} catch (HttpException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 菜单初始化事件
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 手动刷新塑聚
	 * @param item
	 */
	public void refresh(MenuItem item) {
		users.clear();
		loadUsers();
	}

	/**
	 * 跳转到添加界面
	 * @param view
	 */
	public void toAdd(MenuItem item) {
	    Intent i = new Intent();
	    i.setAction("com.user.ADD_USER");
	    i.addCategory("android.intent.category.DEFAULT");
	    
	    startActivityForResult(i, TO_ADD_USER);
	}
	
	/**
	 * 当另外的activity结束返回数据之后的处理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		/**
		 * 当添加用户完成
		 */
		if(requestCode == TO_ADD_USER && resultCode == AddUserActivity.USER_ADDED) {
			User nu = (User) data.getSerializableExtra("newUser");
			
			users.add(nu);
		}
	}
}
