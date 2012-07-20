package com.tmall.asshole.schedule.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
@XStreamAlias("end")
public class End{

	@XStreamAsAttribute
	public String name;

	public End(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "End [name=" + name + "]";
	}
	
}
