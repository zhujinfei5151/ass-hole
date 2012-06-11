package com.tmall.asshole.schedule;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

public class EPProxyTest {
	
	@Test
	public void  testCreateProcessInstanceID() throws SecurityException, NoSuchMethodException{
		try{
		
		EPProxy  proxy = new EPProxy();
		
		Method method = EPProxy.class.getMethod("createProcessInstanceID", null);
		
		Assert.assertNotNull(method);
		
//		System.out.println(proxy);
//		
//		Assert.assertEquals(expected, actual);
		
		}catch (Exception e) {
			Assert.fail();
		}
		
	}

}
