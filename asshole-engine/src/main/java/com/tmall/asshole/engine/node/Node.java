package com.tmall.asshole.engine.node;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Node {

	@XStreamAsAttribute
	public String name;
	
	@XStreamAlias("transitions")
	public List<Transition> transitions;
	
	
	@XStreamAlias("pre-nodes")
	public List<PreNode> preNodes;

	@Override
	public String toString() {
		return "Node [name=" + name + ",preNodes=" + preNodes +" + transitions=" + transitions + "]";
	}
	
}
