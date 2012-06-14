package com.tmall.asshole.engine.process;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.mock.ep.TestEvent1;
import com.tmall.asshole.mock.ep.TestEvent2;
import com.tmall.asshole.schedule.node.ProcessTemplate;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;

public class ProcessorMachineTest {
	/***
	 * 测试需要启动Spring容器
	 */
	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"config.xml");
	static ProcessorMachine machine = (ProcessorMachine) context
			.getBean("processorMachine");

	@Test
	public void testBasic() {
		try {
			Assert.assertTrue(machine.getEventSchedulerProcessors().size()>0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCreateEventProcess(){
		try {
			Assert.assertTrue(ProcessTemplateHelper.processes.size()>0);
			
			ProcessTemplate processTemplate = ProcessTemplateHelper.getProcessTemplate("order_card");
			
			Assert.assertNotNull(processTemplate);
			
			Assert.assertEquals(processTemplate.nodes.get(0).getName(), "order_create");
			
			TestEvent1 event1 = new TestEvent1();
			
			event1.setTestVar1("testVar1");
			
			machine.createEventProcess(event1, "order_card");
			
			Thread.sleep(3000);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCallBack(){
		TestEvent1 event1 = new TestEvent1();
		event1.setTestVar1("testing");
		event1.setCurrentName("order_create");
		event1.setProcessName("order_card");
		
		EventContext e_context = new EventContext(); 
		try {
			machine.callback(event1, e_context);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	
}
