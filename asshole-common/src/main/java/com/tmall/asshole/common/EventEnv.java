package com.tmall.asshole.common;

/****
 * @author tangjinou
 */
public enum EventEnv {

	dev(0, "dev"), 
	daily(1, "daily"), 
	pre(2, "pre"), 
	online(3, "online"), 
	local(5, "local");

	private int code;
	private String name;

	private EventEnv(int code, String name) {
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

	public static final EventEnv getEventEnvByCode(Integer code) {
		EventEnv t = null;
		for (EventEnv e : EventEnv.values()) {
			if (code.intValue() == e.getCode()) {
				t = e;
				break;
			}
		}
		return t;
	}
}
