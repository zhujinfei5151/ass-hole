package com.tmall.asshole.config;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ProcessorConfig {
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
	
	private int processorNumber;
	
	/** *线程池维护线程的最少数量 */
	private int corePoolSize = 20;

	/** *线程池维护线程的最大数量 */
	private int maxPoolSize = 20;

	/** *线程池维护线程所允许的空闲时间 */
	private int keepAliveTime = 0;
	
	
	
	

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

	public int getProcessorNumber() {
		return processorNumber;
	}

	public void setProcessorNumber(int processorNumber) {
		this.processorNumber = processorNumber;
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
	
}
