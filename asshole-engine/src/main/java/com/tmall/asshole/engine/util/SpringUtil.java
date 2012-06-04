package com.tmall.asshole.engine.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ApplicationObjectSupport;


/***
 * 
 * @author jiuxian.tjo
 *
 */
public class SpringUtil extends ApplicationObjectSupport implements ApplicationListener{
	private static SpringUtil stools = null;

	public synchronized static Object getBean(String beanName) throws Exception{
		return SpringUtil.getInstance().getApplicationContext().getBean(beanName);
	}
	
	public synchronized static Object getBean(Class<?> type) throws Exception{
		if(SpringUtil.getInstance().getApplicationContext()==null){
			throw new NullPointerException("can't find the spring context,did you start spring container or config the spring container");
		}
		String[] names = SpringUtil.getInstance().getApplicationContext().getBeanNamesForType(type);
		if(names==null || names.length<1){
			//throw new NullPointerException("can't find "+type.getName()+" in spring container");
//			 RootBeanDefinition beanDefinition = new RootBeanDefinition();
//			 beanDefinition.setAttribute(type.getSimpleName().toLowerCase(), type.newInstance());
//			 beanFactory.registerBeanDefinition(type.getSimpleName().toLowerCase(), beanDefinition);
			 return SpringUtil.getInstance().getApplicationContext().getBean(type.getSimpleName().toLowerCase());
		}
		if(names.length>1){
			throw new Exception("there are too many "+ type.getName()+" in spring container");
		}
		
		return SpringUtil.getInstance().getApplicationContext().getBean(names[0]);
	}
	

	public synchronized static SpringUtil getInstance() {
		if (stools == null) {
			stools = new SpringUtil();
		}
		return stools;
	}
	
	protected void initApplicationContext() throws BeansException {
		SpringUtil.getInstance().setApplicationContext(getApplicationContext());
	}
    
	private boolean handlerMapLoaded;

	public void onApplicationEvent(ApplicationEvent event) {
		 if(event instanceof ContextRefreshedEvent && handlerMapLoaded == false){
			 handlerMapLoaded = true;
		//	 loadAllHandlerMap();
		 }
	}
	
	
//	private void loadAllHandlerMap() throws BeansException{
//		String[] names = getApplicationContext().getBeanNamesForType(EventHandlerLocator.class);
//		EventHandlerLocator locator = null;
//		if(names.length == 1){
//			locator = (EventHandlerLocator)getApplicationContext().getBean(names[0]);
//			locator.getHANDLER_MAP().putAll(SpringUtil.getInstance().getHandlerMap());
//		}
//	}
//	
//    public synchronized static Map<String, Handler<Event, EventContext>>  getHandlerMap() {
//    	Map<String, Handler<Event, EventContext>> map = new HashMap<String, Handler<Event,EventContext>>();
//		String[] names = SpringUtil.getInstance().getApplicationContext().getBeanNamesForType(AbstractHandler.class);
//    	for (String name : names) {
//    		AbstractHandler handler = (AbstractHandler)SpringUtil.getInstance().getApplicationContext().getBean(name);
//    		map.put(handler.getHANDLER_MAP_KEY(), handler);
//		}
//		return map;
//	}

}
