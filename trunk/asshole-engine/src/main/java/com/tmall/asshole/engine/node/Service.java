package com.tmall.asshole.engine.node;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("service")
public class Service extends Node {
	
	@XStreamImplicit
	public List<Invokable> invokables;
	
	public Service(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [invokables=" + invokables + ", name=" + name
				+ ",preNodes=" + preNodes +", transitions=" + transitions + "]";
	}

}
