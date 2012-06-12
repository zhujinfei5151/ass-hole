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
import com.tmall.asshole.ep.TestEvent1;

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
			
			/**
			 *  需要构造eventDAO 为了测试
			 */
			processor.setEventDAOForTest(new IEventDAO() {
				
				@Override
				public Integer updateServiceEventDO(Event dao) {
					return 1;
				}
				
				@Override
				public Event queryEventByPrimaryKey(Long id, Integer hashNum) {
					// 暂时不需要
					return null;
				}
				
				@Override
				public Integer insertServiceEventDO(Event dao) {
					// 暂时不需要
					return null;
				}
				
				@Override
				public Integer batchChangeEventStatusBytime(int from, int to, int minute) {
					// 暂时不需要
					return null;
				}
				
				@Override
				public Integer batchChangeEventStatus(int from, int to) {
					// 暂时不需要
					return null;
				}

				@Override
				public List<Event> queryEvent(int start, int end, int count,
						int env, int scheduleType) {
					List<Event> l= new ArrayList<Event>();
					l.add(new TestEvent1());
					return l;
				}
			});
			
	   try {
			processor.process(new TestEvent1());
		} catch (Exception e) {
			Assert.fail();
		}

	}

}
