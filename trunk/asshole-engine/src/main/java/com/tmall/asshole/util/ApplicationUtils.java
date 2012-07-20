package com.tmall.asshole.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ApplicationObjectSupport;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;
import com.tmall.asshole.engine.EventHandlerLocator;
import com.tmall.asshole.engine.IHandler;
import com.tmall.asshole.engine.process.EventSchedulerProcessor;
import com.tmall.asshole.engine.process.ProcessorMachine;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ApplicationUtils extends ApplicationObjectSupport implements ApplicationListener{
	private static ApplicationUtils stools = null;

	public synchronized static Object getBean(String beanName) throws Exception{
		return ApplicationUtils.getInstance().getApplicationContext().getBean(beanName);
	}
	
	public synchronized static Object getBean(Class<?> type) throws Exception{
		if(ApplicationUtils.getInstance().getApplicationContext()==null){
			throw new NullPointerException("can't find the spring context,did you start spring container or config the spring container");
		}
		String[] names = ApplicationUtils.getInstance().getApplicationContext().getBeanNamesForType(type);
		if(names==null || names.length<1){
			//throw new NullPointerException("can't find "+type.getName()+" in spring container");
//			 RootBeanDefinition beanDefinition = new RootBeanDefinition();
//			 beanDefinition.setAttribute(type.getSimpleName().toLowerCase(), type.newInstance());
//			 beanFactory.registerBeanDefinition(type.getSimpleName().toLowerCase(), beanDefinition);
			 return ApplicationUtils.getInstance().getApplicationContext().getBean(type.getSimpleName().toLowerCase());
		}
		if(names.length>1){
			throw new Exception("there are too many "+ type.getName()+" in spring container");
		}
		
		return ApplicationUtils.getInstance().getApplicationContext().getBean(names[0]);
	}
	

	public synchronized static ApplicationUtils getInstance() {
		if (stools == null) {
			stools = new ApplicationUtils();
		}
		return stools;
	}
	
	protected void initApplicationContext() throws BeansException {
		ApplicationUtils.getInstance().setApplicationContext(getApplicationContext());
	}
    
	private boolean loaded;

	public void onApplicationEvent(ApplicationEvent event) {
		 if(event instanceof ContextRefreshedEvent && loaded == false){
			 loaded = true;
			 try{
			   loadAllHandlerMap();
			   loadEventSchedulerProcessors();
			   init();
			 }catch (Exception e) {
				 logger.error("can't load the applation composites l"+e);
				 System.exit(-1);
			}
		 }
	}
	

	private void init() throws Exception{
		String[] names = getApplicationContext().getBeanNamesForType(Initialize.class);
        for (String name : names) {
        	Initialize  initialize = (Initialize)getApplicationContext().getBean(name);
        	initialize.init();
		}
		
	}

	private void loadEventSchedulerProcessors() {
		String[] processorMachine_names = getApplicationContext().getBeanNamesForType(ProcessorMachine.class);
		ProcessorMachine proxy = null;
		if(processorMachine_names.length == 1){
			proxy= (ProcessorMachine)getApplicationContext().getBean(processorMachine_names[0]);
			String[] processor_names = ApplicationUtils.getInstance().getApplicationContext().getBeanNamesForType(EventSchedulerProcessor.class);
	    	for (String processor_name : processor_names) {
	    		EventSchedulerProcessor processor = (EventSchedulerProcessor)ApplicationUtils.getInstance().getApplicationContext().getBean(processor_name);
	    		proxy.getEventSchedulerProcessors().add(processor);
	    		processor.start();
	    		processor.getSchedule().setDataProcessorCallBack(proxy);
	    	}
		}
	}

	private void loadAllHandlerMap() throws BeansException{
		String[] names = getApplicationContext().getBeanNamesForType(EventHandlerLocator.class);
		EventHandlerLocator locator = null;
		if(names.length == 1){
			locator = (EventHandlerLocator)getApplicationContext().getBean(names[0]);
			locator.getHANDLER_MAP().putAll(ApplicationUtils.getInstance().getHandlerMap());
		}
	}
	
    public synchronized static Map<String, IHandler<Event, EventContext>>  getHandlerMap() {
    	Map<String, IHandler<Event, EventContext>> map = new HashMap<String, IHandler<Event,EventContext>>();
		String[] names = ApplicationUtils.getInstance().getApplicationContext().getBeanNamesForType(AbstractHandler.class);
    	for (String name : names) {
    		AbstractHandler handler = (AbstractHandler)ApplicationUtils.getInstance().getApplicationContext().getBean(name);
    		map.put(handler.getHANDLER_MAP_KEY(), handler);
		}
		return map;
	}
}