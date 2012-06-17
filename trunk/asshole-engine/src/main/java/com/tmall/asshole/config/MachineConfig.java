package com.tmall.asshole.config;

import java.util.List;

public class MachineConfig {
	/**zookeeper client 相关配置**/
	private Boolean usePermissions=false;
	private String username;
	private String password;
	private String zkConnectString;
	private int zkSessionTimeout;
	private String rootPath;
	private String localIPAddress;
	private boolean startZK=true;
	
	/***
	 *   流程模版的路径
	 */
	private List<String> processTemplateFolders;
	
	
	public Boolean getUsePermissions() {
		return usePermissions;
	}
	public void setUsePermissions(Boolean usePermissions) {
		this.usePermissions = usePermissions;
	}
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
	public int getZkSessionTimeout() {
		return zkSessionTimeout;
	}
	public void setZkSessionTimeout(int zkSessionTimeout) {
		this.zkSessionTimeout = zkSessionTimeout;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getLocalIPAddress() {
		return localIPAddress;
	}
	public void setLocalIPAddress(String localIPAddress) {
		this.localIPAddress = localIPAddress;
	}
	public boolean getStartZK() {
		return startZK;
	}
	public void setStartZK(boolean startZK) {
		this.startZK = startZK;
	}
	public List<String> getProcessTemplateFolders() {
		return processTemplateFolders;
	}
	public void setProcessTemplateFolders(List<String> processTemplateFolders) {
		this.processTemplateFolders = processTemplateFolders;
	}
	
	
	

}
