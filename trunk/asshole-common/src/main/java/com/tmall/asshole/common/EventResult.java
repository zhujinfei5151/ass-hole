package com.tmall.asshole.common;
/**
 * @author jiuxian.tjo
 * 
 * 
 * 
 */
public class EventResult {
	/***
	 * 
	 * 判断是不是同步调用或者是异步调用
	 * 
	 * 如果是同步调用则有执行结果相关信息
	 * 
	 * 如果是异步调用则无执行结果相关信息
	 */
	private boolean synInvoke=false;
	
	
	/**
	 * 只有同步才可能会有errorMsg
	 */
	private String errorMsg;

	public boolean isSynInvoke() {
		return synInvoke;
	}

	public void setSynInvoke(boolean synInvoke) {
		this.synInvoke = synInvoke;
	}
	
	private boolean isSuccess;
	
	/**
	 * 对于同步调用，true表示执行成功
	 * 
	 * 对于异步调用, true并不一定表示执行成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
