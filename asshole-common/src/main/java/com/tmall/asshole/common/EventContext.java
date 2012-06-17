package com.tmall.asshole.common;

import java.util.HashMap;
import java.util.Map;

/****
 * 
 * @author tangjinou
 *
 */
public class EventContext  implements IScheduleContext{
	
	private String process_logs;
	
	private String operator;
	
	private Map<String,Object> map = new HashMap<String,Object>();

	public String getProcessLogs() {
		return process_logs;
	}

	public void setProcessLogs(String process_logs) {
		this.process_logs = process_logs;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public void putData(String key,Object value){
		map.put(key, value);
	}
	
	public Object getData(String key){
		return map.get(key);
	}

	public Map<String, Object> getMap() {
		return map;
	}
	
	
	
	
	
	


}
