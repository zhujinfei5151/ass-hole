package com.tmall.asshole.engine.process;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessorProxyTest {
	/***
	 * 测试需要启动Spring容器
	 */
	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"config.xml");
	static ProcessorMachine proxy = (ProcessorMachine) context
			.getBean("processorProxy");

	@Test
	public void testBasic() {
		try {
			Assert.assertTrue(proxy.getEventSchedulerProcessors().size()>0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}
}
