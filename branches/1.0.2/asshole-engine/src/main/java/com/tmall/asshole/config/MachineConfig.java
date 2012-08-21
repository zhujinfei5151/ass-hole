package com.tmall.asshole.config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class MachineConfig {
	/**zookeeper client **/
	private Boolean usePermissions=false;
	private String username;
	private String password;
	private String zkConnectString;
	private int zkSessionTimeout;
	private String rootPath;
	private String localIPAddress;
	private String env="local";
	private boolean startZK=true;
	
	/***
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
		  if(localIPAddress==null){
	    	   try {
	               Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
	               while (e1.hasMoreElements()) {
	                      NetworkInterface ni = (NetworkInterface) e1.nextElement();
	                      Enumeration<?> e2 = ni.getInetAddresses();
	                      while (e2.hasMoreElements()) {
	                             InetAddress ia = (InetAddress) e2.nextElement();
	                             // ip = ia.getHostAddress ();
	                             if (!ia.isLoopbackAddress()) {
	                                    if (ia instanceof Inet4Address) {
	                                    	  localIPAddress = ia.getHostAddress();
	                                    }
	                             }
	                      }
	               }

	        } catch (SocketException e) {
	        }
	    }
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
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
	
	

}
