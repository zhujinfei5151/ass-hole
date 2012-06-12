package com.tmall.asshole.schedule.process;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;
import com.tmall.asshole.engine.process.EventSchedulerProcessor;
import com.tmall.asshole.mock.ep.TestEvent1;

/***
 * 
 * @author tangjinou
 * 
 */
public class EventSchedulerProcessorTest {
	/***
	 * 测试需要启动Spring容器
	 */
	@Test
	public void testEventSchedulerProcessor() {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			EventSchedulerProcessor processor = (EventSchedulerProcessor) context
					.getBean("eventSchedulerProcessor");
			
	   try {
			processor.process(new TestEvent1());
		} catch (Exception e) {
			Assert.fail();
		}

	}

}
