package com.tmall.asshole.engine.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventConstant;
import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.common.EventEnv;
import com.tmall.asshole.common.EventStatus;
import com.tmall.asshole.common.IEventDAO;
import com.tmall.asshole.config.ProcessorConfig;
import com.tmall.asshole.engine.IEngine;
import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;
import com.tmall.asshole.schedule.IContextCreate;
import com.tmall.asshole.schedule.IDataLoader;
import com.tmall.asshole.schedule.IDataProcessor;
import com.tmall.asshole.schedule.IDataProducer;
import com.tmall.asshole.schedule.Schedule;


/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class EventSchedulerProcessor implements IDataLoader<Event>,IDataProcessor<Event,EventContext>,IDataProducer<Event>,IContextCreate<EventContext>{

	private static transient Log logger = LogFactory
			.getLog(EventSchedulerProcessor.class);
	
	private IEngine<Event, EventContext> eventEngine;
	
	private IEventDAO eventDAO;
	
	private ProcessorConfig processorConfig;
	
	private Schedule<Event,EventContext> schedule;
	
    private int processorNumber;
	
	public ProcessorConfig getProcessorConfig() {
		return processorConfig;
	}

	public void setProcessorConfig(ProcessorConfig processorConfig) {
		this.processorConfig = processorConfig;
	}

	public IEventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(IEventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public Schedule<Event,EventContext> getSchedule() {
		return schedule;
	}

	public void start(){
		   schedule = new Schedule<Event,EventContext>(this, this,this,processorConfig);
		   schedule.strart();
		   processorNumber =  processorConfig.getProcessorNumber();
	}
	
	public void stopSchedule(){
		schedule.stopSchedule();
	}
	

	public void setEventDAOForTest(IEventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public IEngine<Event, EventContext> getEventEngine() {
		return eventEngine;
	}

	public void setEventEngine(IEngine<Event, EventContext> eventEngine) {
		this.eventEngine = eventEngine;
	}
	

	public int getProcessorNumber() {
		return processorNumber;
	}


	@Autowired
	private ProtocolCodecFactory<Event> protocolCodecFactory;
	

	public void setProtocolCodecFactoryForTest(ProtocolCodecFactory<Event> protocolCodecFactory) {
		this.protocolCodecFactory = protocolCodecFactory;
	}

	public void process(Event data,EventContext context) throws Exception {
		try {
		     if (eventEngine.fire(data, context)) {
		           data.setStatus(EventStatus.EVENT_STATUS_SUCCESS);
		           data.setProcessLogs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	               data.setOperator(context.getOperator());
	               data.setExecCount(data.getExecCount() + 1);
		     } else {
	                data.setExecCount(data.getExecCount() + 1);
	                data.setStatus(EventStatus.EVENT_STATUS_FAILED);
	                data.setProcessLogs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	                data.setOperator(context.getOperator());
	          }
			
		} catch (Exception e) {
			    data.setExecCount(data.getExecCount()+1);
	            data.setStatus(EventStatus.EVENT_STATUS_EXCEPTION);
	            data.setProcessLogs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	            data.setOperator(context.getOperator());
	            data.appendMemo(e.getMessage());
				logger.error("update status failed,"+e.getMessage());
			throw e;
		}  finally{
			eventDAO.updateEventDO(data);
		}
	}

	public List<Event> getDataList(int start, int end, int rownum,
			EventEnv envionmentGroup,String executeMachineAlias) throws Exception{
		List<Event> l = eventDAO.queryEvent(start, end, rownum,envionmentGroup.getCode(),processorNumber);
		List<Event> noErrorLst = new ArrayList<Event>();
	    for (Event data : l) {
	    	  Event newData = (Event) Class.forName(data.getTypeClass()).newInstance();
	    	
	    	   try{
	    		   Date now = new Date();
	    		   if(data.isDelayExec()){
	    			   if(now.getTime()>=data.getExecStartTime().getTime()){
	    			      addNoErrorListAndUpdateEventDO(start, end,executeMachineAlias, noErrorLst, data, newData); 
	    			   }
	    			   continue;
	    		   }else{
	    			   addNoErrorListAndUpdateEventDO(start, end,executeMachineAlias, noErrorLst, data, newData);
	    		   }
	    		   
	    		   
	    	   } catch (Exception e) {
	    		    logger.error(e.getStackTrace());
	    		    data.setStatus(EventConstant.EVENT_STATUS_PARAMETER_ERROR);
	    			eventDAO.updateEventDO(data);
					continue;
			   }
		}
	    return noErrorLst;
	}

	private Event addNoErrorListAndUpdateEventDO(int start, int end, String executeMachineAlias,
			List<Event> noErrorLst, Event data, Event newData) throws Exception {
		  Event event = protocolCodecFactory.getDecoder().decode(data.getContext().getBytes(), newData);
		  event.setId(data.getId());
		  event.setHashNum(data.getHashNum());
		  event.setStatus(EventConstant.EVENT_STATUS_LOADED);
		  event.setExecCount(event.getExecCount() + 1);
		  event.setExecuteMachineHashRange(start+"--"+end);
		  event.setExecuteMachineIp(executeMachineAlias);
		  noErrorLst.add(event);
		  eventDAO.updateEventDO(event);
		return event;
	}

	@Override
	public void addData(Event event) throws Exception{
		event.setContext(new String(protocolCodecFactory.getEncoder().encode(event)));
		eventDAO.insertEventDO(event);
	}

	@Override
	public EventContext create() {
		return new EventContext();
	}

}
