package com.tmall.asshole.engine.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.config.EngineConfig;
import com.tmall.asshole.schedule.IDataProcessorCallBack;
import com.tmall.asshole.schedule.Schedule;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.Transition;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.BeanCopyUtil;
import com.tmall.asshole.util.Initialize;
/****
 * 
 * @author tangjinou
 *
 */
public class ProcessorMachine implements IDataProcessorCallBack<Event,EventContext>,Initialize{
	
	private static transient Log logger = LogFactory.getLog(Schedule.class);
	
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
		if(n.transitions==null || n.transitions.size()==0){
			logger.info("no transitions ,procss finished, name="+event.getProcessName()+",id="+event.getProcessInstanceId()+",last node name="+event.getCurrentName());
			return;
		}
		
		for (Transition transition : n.transitions) {
			if(trigger(context,transition.exp)){
				
				if(StringUtils.isBlank( transition.to) || transition.to.trim().toLowerCase().equals("end")){
					logger.info("procss finished, name="+event.getProcessName()+",id="+event.getProcessInstanceId()+",last node name="+event.getCurrentName());
					return;
				}
				Node nextN = ProcessTemplateHelper.find(event.getProcessName(), transition.to);
		        EventSchedulerProcessor processor = getEventSchedulerProcessor(nextN.getScheduleType());
		        Class<?> eventName = Class.forName(nextN.getClassname());
		        Object newInstance = eventName.newInstance();
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
		logger.error("can't find the processor, scheduleType="+scheduleType);
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
			logger.error("execute the script error :" +e.getStackTrace());
		    return false;
		}
	}

	public void init() throws Exception {
		//加载流程模版
		ProcessTemplateHelper.deploy(engineConfig.getProcessTemplateFolders());
	}
  	

	

}
