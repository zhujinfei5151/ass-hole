package com.tmall.asshole.schedule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;
import com.tmall.asshole.event.engine.IAfterHandler;
import com.tmall.asshole.schedule.node.EventNode;
/****
 * 
 * @author tangjinou
 *
 */
public class EPProxy implements IAfterHandler<Event,EventContext>{
	/***
	 *  流程模版图
	 */
	private Map<String, LinkedList<EventNode>> processTemplateMap = new HashMap<String, LinkedList<EventNode>>();

	@Override
	public boolean afterHandle(Event event, EventContext context) {
		
		LinkedList<EventNode> linkedList = processTemplateMap.get(event.getProcess_name());
		
		int index=-1;
		
		for (int i = 0; i < linkedList.size(); i++) {
			index = i;
		}
		
		if( index == -1 ){
			
		}
		
		if((index+1) < linkedList.size()){
			EventNode eventNode = linkedList.get(index+1);
			
		}
		
		
		return false;
	}
	
	private Long createProcessInstanceID(){
		//根据事件来创建
		int random=(int)(Math.random()*100) + 100;
		String process_instance_id_str= System.currentTimeMillis() +"" + random;
		return Long.parseLong(process_instance_id_str);
	}
	
    public void initProcessTemplates(){
    	//需要加载XML模版
    }
	
	public  LinkedList<EventNode> getProcessTemplate(String templateName){
		return processTemplateMap.get(templateName);
	}
	
	
	private void invokeEventProcess(Event event){
	}
	
	
	
	public void invokeFirstEventProcess(Event event,String templateName) throws Exception{
		if(!processTemplateMap.keySet().contains(templateName)){
			throw new NullPointerException("can't find the templateName");
		}
		event.setProcess_name(templateName);
		event.setProcess_instance_id(createProcessInstanceID());
		invokeEventProcess(event);
	}
  	

	

}
