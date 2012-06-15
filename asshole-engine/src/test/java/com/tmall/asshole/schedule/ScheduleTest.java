package com.tmall.asshole.schedule;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.config.EngineConfig;
import com.tmall.asshole.engine.process.EventSchedulerProcessor;
import com.tmall.asshole.mock.dao.MockEventDAO;
import com.tmall.asshole.schedule.fgetcplolicy.BasicScheduleFgetcPolicy;
import com.tmall.asshole.zkclient.ZKConfig;

public class ScheduleTest {
	
	EventSchedulerProcessor processor = new  EventSchedulerProcessor();
	
	EngineConfig config = new EngineConfig();
	
	
	
	@Before
	public void init(){
		config.setAlgorithmType("basic");
		config.setEnvionmentGroup("dev");
		config.setTaskName("test");
		config.setGroupingName("testGroup");
		config.setSchedulingPollingTime(600);
		config.setMaxHashNum(10000);
		config.setScheduleType("BASIC_SCHEDULE");
	}
	
	
	@Test
	public void testSchedule(){
		try{
		  processor.setScheduleType("BASIC_SCHEDULE");
		  processor.setEventDAOForTest(new MockEventDAO());
		  Schedule<Event,EventContext> schedule= new Schedule<Event,EventContext>(processor,processor,processor,config);
		  BasicScheduleFgetcPolicy scheduleFgetcPolicy = (BasicScheduleFgetcPolicy)schedule.getScheduleFgetcPolicy();
		  List<String> machines = new ArrayList<String>();
		  machines.add("192.168.1.23");
		  ZKConfig zKConfig =new ZKConfig();
		  zKConfig.setLocalIPAddressForTest("192.168.1.23");
		  scheduleFgetcPolicy.setZKConfig(zKConfig);
		  scheduleFgetcPolicy.onChange(machines);
		  schedule.strart();
		  Assert.assertTrue(scheduleFgetcPolicy.getEndIndex() > 0);
		  Thread.sleep(3000);
		}catch (Exception e) {
			Assert.fail();
		}
		
	}

}
