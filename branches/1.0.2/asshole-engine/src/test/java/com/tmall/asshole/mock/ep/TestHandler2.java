package com.tmall.asshole.mock.ep;

import org.springframework.stereotype.Component;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;

@Component
public class TestHandler2 extends AbstractHandler<TestEvent2,EventContext> {

	public boolean handle(TestEvent2 event, EventContext context)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
