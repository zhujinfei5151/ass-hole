package com.tmall.asshole.ep;

import com.tmall.asshole.engine.AbstractHandler;
import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;

public class TestHandler1 extends AbstractHandler {

	@Override
	public boolean handle(Event event, EventContext context) throws Exception {
		return true;
	}

}
