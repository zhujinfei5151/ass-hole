package com.tmall.asshole.common;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
import com.tmall.asshole.event.annotation.Extends;
import com.tmall.asshole.event.annotation.Ignore;


@Extends(true)
public class TestEvent extends Event {

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
