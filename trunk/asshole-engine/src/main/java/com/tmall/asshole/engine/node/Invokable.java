package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Invokable {

	@XStreamAlias("method")
	@XStreamAsAttribute
	public String method;
	
	@XStreamAlias("args")
	@XStreamAsAttribute
	public String args;
	
	@XStreamAlias("result")
	@XStreamAsAttribute
	public String callback;

	public Invokable(String method, String args, String callback) {
		super();
		this.method = method;
		this.args = args;
		this.callback = callback;
	}

	@Override
	public String toString() {
		return "Invokable [method=" + method + ", args=" + args + ", callback="
				+ callback + "]";
	}
	
}
