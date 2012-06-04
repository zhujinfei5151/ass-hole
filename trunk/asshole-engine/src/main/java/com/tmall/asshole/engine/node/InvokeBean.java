package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("spring-bean")
public class InvokeBean extends Invokable{

	@XStreamAlias("beanId")
	@XStreamAsAttribute
	public String beanId;

	public InvokeBean(String beanId, String method, String args, String callback) {
		super(method, args, callback);
		this.beanId = beanId;
	}

	@Override
	public String toString() {
		return "InvokeBean [beanId=" + beanId + ", method=" + method
				+ ", args=" + args + ", callback=" + callback + "]";
	}
	
}
