package com.tmall.asshole.schedule.monitor;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.IEventDAO;
import com.tmall.asshole.util.Initialize;
/***
 * º‡øÿ π”√
 * 
 * @author jiuxian.tjo
 *
 */
public class ScheduleMonitor implements Initialize{
	
	@Autowired
	private IEventDAO eventDAO;
    	

	@Override
	public void init() throws Exception {
		
		long count=eventDAO.queryCountOfUnExecuteEvent();
		
		
		
		
	}

}
