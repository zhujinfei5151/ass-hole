package com.tmall.asshole.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.tmall.asshole.mock.ep.TestEvent1;


public class BeanCopyUtilTest {

	@Test
    public void testCopy(){
		 TestEvent1 testEvent1 = new TestEvent1(); 
		
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 map.put("testVar1", "hello world");
         
		 BeanCopyUtil.copy(testEvent1, map);
		 
         Assert.assertEquals("hello world", testEvent1.getTestVar1());		 
		
	}

}
