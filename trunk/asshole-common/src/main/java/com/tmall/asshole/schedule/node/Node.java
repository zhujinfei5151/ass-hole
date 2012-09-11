package com.tmall.asshole.schedule.node;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
@XStreamAlias("node")
public class Node {
	
	public static String NODE_AUTO_TYPE="auto";
	
	public static String NODE_MANUAL_TYPE="manual";
	
	
	@XStreamAsAttribute
	private String classname;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String processorNumber;
	
	@XStreamAsAttribute
	private String foreach;
	
	@XStreamAsAttribute
	private String hashNum;
	
	@XStreamAsAttribute
	private Boolean syn;
	
	@XStreamAlias("transitions")
	public List<Transition> transitions;
	
	@XStreamAlias("type")
	private String type;
	
	@XStreamAsAttribute
	private String retry;
	

	public String getRetry() {
		if(StringUtils.isBlank(retry)){
			return "0";
		}
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String className) {
		this.classname = className;
	}
	
	public String getProcessorNumber() {
		//Ä¬ÈÏÎª0
		if(StringUtils.isBlank(processorNumber)){
			return "0";
		}
		return processorNumber;
	}

	public void setProcessorNumber(String processorNumber) {
		this.processorNumber = processorNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getForeach() {
		return foreach;
	}
	
	public String getType() {
		if(!StringUtils.isBlank(type)){
			return type;
		}
		return NODE_AUTO_TYPE;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isForeach(){
		if(StringUtils.isBlank(foreach)){
			return false;
		}
		return foreach.trim().equals("true");
	}
	
	public void setForeach(String foreach) {
		this.foreach = foreach;
	}
	
	public Boolean getSyn() {
		return syn==null?false:syn;
	}

	public void setSyn(Boolean syn) {
		this.syn = syn;
	}

	public String getHashNum() {
		return hashNum;
	}

	public void setHashNum(String hashNum) {
		this.hashNum = hashNum;
	}

	
	
	
	@Override
	public String toString() {
		return "Node [name=" + name + ","+"classname="+classname +", + transitions=" + transitions + "]";
	}


}
