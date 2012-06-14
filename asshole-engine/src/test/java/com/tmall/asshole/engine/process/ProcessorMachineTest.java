package com.tmall.asshole.engine.process;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.mock.ep.TestEvent1;
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
			
			Assert.assertEquals(processTemplate.nodes.get(2).getName(), "order_end");
			
			TestEvent1 event1 = new TestEvent1();
			
			event1.setTestVar1("testVar1");
			
			machine.createEventProcess(event1, "order_card");
			
			Thread.sleep(10000);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	
}