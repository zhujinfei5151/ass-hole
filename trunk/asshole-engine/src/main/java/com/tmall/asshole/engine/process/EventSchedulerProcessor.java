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


/****
 * 
 * @author tangjinou
 * 
 * @param <Event>
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
		           data.setProcess_logs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	               data.setOperator(context.getOperator());
		     } else {
	                data.setExec_count(data.getExec_count() + 1);
	                data.setStatus(EventStatus.EVENT_STATUS_FAILED);
	                data.setProcess_logs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	                data.setOperator(context.getOperator());
	          }
			
		} catch (Exception e) {
			    data.setExec_count(data.getExec_count()+1);
	            data.setStatus(EventStatus.EVENT_STATUS_EXCEPTION);
	            data.setProcess_logs(StringUtils.isBlank(context.getProcessLogs())?"":context.getProcessLogs());
	            data.setOperator(context.getOperator());
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
	    	   try{
	    		   Date now = new Date();
	    		   
	    		   //如果是延迟打标的 时间又没有到则只更新执行时间
	    		   if(data.isIsDelayExec()){
	    			   if(now.getTime()>=data.getExecStartTime().getTime()){
	    			      Event event = protocolCodecFactory.getDecoder().decode(data.getContext().getBytes(), data);
	    			      eventDAO.updateEventDO(event); 
	    			      continue;
	    			   }
	    		   }
	    		   
	    	          Event event = protocolCodecFactory.getDecoder().decode(data.getContext().getBytes(), data);
	    	  	      noErrorLst.add(event);
	    	  	      event.setStatus(EventConstant.EVENT_STATUS_LOADED);
	    	          event.setExec_count(event.getExec_count() + 1);
	    	  	      event.setExecute_machine_hash_range(start+"--"+end);
	    	  	      event.setExecute_machine_ip(executeMachineAlias);
				      eventDAO.updateEventDO(event);
                	
                	  
	    	   } catch (Exception e) {
	    		    logger.error(e.getStackTrace());
	    		    data.setStatus(EventConstant.EVENT_STATUS_PARAMETER_ERROR);
	    			eventDAO.updateEventDO(data);
					continue;
	    		    // 一条记录的失败 不影响全局
			   }
		}
	    return noErrorLst;
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
