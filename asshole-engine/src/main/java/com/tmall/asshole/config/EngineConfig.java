package com.tmall.asshole.config;

import java.util.List;

/****
 * 
 * @author jiuxian.tjo
 *
 */
public class EngineConfig {
    /***
     *  调度算法类型
     */
	private String algorithmType;
	
	/***
	 *  环境类型
	 */
	private String envionmentGroup;
	
	/***
	 *  taskname
	 */
	private String taskName;
	/**
	 *  groupingName
	 */
	private String groupingName;
	
	/***
	 *  调度轮询时间
	 */
	private int schedulingPollingTime;
	
	/***
	 *  最大hash值
	 */
	private int maxHashNum;
	
	/***
	 *  详细类型见 ScheduleType 枚举类
	 */
	private String scheduleType;
	
	
	/** *线程池维护线程的最少数量 */
	private int corePoolSize = 20;

	/** *线程池维护线程的最大数量 */
	private int maxPoolSize = 20;

	/** *线程池维护线程所允许的空闲时间 */
	private int keepAliveTime = 0;
	
	private boolean startZK=true;
	
	/**zookeeper client 相关配置**/
	private Boolean usePermissions=false;
	private String username;
	private String password;
	private String zkConnectString;
	private int zkSessionTimeout;
	private String rootPath;
	private String localIPAddress;
	
	
	/***
	 *   流程模版的路径
	 */
	private List<String> processTemplateFolders;
	

	public String getEnvionmentGroup() {
		return envionmentGroup;
	}

	public void setEnvionmentGroup(String envionmentGroup) {
		this.envionmentGroup = envionmentGroup;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getGroupingName() {
		return groupingName;
	}

	public void setGroupingName(String groupingName) {
		this.groupingName = groupingName;
	}

	public int getSchedulingPollingTime() {
		return schedulingPollingTime;
	}

	public void setSchedulingPollingTime(int schedulingPollingTime) {
		this.schedulingPollingTime = schedulingPollingTime;
	}

	public String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(String algorithmType) {
		this.algorithmType = algorithmType;
	}

	public int getMaxHashNum() {
		return maxHashNum;
	}

	public void setMaxHashNum(int maxHashNum) {
		this.maxHashNum = maxHashNum;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public List<String> getProcessTemplateFolders() {
		return processTemplateFolders;
	}

	public void setProcessTemplateFolders(List<String> processTemplateFolders) {
		this.processTemplateFolders = processTemplateFolders;
	}

	public boolean getStartZK() {
		return startZK;
	}

	public void setStartZK(boolean startZK) {
		this.startZK = startZK;
	}

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
	
	
	
	
	
}
