package com.tmall.asshole.engine.machine;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import junit.framework.Assert;
import junit.framework.TestCase;



public class ScriptEngineTest extends TestCase{
	
	public void test() {
		ScriptEngine engine;
		ScriptEngineManager factory;
		factory = new ScriptEngineManager();
		engine = factory.getEngineByName("javascript");
		
		
		Map<String,Object> context=new HashMap<String,Object>();
		
		context.put("r", Boolean.FALSE);
		for (Entry<String, Object> entry : context.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		try {
			Boolean result = (Boolean) engine.eval("r == false");
			//System.err.println(result);
			Assert.assertEquals(result, Boolean.TRUE);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
