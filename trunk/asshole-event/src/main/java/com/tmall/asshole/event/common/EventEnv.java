package com.tmall.asshole.event.common;
/****
 * @author tangjinou
 */
public enum EventEnv {
	
	DEV(0,"dev"),
	DAILY(1,"daily"),
	PRE(2,"dev"),
	ONLINE(3,"online"),
	LOCAL(5,"local");
	
	
	private int code;
	private String name;
	
	private EventEnv(int code, String name){
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
