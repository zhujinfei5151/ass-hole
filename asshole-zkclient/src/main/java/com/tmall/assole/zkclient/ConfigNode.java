package com.tmall.assole.zkclient;

/**
 * @author jiuxian.tjo
 * @since 2012-5-17
 * @version 1.0
 */
public class ConfigNode {

	private String rootPath;

	private String configType;

	private String name;

	private String value;

	private String ip="";
	
	public ConfigNode() {

	}

	public ConfigNode(String rootPath, String configType, String name) {
		this.rootPath = rootPath;
		this.configType = configType;
		this.name = name;
	}
	
	public ConfigNode(String rootPath, String configType, String ip,String name) {
		this.rootPath = rootPath;
		this.configType = configType;
		this.name = name;
		this.ip = ip;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("rootPath").append(rootPath).append("\n");
		buffer.append("configType").append(configType).append("\n");
		buffer.append("name").append(name).append("\n");
		buffer.append("value").append(value).append("\n");
		buffer.append("ip").append(ip).append("\n");
		return buffer.toString();
	}
}
