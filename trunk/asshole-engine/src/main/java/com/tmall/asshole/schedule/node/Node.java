package com.tmall.asshole.schedule.node;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author tangjinou
 */

@XStreamAlias("node")
public class Node {
	
	@XStreamAsAttribute
	private String classname;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String processorNumber;
	
	@XStreamAlias("transitions")
	public List<Transition> transitions;
	

	public String getClassname() {
		return classname;
	}

	public void setClassname(String className) {
		this.classname = className;
	}
	
	public String getProcessorNumber() {
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
	
	@Override
	public String toString() {
		return "Node [name=" + name + ","+"classname="+classname +", + transitions=" + transitions + "]";
	}
	
	
	

}
