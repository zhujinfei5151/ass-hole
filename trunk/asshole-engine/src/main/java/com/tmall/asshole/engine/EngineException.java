package com.tmall.asshole.engine;
/***
 * @author tangjinou
 */
public class EngineException extends Exception {

	private String code;
	private String queryCriteria;

	public EngineException(String code, String queryCriteria) {
		this.code=code;
		this.queryCriteria=queryCriteria;
	}

	public String getCode() {
		return code;
	}

	public String getQueryCriteria() {
		return queryCriteria;
	}
	
	

}
