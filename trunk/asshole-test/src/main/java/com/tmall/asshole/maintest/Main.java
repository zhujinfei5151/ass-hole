package com.tmall.asshole.maintest;

import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.engine.process.EventSchedulerProcessor;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			
			 EventSchedulerProcessor processor = (EventSchedulerProcessor) context.getBean("eventSchedulerProcessor");
			
			 Thread.sleep(1000000);
		} catch (InterruptedException e) {
			Assert.fail();
		}

	}

}
