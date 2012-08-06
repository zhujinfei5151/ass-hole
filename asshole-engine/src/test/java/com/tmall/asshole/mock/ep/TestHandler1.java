package com.tmall.asshole.mock.ep;

import org.springframework.stereotype.Component;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;

@Component
public class TestHandler1  extends AbstractHandler<TestEvent1,EventContext> {

	public boolean handle(TestEvent1 event, EventContext context) throws Exception {
		context.putData("a", 121212);
		context.putData("b", 121212);
		return true;
	}

}
