package com.tmall.asshole.event.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * 
 * @author tangjinou
 *
 * @param <Event>
 * @param <EventContext>
 */
public class EventEngine<Event,EventContext> implements IEngine<Event,EventContext> {
	private static transient Log logger = LogFactory.getLog(EventEngine.class);
	
	@Autowired
	private IHandlerLocator<Event, EventContext> handlerLocator;
	
	public void setHandlerLocator(IHandlerLocator<Event, EventContext> handlerLocator) {
		this.handlerLocator = handlerLocator;
	}

	public boolean fire(Event event, EventContext context) throws Exception {
		boolean flg = false;
		if (event != null) {
			String eventName = event.getClass().getName();
			IHandler handler = handlerLocator.lookup(eventName);
			try {
				flg = handler.handle(event, context);
			} catch (Exception e) {
				logger.debug("Event process error name:" + eventName + " content:" + event.toString(), e);
				throw e;
			}
		}
		return flg;
	}

}
