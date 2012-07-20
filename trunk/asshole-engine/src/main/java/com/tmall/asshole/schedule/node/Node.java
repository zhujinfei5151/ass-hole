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
	
	@XStreamAsAttribute
	private String classname;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String processorNumber;
	
	@XStreamAsAttribute
	private String foreach;
	
	@XStreamAlias("transitions")
	public List<Transition> transitions;
	

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
	
	public boolean isForeach(){
		if(StringUtils.isBlank(foreach)){
			return false;
		}
		return foreach.trim().equals("true");
	}
	
	public void setForeach(String foreach) {
		this.foreach = foreach;
	}
	
	@Override
	public String toString() {
		return "Node [name=" + name + ","+"classname="+classname +", + transitions=" + transitions + "]";
	}


}
