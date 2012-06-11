package com.tmall.asshole.schedule;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

public class EPProxyTest {
	
	@Test
	public void  testCreateProcessInstanceID() throws SecurityException, NoSuchMethodException{
		try{
	   
		EPProxy  proxy = new EPProxy();
			
		Method method = EPProxy.class.getDeclaredMethod("createProcessInstanceID");
		
		method.setAccessible(true);
		
		Assert.assertNotNull(method);
		
		Object o1 = method.invoke(proxy);
		
		Object o2 = method.invoke(proxy);
		
		Object o3 = method.invoke(proxy);
		
		System.out.println(o1);
		System.out.println(o2);
		System.out.println(o3);
		
		Assert.assertNotSame(o1, o2);
		Assert.assertNotSame(o2, o3);
		
		}catch (Exception e) {
			Assert.fail();
		}
		
	}

}
