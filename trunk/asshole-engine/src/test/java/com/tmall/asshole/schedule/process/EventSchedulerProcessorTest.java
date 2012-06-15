package com.tmall.asshole.schedule.process;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.common.EventEnv;
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
	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"config.xml");
	static EventSchedulerProcessor processor = (EventSchedulerProcessor) context
			.getBean("eventSchedulerProcessor");

	@Test
	public void testEventSchedulerProcessor() {

		try {
			processor.process(new TestEvent1(),new EventContext());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void testGetDataList() {
		try {
			TestEvent1 testEvent1 = new TestEvent1();
			testEvent1.setTestVar1("hello");
			processor.addData(testEvent1);
			List<Event> dataList = processor.getDataList(0, 3333, 10,
					EventEnv.local, "192.168.1.1");
			Assert.assertTrue(dataList.size() > 0);
			TestEvent1 newTestEvent1 = (TestEvent1) dataList.get(0);
			Assert.assertEquals(testEvent1.getTestVar1(),
					newTestEvent1.getTestVar1());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testStart() {
		try {
			processor.start();
			Thread.sleep(3000);
			processor.stopSchedule();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
