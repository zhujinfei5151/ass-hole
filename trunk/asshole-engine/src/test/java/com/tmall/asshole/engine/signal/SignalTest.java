package com.tmall.asshole.engine.signal;

import java.util.HashMap;
import java.util.Map;

import com.tmall.asshole.engine.model.mock.MockClazz;


import junit.framework.Assert;
import junit.framework.TestCase;


public class SignalTest extends TestCase{

	MockClazz mock ;
	
	Map<String, Object> context;
	
	public void setUp(){
		mock = new MockClazz();
		context = new HashMap<String, Object>();
		context.put("a", "thx");
		context.put("b", "again");
	}
	
	public void testSignal(){
		Signal s = new Signal();
		s.add(mock, "shot", "${a}, ${b}", "${r}");
		s.dispatch(context);
		Assert.assertEquals("shot", context.get("r"));
	}
}
