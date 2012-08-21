package com.tmall.asshole.common;

import java.util.List;

/**
 * 
 * 监控的数据结构
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ScheduleMonitorData {

	private long countOfUnExecuteEvent;
	
	private String env;
	
	private List<String> machines;
	

	public long getCountOfUnExecuteEvent() {
		return countOfUnExecuteEvent;
	}

	public void setCountOfUnExecuteEvent(long countOfUnExecuteEvent) {
		this.countOfUnExecuteEvent = countOfUnExecuteEvent;
	}

	public ScheduleMonitorData(long countOfUnExecuteEvent) {
		this.countOfUnExecuteEvent = countOfUnExecuteEvent;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public List<String> getMachines() {
		return machines;
	}

	public void setMachines(List<String> machines) {
		this.machines = machines;
	}
	
	
   
	
   
	
	
}

