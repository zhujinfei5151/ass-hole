package com.tmall.asshole.engine.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.config.EngineConfig;
import com.tmall.asshole.schedule.IDataProcessorCallBack;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.Transition;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.BeanCopyUtil;
import com.tmall.asshole.util.BeanCopyUtilTest;
import com.tmall.asshole.util.Initialize;
/****
 * 
 * @author tangjinou
 *
 */
public class ProcessorMachine implements IDataProcessorCallBack<Event,EventContext>,Initialize{
	
	private ScriptEngineManager factory;
	
	private ScriptEngine scriptEngine;
	
	@Autowired
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
		//根据类型反找到节点
		List<Node> nodes = ProcessTemplateHelper.find(processName, event.getClass());
		if(nodes.size()==0){
			throw new NullPointerException("can't find the event, type="+event.getClass()+" in the processs, name="+processName);
		}
		Node n = nodes.get(0);
		event.setProcessName(processName);
		event.setProcess_instance_id(ProcessTemplateHelper.createProcessInstanceID());
		EventSchedulerProcessor eventSchedulerProcessor = getEventSchedulerProcessor(n.getScheduleType());
		eventSchedulerProcessor.addData(event);
	}
    

	public void callback(Event event,EventContext context) throws Exception {
		Node n = ProcessTemplateHelper.find(event.getProcessName(), event.getCurrentName());
		for (Transition transition : n.transitions) {
			if(trigger(context,transition.exp)){
				Node nextN = ProcessTemplateHelper.find(event.getProcessName(), transition.to);
		        EventSchedulerProcessor processor = getEventSchedulerProcessor(nextN.getScheduleType());
		        Class<?> eventName = Class.forName(nextN.getClassname());
		        Object newInstance = eventName.newInstance();
		        
		        //TODO: 需要数据拷贝 
		        Map<String, Object> map = context.getMap();
		        BeanCopyUtil.copy(newInstance, map);
		        
		        processor.addData((Event)newInstance);
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
			if(StringUtils.isBlank(exec)){
				return true;
			}
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
