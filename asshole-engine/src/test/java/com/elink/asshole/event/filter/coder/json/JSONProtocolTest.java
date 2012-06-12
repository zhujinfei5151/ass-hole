package com.elink.asshole.event.filter.coder.json;

import org.junit.Assert;
import org.junit.Test;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.event.filter.codec.json.JSONProtocol;
import com.tmall.asshole.mock.ep.TestEvent1;

public class JSONProtocolTest {
	
	
	@Test
	public void test(){
		JSONProtocol<Event> jsonProtocol = new JSONProtocol<Event>();
		TestEvent1 testEvent1 = new TestEvent1();
		testEvent1.setTestVar1("testVar1");
		
		TestEvent1 newTestEvent1 = new TestEvent1();
		try {
			
			TestEvent1 event = (TestEvent1)jsonProtocol.decode(jsonProtocol.encode(testEvent1), newTestEvent1);
			
			Assert.assertEquals(testEvent1.getTestVar1(), newTestEvent1.getTestVar1());
			
			Assert.assertEquals(testEvent1.getTestVar1(), event.getTestVar1());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}


}
