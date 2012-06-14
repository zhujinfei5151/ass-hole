package com.tmall.asshole.engine.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.config.EngineConfig;
import com.tmall.asshole.schedule.IDataProcessorCallBack;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.Transition;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.Initialize;
/****
 * 
 * @author tangjinou
 *
 */
public class ProcessorMachine implements IDataProcessorCallBack<Event,EventContext>,Initialize{
	
	private ScriptEngineManager factory;
	
	private ScriptEngine scriptEngine;
	
	@Resource
	private EngineConfig engineConfig;
	
	
	
	public ProcessorMachine() {
		 factory = new ScriptEngineManager();
		 scriptEngine = factory.getEngineByName("javascript");
	}
	
	private List<EventSchedulerProcessor> eventSchedulerProcessors=new ArrayList<EventSchedulerProcessor>();
	
	public List<EventSchedulerProcessor> getEventSchedulerProcessors() {
		return eventSchedulerProcessors;
	}

	
	
	public void createEventProcess(Event event,String processName) throws Exception{
		Node n = ProcessTemplateHelper.find(processName, event.getCurrent_name());
		event.setProcess_name(processName);
		event.setProcess_instance_id(ProcessTemplateHelper.createProcessInstanceID());
		EventSchedulerProcessor eventSchedulerProcessor = getEventSchedulerProcessor(n.getScheduleType());
		eventSchedulerProcessor.addData(event);
	}
    

	public void callback(Event event,EventContext context) throws Exception {
		Node n = ProcessTemplateHelper.find(event.getProcess_name(), event.getProcess_name());
		for (Transition transition : n.transitions) {
			if(trigger(context,transition.exp)){
				Node nextN = ProcessTemplateHelper.find(event.getProcess_name(), transition.to);
		        EventSchedulerProcessor processor = getEventSchedulerProcessor(nextN.getScheduleType());
		        Class<?> eventName = Class.forName(nextN.getClassname());
		        Object newInstance = eventName.newInstance();
		        
		        //TODO: 需要数据拷贝 
		        Map<String, Object> map = context.getMap();
		        //BeanUtils.copyProperties(newInstance, orig);
		        
		        processor.addData(event);
				//目前业务场景 下一个节点只有1个会执行,不排除以后多个执行
				break;
			}
		}
		
	}

	private EventSchedulerProcessor getEventSchedulerProcessor(String scheduleType)  throws Exception{
		for (EventSchedulerProcessor processor : eventSchedulerProcessors) {
			if(processor.getScheduleType().trim().equals(scheduleType)){
				  return processor;
			}
		}
		throw new NullPointerException("can't find the processor, scheduleType="+scheduleType);
	}
	
	private boolean trigger(EventContext context,String exec) {
		for (Entry<String, Object> entry : context.getMap().entrySet()) {
			scriptEngine.put(entry.getKey(), entry.getValue());
		}
		try {
			Boolean result = (Boolean) scriptEngine.eval(exec);
		    return result;
		} catch (ScriptException e) {
		    return false;
		}
	}

	public void init() throws Exception {
		//加载流程模版
		ProcessTemplateHelper.deploy(engineConfig.getProcessTemplateFolders());
	}
  	

	

}
