package com.elink.asshole.event.biz.dal.dao.testcase;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.unitils.spring.annotation.SpringBeanByName;

import com.taobao.common.dao.persistence.exception.DAOException;
import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventEnv;
import com.tmall.asshole.event.common.EventStatus;
import com.tmall.asshole.event.dao.EventDAO;

public class TestEventDAO extends TestBase {

	        		
	@SpringBeanByName
    private EventDAO eventDao ;

   
	@Ignore
    @Test
    public void insertTest() throws DAOException {
    	
    	Event eventDO = new Event();
        eventDO.setId(0000001L);
        eventDO.setEnv(EventEnv.PRE.getCode());
        eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED);
        
        eventDO.setMemo("testOnly");
        eventDO.setContent("only for test do not");
        eventDO.setOperator("chuzheng");
        eventDO.setIdentifier(0L);
        eventDO.setBiz_order_id(0L);
        eventDO.setType("");
        eventDO.setType_class("TestClass");
        eventDO.setExec_count(0);
        eventDO.setSource(0);
        eventDO.setEvent_time(new Date());
        eventDO.setWorkcard_id(0L);
        eventDO.setHash_num(0001);
        eventDO.setGmt_create(new Date());
        eventDO.setGmt_modify(new Date());
        
        long id = 0;
        try {
			id = eventDao.insertEventDO(eventDO);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
    }
	@Ignore
    @Test
    public void updateDOTest() throws DAOException {
    	
    	Event eventDO = new Event();
        eventDO.setId(0000001L);
        eventDO.setEnv(EventEnv.PRE.getCode());
        eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED);
        
        eventDO.setMemo("testOnly");
        eventDO.setContent("update");
        eventDO.setOperator("chuzheng");
        eventDO.setIdentifier(0L);
        eventDO.setBiz_order_id(0L);
        eventDO.setType("");
        eventDO.setType_class("TestClass");
        eventDO.setExec_count(0);
        eventDO.setSource(0);
        eventDO.setEvent_time(new Date());
        eventDO.setWorkcard_id(0L);
        eventDO.setHash_num(1123);
        eventDO.setGmt_create(new Date());
        eventDO.setGmt_modify(new Date());
        
        
        long id = 0;
        try {
        	id = eventDao.updateEvent(eventDO);
		//	id = eventDao.insertEventDO(eventDo);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
    }
	@Ignore
    @Test
    public void queryDotest() throws DAOException {
    
    	List<Event> lst = eventDao.queryEvent(0,10000,10000,"pre");
    	if(lst!=null)
    		Assert.assertTrue(true);
    }
	@Ignore
	@Test
	public void batchqueryDotest() throws DAOException {
	//测试已经通过 	
	//	 Integer count = eventDao.batchChangeEventStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode(),EventStatus.EVENT_STATUS_SUCCESS.getCode());
	}
	@Test
	public void batchqueryByTimetest() throws DAOException {
	//测试已经通过 	
	//	 Integer count = eventDao.batchChangeEventStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode(),EventStatus.EVENT_STATUS_SUCCESS.getCode());
	
		
		Integer count = eventDao.batchChangeEventStatusBytime(1 ,0 ,10);
	
	
	}
	
    
    
    
}
