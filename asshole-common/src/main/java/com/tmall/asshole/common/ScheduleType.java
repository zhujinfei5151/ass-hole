package com.tmall.asshole.common;

/****
 * @author tangjinou
 */
public enum ScheduleType {
     //正常调度器
	BASIC_SCHEDULE(0, "BASIC_SCHEDULE"), 
	//延期调度器
	DELAY_SCHEDULE(1, "DELAY_SCHEDULE");

	private int code;
	private String name;

	private ScheduleType(int code, String name) {
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

	public static final ScheduleType getEventEnvByCode(Integer code) {
		ScheduleType t = null;
		for (ScheduleType e : ScheduleType.values()) {
			if (code.intValue() == e.getCode()) {
				t = e;
				break;
			}
		}
		return t;
	}
}
