package com.example.users.vo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 9119915147700572658L;
	
	private String _id;
	private String username;
	private String password;
	private String name;
	private String nickName;
	private String remarkName;
	private String gender;
	private String information;

	public User() {
		super();
	}
	public User(String _id) {
		super();
		this._id = _id;
	}
	public User(String username, String password, String name,
			String remarkName, String gender, String information) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.remarkName = remarkName;
		this.gender = gender;
		this.information = information;
	}
	public User(String username, String password, String name, String nickName,
			String remarkName, String gender, String information) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.remarkName = remarkName;
		this.gender = gender;
		this.information = information;
	}
	public User(String _id, String username, String password, String name,
			String nickName, String remarkName, String gender,
			String information) {
		super();
		this._id = _id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.remarkName = remarkName;
		this.gender = gender;
		this.information = information;
	}
	public String get_id() {
		return _id;
	}
	public String getGender() {
		return gender;
	}
	public String getInformation() {
		return information;
	}
	public String getName() {
		return name;
	}
	public String getNickName() {
		return nickName;
	}
	public String getPassword() {
		return password;
	}
	public String getRemarkName() {
		return remarkName;
	}
	public String getUsername() {
		return username;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "User [_id=" + _id + ", username=" + username + ", password="
				+ password + ", name=" + name + ", nickName=" + nickName
				+ ", remarkName=" + remarkName + ", gender=" + gender
				+ ", information=" + information + "]";
	}
	
}
