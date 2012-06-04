package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("pojo-object")
public class InvokeClass extends Invokable{

	@XStreamAsAttribute
	public String classPath;

	public InvokeClass(String classPath, String method, String args, String callback) {
		super(method, args, callback);
		this.classPath = classPath;
	}

	@Override
	public String toString() {
		return "InvokeClass [classPath=" + classPath + ", method=" + method
				+ ", args=" + args + ", callback=" + callback + "]";
	}
	
}
