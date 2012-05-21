package com.tmall.asshole.event.common;

public enum EventStatus {
	
	EVENT_STATUS_FAILED(-1,"执行失败"),
	EVENT_STATUS_UNEXECUTED(0,"未执行"),
	EVENT_STATUS_SUCCESS(1,"执行成功"),
	EVENT_STATUS_DELETED(2,"执行被删除"),
	EVENT_STATUS_PARAMETER_ERROR(3,"参数异常"),
	EVENT_STATUS_LOADED(9,"执行中"),
	EVENT_STATUS_EXCEPTION(10,"执行异常");
  	

	private int code;
	
	private String name;
	
	private EventStatus(int code, String name){
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
}
