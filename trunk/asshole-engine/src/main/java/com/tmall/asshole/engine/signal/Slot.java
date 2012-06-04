package com.tmall.asshole.engine.signal;

public class Slot {

	public Object listener;
	public Boolean sync = true;
	public String method;
	public String args;
	public String result;

	public Slot(Object listener, Boolean sync, String method, String args,
			String result) {
		super();
		this.listener = listener;
		this.sync = sync;
		this.method = method;
		this.args = args;
		this.result = result;
	}
}
