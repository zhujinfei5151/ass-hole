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
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventConstant;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.common.EventResult;
import com.tmall.asshole.config.MachineConfig;
import com.tmall.asshole.engine.http.JettyServer;
import com.tmall.asshole.schedule.IDataProcessorCallBack;
import com.tmall.asshole.schedule.Schedule;
import com.tmall.asshole.schedule.monitor.ScheduleMonitor;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.Transition;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.BeanCopyUtil;
import com.tmall.asshole.util.Initialize;
import com.tmall.asshole.zkclient.INodeChange;
import com.tmall.asshole.zkclient.ZKClient;
import com.tmall.asshole.zkclient.ZKConfig;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ProcessorMachine implements IDataProcessorCallBack<Event,EventContext>,Initialize{
	
	private static transient Log logger = LogFactory.getLog(Schedule.class);
	
	private ScriptEngineManager factory;
	
	private ScriptEngine scriptEngine;
	
	private MachineConfig machineConfig;
	
	
	private  ZKClient zkClient;
	
	private  ScheduleMonitor scheduleMonitor;
	

	public MachineConfig getMachineConfig() {
		return machineConfig;
	}


	public ScheduleMonitor getScheduleMonitor() {
		return scheduleMonitor;
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

	
	/***
	 * 创建流程实例，流程实例ID随机产生
	 * 
	 * @param event
	 * @param processName
	 * @throws Exception
	 */
	public void createEventProcess(Event event,String processName) throws Exception{
		createEventProcess(event,processName,ProcessTemplateHelper.createProcessInstanceID());
	}
	
	
	/***
	 * 创建流程实例，流程实例ID根据需要定制 ,如交易订单号之类的组成的唯一processInstanceID
	 * 
	 * 支持同步调用和异步调用
	 * 
	 * @param event
	 * @param processName
	 * @throws Exception
	 */
	public EventResult createEventProcess(Event event,String processName,Long processInstanceID) throws Exception{
		//根据类型反找到节点
		List<Node> nodes = ProcessTemplateHelper.find(processName, event.getClass());
		if(nodes.size()==0){
					throw new NullPointerException("can't find the event, type="+event.getClass()+" in the processs, name="+processName);
		}
	    Node n = nodes.get(0);
		event.setProcessName(processName);
		event.setProcessInstanceId(processInstanceID);
		event.setTypeClass(event.getClass().getName());
		event.setCurrentName(n.getName());
		event.setEnv(machineConfig.getEnv());
		event.setSynInvoke(n.getSyn());
		
		return invokeNextNode(event, n);
	}
	
	/***
	 * 继续流程的流转
	 * 
	 * 支持同步调用和异步调用
	 * 
	 * @param event
	 * @param processName
	 * @param nodeName
	 * @param processInstanceID
	 * @throws Exception
	 */
	public EventResult contineEventProcess(Event event,String processName,String nodeName,Long processInstanceID) throws Exception{
		Node n = ProcessTemplateHelper.find(processName, event.getClass(),nodeName);
		if(n==null){
			throw new NullPointerException("can't find the event, type="+event.getClass()+" in the processs, name="+processName);
		}
		if(processInstanceID==null || processInstanceID==0){
			throw new NullPointerException("processInstanceID can't be null or 0");
		}
		
		event.setProcessName(processName);
		event.setProcessInstanceId(processInstanceID);
		event.setTypeClass(event.getClass().getName());
		event.setCurrentName(n.getName());
		event.setEnv(machineConfig.getEnv());
		
		return invokeNextNode(event, n);		
		
	}


	private EventResult invokeNextNode(Event event, Node n) throws Exception {
		if(n.getSyn()==true){
			//同步调用
			//直接调用
			EventResult result=new EventResult();
			result.setSynInvoke(true);
			EventSchedulerProcessor eventSchedulerProcessor = getEventSchedulerProcessor(Integer.parseInt(n.getProcessorNumber()));
			//setHashNum(event, n, eventSchedulerProcessor);
		    logger.info("procss start, name="+event.getProcessName()+",id="+event.getProcessInstanceId()+" syn=true");
		    try{
		      eventSchedulerProcessor.addData(event);
		      EventContext context= new EventContext();
		      eventSchedulerProcessor.process(event, context);
		      result.setSuccess(event.getStatus().intValue()==EventConstant.EVENT_STATUS_SUCCESS?true:false);
		      result.setErrorMsg(event.getMemo());
		    }catch (Exception e) {
		    	result.setSuccess(false);
		    	result.setErrorMsg(e.getMessage());
		    	//throw e;
			}
			return result;
			
		}else{
			
			//异步调用
		    EventSchedulerProcessor eventSchedulerProcessor = getEventSchedulerProcessor(Integer.parseInt(n.getProcessorNumber()));
			setHashNum(event, n, eventSchedulerProcessor);
			logger.info("procss start, name="+event.getProcessName()+",id="+event.getProcessInstanceId());
			eventSchedulerProcessor.addData(event);
			EventResult result=new EventResult();
			result.setSuccess(true);
			return result;
		}
	}


	private void setHashNum(Event event, Node n,
			EventSchedulerProcessor eventSchedulerProcessor) {
		//如果设定了hash值则不会修改
		if(!StringUtils.isBlank(n.getHashNum())){
		   event.setHashNum(Integer.parseInt(n.getHashNum()));
		}else{
		   // 0 - MAXHASHNUM
		   event.setHashNum(RandomUtils.nextInt(eventSchedulerProcessor.getSchedule().getScheduleFgetcPolicy().getMaxHashNum()));
		}
	}
	
    

	public void callback(Event event,EventContext context) throws Exception {
		if(event.getStatus()!=EventConstant.EVENT_STATUS_SUCCESS){
			logger.error("due to node "+event.getCurrentName()+" execute not success, procss "+event.getProcessName()+" finished, process id="+event.getProcessInstanceId()+",last node name="+event.getCurrentName());
		    return;
		}
		
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
				if(nextN.isForeach()){
					if(context.getDataList()!=null){
						  List<Map<String, Object>> dataList = context.getDataList();
						  for (Map<String, Object> map : dataList) {
							  callback(event, context, nextN, map);
						  }
						  break;
					}
				}else{
		           callback(event, context, nextN, context.getMap());
				   break;
				}
			}
		}
		
	}



	private void callback(Event event, EventContext context, Node nextN,Map<String, Object> map)
			throws Exception, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		EventSchedulerProcessor processor = getEventSchedulerProcessor(Integer.parseInt(nextN.getProcessorNumber()));
		  Class<?> eventName = Class.forName(nextN.getClassname());
		  Event newEvent = (Event)eventName.newInstance();
		  //Map<String, Object> map = context.getMap();
		  BeanCopyUtil.copy(newEvent, map);
		//关键属性需要copy
		  newEvent.setProcessName(event.getProcessName());
		  newEvent.setCurrentName(nextN.getName());
		  newEvent.setProcessInstanceId(event.getProcessInstanceId());
		  newEvent.setProcessorNumber(Integer.parseInt(nextN.getProcessorNumber()));
		  newEvent.setEnv(machineConfig.getEnv());
		  newEvent.setTypeClass(nextN.getClassname());
		  
		  //如果设定了hash值则不会修改
		  if(!StringUtils.isBlank(nextN.getHashNum())){
			   event.setHashNum(Integer.parseInt(nextN.getHashNum()));
		  }else{
				  // 0 - MAXHASHNUM
				  newEvent.setHashNum(RandomUtils.nextInt(processor.getSchedule().getScheduleFgetcPolicy().getMaxHashNum()));
		  }
		  
		
		
		  logger.info("procss excute, name="+event.getProcessName()+",id="+event.getProcessInstanceId()+",current node name="+event.getCurrentName());
		  processor.addData(newEvent);
		   //目前业务场景 下一个节点只有1个会执行,不排除以后多个执行
	}
	

	private EventSchedulerProcessor getEventSchedulerProcessor(int processNumber)  throws Exception{
		for (EventSchedulerProcessor processor : eventSchedulerProcessors) {
			if(processor.getProcessorNumber()==processNumber){
				  return processor;
			}
		}
		logger.error("can't find the processor, processorNumber="+processNumber);
		throw new NullPointerException("can't find the processorr, processorNumber="+processNumber);
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
		
		scheduleMonitor = new ScheduleMonitor();
        
		scheduleMonitor.start();
		
		
		ZKConfig zkConfig =new ZKConfig(machineConfig.getUsePermissions(), machineConfig.getUsername(), machineConfig.getPassword(), machineConfig.getZkConnectString(), machineConfig.getZkSessionTimeout(), machineConfig.getRootPath(), machineConfig.getLocalIPAddress());
		 
		 
		logger.info("start the the  zookeeper client");
		zkClient = new ZKClient(iNodeChanges,zkConfig);
		zkClient.start();
		  
		new JettyServer(this).start();
		
		  
	}
  	

	

}
