package com.elink.asshole.event.biz.dal.dao.testcase;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByName;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventEnv;
import com.tmall.asshole.common.EventStatus;
import com.tmall.asshole.common.IEventDAO;


public class TestEventDAO extends TestBase {

	@SpringBeanByName
    private IEventDAO EventDAO ;
	@Ignore
	@Test
    public void insertTest() throws Exception {
    	//pass
    	Event eventDO = new Event();
        eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode());        
        eventDO.setEnv(EventEnv.LOCAL.getCode());        
        eventDO.setOperator("chuzheng");     
        eventDO.setType_class("com.elink.asshole.event.biz.dal.dao.testcase.TestEventDAO");
        eventDO.setProcess_logs("good moring");
        eventDO.setMemo("testOnly");  
        eventDO.setContext("context");
        eventDO.setExecute_machine_ip("192.168.1.1");
        eventDO.setExecute_machine_hash_range("1000");
        eventDO.setHash_num(1000);
        eventDO.setExec_count(1);
        eventDO.setSource(0);  
        eventDO.setProcess_name("ibatis");
        eventDO.setProcess_instance_id(1L);
        eventDO.setCurrent_name("ib");
        eventDO.setExec_start_time(new Date());
        eventDO.setIs_delay_exec(true);
        eventDO.setSchedule_type(0);
        
       long id = 0;
        try {
			id = EventDAO.insertEventDO(eventDO);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	@Ignore
    @Test
    public void updateDOTest() throws Exception {
    	//pass
		
		Event eventDO = new Event();
		eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode());
		//eventDO.setEnv(EventEnv.LOCAL.getCode()); 
		eventDO.setId(1L);
        eventDO.setIs_delay_exec(false);
       
        long id = 0;
        try {
        	id = EventDAO.updateEventDO(eventDO);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@Ignore
	@Test
	public void batchqueryDotest() throws Exception {
	//pass
		 EventDAO.batchChangeEventStatus(0,4);
		 Assert.assertTrue(true);
	}
	
	
	@Ignore
	@Test
	public void queryDotest() throws Exception {
	//	
		int scheduleType = 0;
		//public List<Event> queryEvent(int start, int end, int count, int env, int scheduleType);
		List<Event> lst = EventDAO.queryEvent(0,10000,10000,0,scheduleType);
		if(lst!=null)
			Assert.assertTrue(true);
	}
	
	
	@Ignore
	@Test
	public void batchqueryByPrimeKey() throws Exception {
		
		Long id = 1L;
	
		Event e = EventDAO.queryEventByPrimaryKey(id,0);
		if(e!=null)
			Assert.assertTrue(true);
	}
	
    
    
    
}
