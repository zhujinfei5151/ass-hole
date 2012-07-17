package com.tmall.asshole.common;

public class ScheduleMonitorData {

	private long countOfUnExecuteEvent;

	public long getCountOfUnExecuteEvent() {
		return countOfUnExecuteEvent;
	}

	public void setCountOfUnExecuteEvent(long countOfUnExecuteEvent) {
		this.countOfUnExecuteEvent = countOfUnExecuteEvent;
	}

	public ScheduleMonitorData(long countOfUnExecuteEvent) {
		this.countOfUnExecuteEvent = countOfUnExecuteEvent;
	}
	
	public ScheduleMonitorData(){
		
	}
	
	
}

