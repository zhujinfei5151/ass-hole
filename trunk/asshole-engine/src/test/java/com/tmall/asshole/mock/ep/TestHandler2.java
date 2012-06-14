package com.tmall.asshole.mock.ep;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;

public class TestHandler2 extends AbstractHandler {

	@Override
	public boolean handle(Event event, EventContext context) throws Exception {
		return true;
	}

}
