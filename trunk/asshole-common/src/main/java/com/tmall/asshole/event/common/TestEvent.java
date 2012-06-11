package com.tmall.asshole.event.common;

import com.tmall.asshole.event.annotation.Extends;
import com.tmall.asshole.event.annotation.Ignore;


@Extends(true)
public class TestEvent extends Event {

	public TestEvent(Event e) {
		super(e);
	}

	public TestEvent() {

	}

	@Ignore
	private String baomingItemID;

	public String getBaomingItemID() {
		return baomingItemID;
	}

	public void setBaomingItemID(String baomingItemID) {
		this.baomingItemID = baomingItemID;
	}

}
