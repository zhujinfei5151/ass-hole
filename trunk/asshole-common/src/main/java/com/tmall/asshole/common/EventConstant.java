package com.tmall.asshole.common;


import java.util.HashMap;
import java.util.Map;
/***
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class EventConstant {

	public static final int EVENT_STATUS_FAILED = -1;
	public static final int EVENT_STATUS_EXCEPTION = 10;
	public static final int EVENT_STATUS_UNEXECUTED = 0;
	public static final int EVENT_STATUS_SUCCESS = 1;
	public static final int EVENT_STATUS_DELETED = 2;
	public static final int EVENT_STATUS_PARAMETER_ERROR = 3;
	public static final int EVENT_STATUS_LOADED= 9;

	public static final Map<String, Integer> ENV = new HashMap<String, Integer>();
	static {
		ENV.put("dev", 0);
		ENV.put("daily", 1);
		ENV.put("pre", 2);
		ENV.put("online", 3);
		ENV.put("local", 5);
	}
}

