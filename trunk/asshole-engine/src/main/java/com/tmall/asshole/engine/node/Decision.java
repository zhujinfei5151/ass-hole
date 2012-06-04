package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("decision")
public class Decision extends Node{

	public Decision(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Decision [name=" + name + ", transitions=" + transitions + "]";
	}
	
}
