package com.tmall.asshole.common;

import java.util.Date;

/****
 *
 * @author tangjinou
 *
 */
public class Event {


	private Long id;

	private EventStatus status;

	private EventEnv env;
	
	/**
	 * 鎵цlog
	 */
	private String process_logs;
    
	/***
	 * 澶囨敞鏆傛椂涓嶇敤
	 */
	private String memo;

	private String type_class;

	private String context;

	private String execute_machine_ip;

	private String execute_machine_hash_range;

	private Date gmt_create;

	private Date gmt_modified;

	private String operator;

	private int hash_num;

	private int exec_count;
	
	private Long process_instance_id;
	
	private Integer source;
	
	private String processName;
	
	private String currentName;
	
	/**
	 *  鎵ц寮�濮嬫椂闂� 涓昏缁欐椿鍔ㄤ娇鐢� 
	 */
	private Date  exec_start_time;
	
	/***
	 *  鏄惁寤舵湡鏀寔 瀵逛簬娲诲姩闇�瑕佸欢鏈�
	 */
	private boolean is_delay_exec;
	
	/***
	 * 璋冨害鍣ㄧ殑绫诲瀷
	 */
	private int schedule_type;
	
	public Date getExecStartTime() {
		return exec_start_time;
	}

	public void setExec_start_time(Date exec_start_time) {
		this.exec_start_time = exec_start_time;
	}

	public boolean isIsDelayExec() {
		return is_delay_exec;
	}

	public void setIs_delay_exec(boolean is_delay_exec) {
		this.is_delay_exec = is_delay_exec;
	}

	public void setExec_count(int exec_count) {
		this.exec_count = exec_count;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getProcess_logs() {
		return process_logs;
	}

	public void setProcess_logs(String process_logs) {
		this.process_logs = process_logs;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String current_name) {
		this.currentName = current_name;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String process_name) {
		this.processName = process_name;
	}

	public Long getProcessInstanceId() {
		return process_instance_id;
	}

	public void setProcessInstanceId(Long process_instance_id) {
		this.process_instance_id = process_instance_id;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status.getCode();
	}

//	public EventStatus getEventStatus() {
//		return status;
//	}
	/**
	 * 閲嶈浇 鐢ㄤ簬ibatis鏁版嵁搴撴洿鏂�
	 * @param status
	 */
	public void setStatus(Integer status) {
		
		this.status = EventStatus.getEventStatusByCode(status);
	}
	/**
	 * 閲嶈浇
	 * @param status
	 */
	public void setStatus(EventStatus status)	{
		this.status = status;
		
	}

	public Integer getEnv() {
		return env.getCode();
	}

	public void setEnv(EventEnv env) {
		this.env = env;
	}
	
	/**
	 * 閲嶈浇 鐢ㄤ簬ibatis鏁版嵁搴撴洿鏂�
	 * @param env
	 */
	public void setEnv(Integer env) {
		this.env = EventEnv.getEventEnvByCode(env);
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getExecute_machine_ip() {
		return execute_machine_ip;
	}

	public void setExecute_machine_ip(String execute_machine_ip) {
		this.execute_machine_ip = execute_machine_ip;
	}

	public String getExecute_machine_hash_range() {
		return execute_machine_hash_range;
	}

	public void setExecute_machine_hash_range(String execute_machine_hash_range) {
		this.execute_machine_hash_range = execute_machine_hash_range;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

	public Date getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modify(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	public int getHash_num() {
		return hash_num;
	}

	public void setHash_num(int hash_num) {
		this.hash_num = hash_num;
	}

	
	public String getType_class() {
		return type_class;
	}

	public void setType_class(String type_class) {
		this.type_class = type_class;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public int getExec_count() {
		return exec_count;
	}

	public int getSchedule_type() {
		return schedule_type;
	}

	public void setSchedule_type(int schedule_type) {
		this.schedule_type = schedule_type;
	}
	
	
	
	

}
