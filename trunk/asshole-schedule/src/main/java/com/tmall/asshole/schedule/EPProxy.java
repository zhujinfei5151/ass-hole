package com.tmall.asshole.schedule;

import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;
import com.tmall.asshole.event.engine.IAfterHandler;

public class EPProxy implements IAfterHandler<Event,EventContext>{

	@Override
	public boolean afterHandle(Event event, EventContext context) throws Exception {
		
		
		return false;
	}

}
