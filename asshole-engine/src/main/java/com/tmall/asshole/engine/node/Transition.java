package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("transition")
public class Transition {

	@XStreamAsAttribute
	public String exp;
	
	@XStreamAsAttribute
	public String to;

	public Transition(String exp, String to) {
		super();
		this.exp = exp;
		this.to = to;
	}

	@Override
	public String toString() {
		return "Transition [exp=" + exp + ", to=" + to + "]";
	}
	
}
