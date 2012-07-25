package com.tmall.asshole.mock.ep;

import org.springframework.stereotype.Component;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;

@Component
public class TestHandler3  extends AbstractHandler<TestEvent3,EventContext>{

	public boolean handle(TestEvent3 event, EventContext context)
			throws Exception {
        
		System.out.print("execute TestHandler3, testVar3="+event.getTestVar3());
		
		return true;
	}

}
