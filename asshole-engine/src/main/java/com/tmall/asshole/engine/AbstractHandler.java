package com.tmall.asshole.engine;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;


/***
 * @author jiuxian.tjo
 */
public abstract class AbstractHandler<F extends Event,C extends EventContext> implements IHandler<F,C>,IBeforeHandler<F,C>,IAfterHandler<F, C>{
    
	private String HANDLER_MAP_KEY;
    
	public String getHANDLER_MAP_KEY() {
		return HANDLER_MAP_KEY;
	}

	public void setHANDLER_MAP_KEY(String hANDLER_MAP_KEY) {
		HANDLER_MAP_KEY = hANDLER_MAP_KEY;
	}
	
	public boolean beforeHandle(F f, C c) throws Exception {
		return true;
	}

	public boolean afterHandle(F f, C c) throws Exception {
		return true;
	}
	
    
}