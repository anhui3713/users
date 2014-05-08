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
 * ������
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity{

	public static final int TO_ADD_USER = 101;
	
	/**
	 * �û��б�����
	 */
	private List users;
	
	/**
	 * ��ʾ�û��б�Ŀؼ�
	 */
	private ListView usersListView;
	
	/**
	 * ���ڶ��û���Ϣ�����ģ��
	 */
	private UserModel um = new UserModel();
	/**
	 * ���߳��е�handler
	 */
	private Handler handler;
	
	/**
	 * ���洴���¼�
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();
		
		// �����û��б�
		usersListView = (ListView) findViewById(R.id.userListView);
		
		users = new ArrayList();
		
		// ������ʾ������
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
	 * �����û�������ʾ
	 */
	private void loadUsers() {
		new Thread() {
			public void run() {
				try {
					// ��ѯ
					final List<?> userList = um.listUser();
					
					// ִ�жԽ���ĸ���
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
	 * �˵���ʼ���¼�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * �ֶ�ˢ���ܾ�
	 * @param item
	 */
	public void refresh(MenuItem item) {
		users.clear();
		loadUsers();
	}

	/**
	 * ��ת����ӽ���
	 * @param view
	 */
	public void toAdd(MenuItem item) {
	    Intent i = new Intent();
	    i.setAction("com.user.ADD_USER");
	    i.addCategory("android.intent.category.DEFAULT");
	    
	    startActivityForResult(i, TO_ADD_USER);
	}
	
	/**
	 * �������activity������������֮��Ĵ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		/**
		 * ������û����
		 */
		if(requestCode == TO_ADD_USER && resultCode == AddUserActivity.USER_ADDED) {
			User nu = (User) data.getSerializableExtra("newUser");
			
			users.add(nu);
		}
	}
}
