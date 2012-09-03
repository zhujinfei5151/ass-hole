package com.tmall.asshole.mock.ep;

import org.springframework.stereotype.Component;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;
import com.tmall.asshole.mock.ep.TestEvent1;


@Component
public class TestHandler1  extends AbstractHandler<TestEvent1,EventContext> {

	public boolean handle(TestEvent1 event, EventContext context) throws Exception {
		context.getSession().put("session", "helloworld");
		return true;
	}

}
