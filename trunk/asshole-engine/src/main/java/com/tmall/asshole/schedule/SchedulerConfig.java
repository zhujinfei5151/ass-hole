package com.tmall.asshole.schedule;

/****
 * 
 * @author jiuxian.tjo
 *
 */
public class SchedulerConfig {
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
	
	
	
	
	
	
}
