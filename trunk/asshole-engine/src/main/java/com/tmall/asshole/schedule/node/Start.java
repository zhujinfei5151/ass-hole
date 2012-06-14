package com.tmall.asshole.schedule.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("start")
public class Start{

	@XStreamAsAttribute
	public String name;
	
	public Transition transition;

	public Start(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Start [name=" + name + ", transition=" + transition + "]";
	}
	
}
