package com.tmall.asshole.engine;

import java.util.HashMap;
import java.util.Map;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class EventHandlerLocator implements IHandlerLocator<Event, EventContext> ,IHandlerRegister<String, Event, EventContext>{
	private Map<String, IHandler<Event, EventContext>> HANDLER_MAP = new HashMap<String, IHandler<Event, EventContext>>();

	public void registHandler(String eventName, IHandler<Event, EventContext> handler) {
		HANDLER_MAP.put(eventName, handler);
	}

	public void registHandler(Map<String, IHandler<Event, EventContext>> paramMap) {
		HANDLER_MAP.putAll(paramMap);
	}

	@Override
	public IHandler<Event, EventContext> lookup(String queryCriteria) throws EngineException {
		IHandler<Event, EventContext> handler = null;
		if (queryCriteria == null || "".equals(queryCriteria.trim())) {
			throw new EngineException("queryCriteria can't be null");
		}
		String queryStr = queryCriteria.trim();
		if (HANDLER_MAP.containsKey(queryStr)) {
			handler = HANDLER_MAP.get(queryStr);
		}
		if (handler == null) {
			throw new EngineException("can't find the handler for "+queryCriteria);
		}
		return handler;
	}

	public void setHANDLER_MAP(Map<String, IHandler<Event, EventContext>> hANDLER_MAP) {
		HANDLER_MAP = hANDLER_MAP;
	}

	/**
	 * @return the hANDLER_MAP
	 */
	public Map<String, IHandler<Event, EventContext>> getHANDLER_MAP() {
		return HANDLER_MAP;
	}
}
