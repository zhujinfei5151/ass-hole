package com.tmall.asshole.maintest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.common.IScheduleMonitorWatcher;
import com.tmall.asshole.common.ScheduleMonitorData;
import com.tmall.asshole.engine.process.ProcessorMachine;
import com.tmall.asshole.mock.ep.TestEvent1;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			 ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			
			 ProcessorMachine   machine= (ProcessorMachine)context.getBean("processorMachine");
			 
			 
			 machine.getScheduleMonitor().addWatcher(new IScheduleMonitorWatcher() {
				public void onChange(ScheduleMonitorData data) {
					System.err.println("countOfUnExecuteEvent"+data.getCountOfUnExecuteEvent());
					
				}
			});
			 
			 TestEvent1 testEvent1 = new TestEvent1();
			 
			 testEvent1.setTestVar1("testVar1");
			 
			 machine.createEventProcess(testEvent1, "order_card");
//			 
//			 machine.createEventProcess(testEvent1, "brandSale");
			 
			 
			 Thread.sleep(1000000);
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail();
		}

	}

}
