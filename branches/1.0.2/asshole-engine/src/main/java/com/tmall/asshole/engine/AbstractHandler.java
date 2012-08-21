package com.tmall.asshole.engine;

import java.lang.reflect.ParameterizedType;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;


/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public abstract class AbstractHandler<F extends Event,C extends EventContext> implements IHandler<F,C>,IBeforeHandler<F,C>,IAfterHandler<F, C>{
    
    
	public String getHANDLER_MAP_KEY() {
		Class<F> entityClass = (Class<F>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass.getName();
	}
	
	public boolean beforeHandle(F f, C c) throws Exception {
		return true;
	}

	public boolean afterHandle(F f, C c) throws Exception {
		return true;
	}
	
    
}