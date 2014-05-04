package com.example.users.vo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 9119915147700572658L;

	private String id;
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

	public User(String id) {
		super();
		this.id = id;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	public User(String id, String username, String password, String name,
			String nickName, String remarkName, String gender,
			String information) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.remarkName = remarkName;
		this.gender = gender;
		this.information = information;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getGender() {
		return gender;
	}

	public String getId() {
		return id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setId(String id) {
		this.id = id;
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
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", name=" + name + ", nickName=" + nickName
				+ ", remarkName=" + remarkName + ", gender=" + gender
				+ ", information=" + information + "]";
	}
}
