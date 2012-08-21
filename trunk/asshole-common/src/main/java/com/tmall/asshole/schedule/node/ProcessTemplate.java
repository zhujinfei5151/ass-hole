package com.tmall.asshole.schedule.node;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
@XStreamAlias("process")
public class ProcessTemplate {

	@XStreamAsAttribute
	public String name;
	
	public Start start;
	
	@XStreamImplicit
	public List<Node> nodes;
	
	@XStreamImplicit
	public List<End> ends;

	public ProcessTemplate(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Definition [name=" + name + ", start=" + start + ", nodes="
				+ nodes + ", ends=" + ends + "]";
	}
	
}
