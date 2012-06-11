package com.tmall.asshole.engine;

import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;


/***
 * @author jiuxian.tjo
 */
public abstract class AbstractHandler implements IHandler<Event, EventContext>,IBeforeHandler<Event, EventContext>,IAfterHandler<Event, EventContext>{
    
	private String HANDLER_MAP_KEY;
    
	public String getHANDLER_MAP_KEY() {
		return HANDLER_MAP_KEY;
	}

	public void setHANDLER_MAP_KEY(String hANDLER_MAP_KEY) {
		HANDLER_MAP_KEY = hANDLER_MAP_KEY;
	}
	
	public boolean beforeHandle(Event event,EventContext context) throws Exception{
		return true;
	}
	
	public boolean afterHandle(Event event,EventContext context) throws Exception{
		return true;
	}
	
    
}