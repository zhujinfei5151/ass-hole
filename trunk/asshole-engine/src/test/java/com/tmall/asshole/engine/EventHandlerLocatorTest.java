package com.tmall.asshole.engine;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.mock.ep.TestHandler1;

public class EventHandlerLocatorTest {
	/***
	 * 测试需要启动Spring容器
	 */
	@Test
	public void testEventHandlerLocator(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		
		EventHandlerLocator handlerLocator=(EventHandlerLocator)context.getBean("handlerLocator");
		
		Assert.assertNotNull(handlerLocator);
		
		Assert.assertNotNull(handlerLocator.getHANDLER_MAP().get("com.tmall.asshole.ep.TestEvent1"));
		
		Assert.assertTrue(handlerLocator.getHANDLER_MAP().get("com.tmall.asshole.ep.TestEvent1")  instanceof  TestHandler1);
    	
    }
}
