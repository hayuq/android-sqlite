package com.xjc.androiddbstorage;

/**
 * 用户对象类
 */
public class User
{

	private String userName;
	private String userAddress;

	public User(){
		
	}
	
	public User(String userName, String userAddress)
	{
		this.userName = userName;
		this.userAddress = userAddress;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public String getUserAddress()
	{
		return userAddress;
	}

	
}
