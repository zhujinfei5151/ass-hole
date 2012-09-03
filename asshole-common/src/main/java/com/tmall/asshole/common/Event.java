package com.tmall.asshole.common;

import java.util.Date;
import java.util.Map;

/****
 *
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class Event {


	private Long id;

	private EventStatus status = EventStatus.EVENT_STATUS_UNEXECUTED;

	private EventEnv env;
	
	/**
	 */
	
	private String processLogs; //
    
	/***
	 */
	private String memo;

	private String typeClass;//

	private String context;

	private String executeMachineIp;//

	private String executeMachineHashRange; //

	private Date gmtCreate; //

	private Date gmtModified;//

	private String operator;

	private int hashNum;//

	private int execCount;//
	
	private Long processInstanceId;//
	

	private Integer source;
	
	private String processName;
	
	private String currentName;
	
	/**
	 */
	private Date  execStartTime;//
	
	/***
	 */
	private boolean isDelayExec;//
	
    private int processorNumber;
    
    /***
     *  指定一个线程运行
     */
    //private boolean assignedThread;
    
    private boolean synInvoke;
    /**
     * 表示事件节点的类型
     */
    private String type;
    
    
    /***
     * 类似ThreadLocal级的context,不允许外部修改   
     */
	private String sessionContext;
	
    
	
	public String getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(String sessionContext) {
		this.sessionContext = sessionContext;
	}


	public String getInstanceContext() {
		return sessionContext;
	}

	public void setInstanceContext(String instanceContext) {
		this.sessionContext = instanceContext;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getProcessInstanceId() {
    	return processInstanceId;
    }
    
    public void setProcessInstanceId(Long processInstanceId) {
    	this.processInstanceId = processInstanceId;
    }
    
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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
	 */
	public void setStatus(Integer status) {
		
		this.status = EventStatus.getEventStatusByCode(status);
	}
	/**
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
	 */
	public void setEnv(Integer env) {
		this.env = EventEnv.getEventEnvByCode(env);
	}
	
	public void setEnv(String env) {
		this.env = EventEnv.getEventEnvByName(env);
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public void appendMemo(String newMemo){
		if(memo==null){
			memo="";
		}
		memo = memo + ";"+ newMemo;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	public String getProcessLogs() {
		return processLogs;
	}

	public void setProcessLogs(String processLogs) {
		this.processLogs = processLogs;
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	public String getExecuteMachineIp() {
		return executeMachineIp;
	}

	public void setExecuteMachineIp(String executeMachineIp) {
		this.executeMachineIp = executeMachineIp;
	}

	public String getExecuteMachineHashRange() {
		return executeMachineHashRange;
	}

	public void setExecuteMachineHashRange(String executeMachineHashRange) {
		this.executeMachineHashRange = executeMachineHashRange;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public int getHashNum() {
		return hashNum;
	}

	public void setHashNum(int hashNum) {
		this.hashNum = hashNum;
	}

	public int getExecCount() {
		return execCount;
	}

	public void setExecCount(int execCount) {
		this.execCount = execCount;
	}

	public Date getExecStartTime() {
		return execStartTime;
	}

	public void setExecStartTime(Date execStartTime) {
		this.execStartTime = execStartTime;
	}

	public boolean isDelayExec() {
		return isDelayExec;
	}

	public void setDelayExec(boolean isDelayExec) {
		this.isDelayExec = isDelayExec;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}


	public int getProcessorNumber() {
		return processorNumber;
	}

	public void setProcessorNumber(int processorNumber) {
		this.processorNumber = processorNumber;
	}

//	public boolean isAssignedThread() {
//		return assignedThread;
//	}
//
//	public void setAssignedThread(boolean assignedThread) {
//		this.assignedThread = assignedThread;
//	}

	public void setSynInvoke(Boolean syn) {
		this.synInvoke=syn;
	}

	public boolean getSynInvoke() {
		return synInvoke;
	}
	
	
	
	

}
