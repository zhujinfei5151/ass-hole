package com.tmall.asshole.engine.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.common.ScheduleType;
import com.tmall.asshole.config.MachineConfig;
import com.tmall.asshole.schedule.IDataProcessorCallBack;
import com.tmall.asshole.schedule.Schedule;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.Transition;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.BeanCopyUtil;
import com.tmall.asshole.util.Initialize;
import com.tmall.asshole.zkclient.INodeChange;
import com.tmall.asshole.zkclient.ZKClient;
import com.tmall.asshole.zkclient.ZKConfig;
/****
 * 
 * @author tangjinou
 *
 */
public class ProcessorMachine implements IDataProcessorCallBack<Event,EventContext>,Initialize{
	
	private static transient Log logger = LogFactory.getLog(Schedule.class);
	
	private ScriptEngineManager factory;
	
	private ScriptEngine scriptEngine;
	
	private MachineConfig machineConfig;
	

	public MachineConfig getMachineConfig() {
		return machineConfig;
	}



	public void setMachineConfig(MachineConfig machineConfig) {
		this.machineConfig = machineConfig;
	}



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
		event.setProcessInstanceId(ProcessTemplateHelper.createProcessInstanceID());
		event.setCurrentName(n.getName());
		EventSchedulerProcessor eventSchedulerProcessor = getEventSchedulerProcessor(n.getScheduleType());
		logger.info("procss start, name="+event.getProcessName()+",id="+event.getProcessInstanceId());
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
		        Event newEvent = (Event)eventName.newInstance();
		        Map<String, Object> map = context.getMap();
		        BeanCopyUtil.copy(newEvent, map);
		        //关键属性需要copy
		        newEvent.setProcessName(event.getProcessName());
		        newEvent.setCurrentName(nextN.getName());
		        newEvent.setProcessInstanceId(event.getProcessInstanceId());
		        newEvent.setSchedule_type(ScheduleType.valueOf(nextN.getScheduleType()).getCode());
		        
		    	logger.info("procss excute, name="+event.getProcessName()+",id="+event.getProcessInstanceId()+",current node name="+event.getCurrentName());
		        processor.addData(newEvent);
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
			Boolean result = (Boolean) scriptEngine.eval(exec.replace("$", ""));
			
			
			if(!result){
				logger.info("exectue '"+exec+"' fail, so can't move to next node ");
			}
			
		    return result;
		} catch (ScriptException e) {
			logger.error("execute the script error :"+ exec +"  " +e.getStackTrace());
		    return false;
		}
	}

	public void init() throws Exception {
		if(machineConfig==null){
			throw new NullArgumentException("can' t find the machineConfig in "+this.getClass()+", could not find the engineConfig, pls check if the engineConfig is setted in spring config file;");
		}
		
		if(machineConfig.getProcessTemplateFolders()==null || machineConfig.getProcessTemplateFolders().size()==0){
			throw new NullArgumentException("process template folder in machineconfig can't be empty!");
		}
		
		
		//加载流程模版
		ProcessTemplateHelper.deploy(machineConfig.getProcessTemplateFolders());
		
		List<INodeChange> iNodeChanges = new ArrayList<INodeChange>();
		
		for (EventSchedulerProcessor processor : eventSchedulerProcessors) {
			iNodeChanges.add(processor.getSchedule().getScheduleFgetcPolicy());
		}
		
		if(!machineConfig.getStartZK()){
			logger.error("no need to start zookeeper client, pls check the var of startZK  in EngineConfig");
			return;
		}  
		 ZKConfig zkConfig =new ZKConfig(machineConfig.getUsePermissions(), machineConfig.getUsername(), machineConfig.getPassword(), machineConfig.getZkConnectString(), machineConfig.getZkSessionTimeout(), machineConfig.getRootPath(), machineConfig.getLocalIPAddress());
		 
		
		  logger.info("start the the  zookeeper client");
		  ZKClient client = new ZKClient(iNodeChanges,zkConfig);
		  client.start();
	}
  	

	

}
