package com.tmall.asshole.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ApplicationObjectSupport;

import com.tmall.asshole.engine.AbstractHandler;
import com.tmall.asshole.engine.EventHandlerLocator;
import com.tmall.asshole.engine.IHandler;
import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;

public class SpringBeanUtils extends ApplicationObjectSupport implements ApplicationListener{
	private static SpringBeanUtils stools = null;

	public synchronized static Object getBean(String beanName) throws Exception{
		return SpringBeanUtils.getInstance().getApplicationContext().getBean(beanName);
	}
	
	public synchronized static Object getBean(Class<?> type) throws Exception{
		if(SpringBeanUtils.getInstance().getApplicationContext()==null){
			throw new NullPointerException("can't find the spring context,did you start spring container or config the spring container");
		}
		String[] names = SpringBeanUtils.getInstance().getApplicationContext().getBeanNamesForType(type);
		if(names==null || names.length<1){
			//throw new NullPointerException("can't find "+type.getName()+" in spring container");
//			 RootBeanDefinition beanDefinition = new RootBeanDefinition();
//			 beanDefinition.setAttribute(type.getSimpleName().toLowerCase(), type.newInstance());
//			 beanFactory.registerBeanDefinition(type.getSimpleName().toLowerCase(), beanDefinition);
			 return SpringBeanUtils.getInstance().getApplicationContext().getBean(type.getSimpleName().toLowerCase());
		}
		if(names.length>1){
			throw new Exception("there are too many "+ type.getName()+" in spring container");
		}
		
		return SpringBeanUtils.getInstance().getApplicationContext().getBean(names[0]);
	}
	

	public synchronized static SpringBeanUtils getInstance() {
		if (stools == null) {
			stools = new SpringBeanUtils();
		}
		return stools;
	}
	
	protected void initApplicationContext() throws BeansException {
		SpringBeanUtils.getInstance().setApplicationContext(getApplicationContext());
	}
    
	private boolean handlerMapLoaded;

	public void onApplicationEvent(ApplicationEvent event) {
		 if(event instanceof ContextRefreshedEvent && handlerMapLoaded == false){
			 handlerMapLoaded = true;
			 loadAllHandlerMap();
		 }
	}
	
	
	private void loadAllHandlerMap() throws BeansException{
		String[] names = getApplicationContext().getBeanNamesForType(EventHandlerLocator.class);
		EventHandlerLocator locator = null;
		if(names.length == 1){
			locator = (EventHandlerLocator)getApplicationContext().getBean(names[0]);
			locator.getHANDLER_MAP().putAll(SpringBeanUtils.getInstance().getHandlerMap());
		}
	}
	
    public synchronized static Map<String, IHandler<Event, EventContext>>  getHandlerMap() {
    	Map<String, IHandler<Event, EventContext>> map = new HashMap<String, IHandler<Event,EventContext>>();
		String[] names = SpringBeanUtils.getInstance().getApplicationContext().getBeanNamesForType(AbstractHandler.class);
    	for (String name : names) {
    		AbstractHandler handler = (AbstractHandler)SpringBeanUtils.getInstance().getApplicationContext().getBean(name);
    		map.put(handler.getHANDLER_MAP_KEY(), handler);
		}
		return map;
	}
}