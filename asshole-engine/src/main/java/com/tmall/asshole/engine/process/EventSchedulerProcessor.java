package com.tmall.asshole.engine.process;

import java.util.ArrayList;
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
import com.tmall.asshole.common.ScheduleType;
import com.tmall.asshole.config.EngineConfig;
import com.tmall.asshole.engine.IEngine;
import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;
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
public class EventSchedulerProcessor implements IDataLoader<Event>,IDataProcessor<Event,EventContext>,IDataProducer<Event> {

	private static transient Log logger = LogFactory
			.getLog(EventSchedulerProcessor.class);
	
	@Autowired
	private IEngine<Event, EventContext> eventEngine;
	
	@Autowired
	private IEventDAO eventDAO;
	
	@Autowired
	private EngineConfig engineConfig;
	
	private Schedule<Event,EventContext> schedule;
	
	private String scheduleType;
	
	
	public Schedule<Event,EventContext> getSchedule() {
		return schedule;
	}

	public void start(){
		   schedule = new Schedule<Event,EventContext>(this, this, engineConfig);
		   schedule.strart();
		   scheduleType = engineConfig.getScheduleType();
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

	public String getScheduleType() {
		return scheduleType;
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
			eventDAO.updateServiceEventDO(data);
		}
	}

	public List<Event> getDataList(int start, int end, int rownum,
			EventEnv envionmentGroup,String executeMachineAlias) throws Exception{
		List<Event> l = eventDAO.queryEvent(start, end, rownum,envionmentGroup.getCode(),ScheduleType.valueOf(scheduleType).getCode());
		List<Event> noErrorLst = new ArrayList<Event>();
	    for (Event data : l) {
	    	   try{
	    	      Event event = protocolCodecFactory.getDecoder().decode(data.getContext().getBytes(), data);
	    	  	  noErrorLst.add(event);
	    	  	  event.setStatus(EventConstant.EVENT_STATUS_LOADED);
	    	      event.setExec_count(event.getExec_count() + 1);
	    	  	  event.setExecute_machine_hash_range(start+"--"+end);
	    	  	  event.setExecute_machine_ip(executeMachineAlias);
				  eventDAO.updateServiceEventDO(event);
	    	   } catch (Exception e) {
	    		    logger.error(e.getStackTrace());
	    		    data.setStatus(EventConstant.EVENT_STATUS_PARAMETER_ERROR);
	    			eventDAO.updateServiceEventDO(data);
					continue;
	    		    // 一条记录的失败 不影响全局
			   }
		}
	    return noErrorLst;
	}

	@Override
	public void addData(Event event) throws Exception{
		event.setContext(new String(protocolCodecFactory.getEncoder().encode(event)));
		eventDAO.insertServiceEventDO(event);
	}

}
