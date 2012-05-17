package com.tmall.assole.zkclient;

public class ZKConfig {
	
	private String username;
	private String password;
	private String zkConnectString;
	private int zkSessionTimeout;
	private String rootPath;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getZkConnectString() {
		return zkConnectString;
	}
	public void setZkConnectString(String zkConnectString) {
		this.zkConnectString = zkConnectString;
	}
	
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public int getZkSessionTimeout() {
		return zkSessionTimeout;
	}
	public void setZkSessionTimeout(int zkSessionTimeout) {
		this.zkSessionTimeout = zkSessionTimeout;
	}
	
	

}
