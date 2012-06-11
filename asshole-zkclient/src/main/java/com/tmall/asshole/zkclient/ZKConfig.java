package com.tmall.asshole.zkclient;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/***
 * @author tangjinou
 */
public class ZKConfig {
	
	//Ĭ�ϲ���Ȩ��У�� ���ڲ���
	private Boolean usePermissions=false;
	private String username;
	private String password;
	private String zkConnectString;
	private int zkSessionTimeout;
	private String rootPath;
	private String localIPAddress;

	
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
	
	public String getLocalIPAddress(){
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
	
	/****
	 * Ĭ�ϸ����ʹ��
	 * 
	 * @param localIPAddress
	 */
	public void setLocalIPAddressForTest(String localIPAddress) {
		this.localIPAddress = localIPAddress;
	}
	public String getFullPath(){
		  return  this.rootPath+"/"+getLocalIPAddress();
	}
	
	public String getMyPath(){
		return  "/"+getLocalIPAddress();
	}
	
	
	

}
