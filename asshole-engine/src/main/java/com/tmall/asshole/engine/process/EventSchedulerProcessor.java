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
import com.tmall.asshole.engine.IEngine;
import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;
import com.tmall.asshole.schedule.IDataLoader;
import com.tmall.asshole.schedule.IDataProcessor;


/****
 * 
 * @author tangjinou
 * 
 * @param <Event>
 */
public class EventSchedulerProcessor implements IDataLoader<com.tmall.asshole.common.Event>,IDataProcessor<com.tmall.asshole.common.Event> {

	private static transient Log logger = LogFactory
			.getLog(EventSchedulerProcessor.class);
	
	
	@Autowired
	private IEngine<Event, EventContext> eventEngine;
	
	@Autowired
	private IEventDAO eventDAO;
	

	public void setEventDAOForTest(IEventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public IEngine<Event, EventContext> getEventEngine() {
		return eventEngine;
	}

	public void setEventEngine(IEngine<Event, EventContext> eventEngine) {
		this.eventEngine = eventEngine;
	}

	@Autowired
	private ProtocolCodecFactory<Event> protocolCodecFactory;
	

	public void setProtocolCodecFactoryForTest(ProtocolCodecFactory<Event> protocolCodecFactory) {
		this.protocolCodecFactory = protocolCodecFactory;
	}

	public void process(Event data) throws Exception {
		EventContext context = new EventContext();
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
			if (logger.isErrorEnabled()) {
				logger.error("update status failed", e);
			}
			throw e;
		}  finally{
			eventDAO.updateServiceEventDO(data);
		}
	}

	public List<Event> getDataList(int start, int end, int rownum,
			EventEnv envionmentGroup, ScheduleType scheduleType, String executeMachineAlias) throws Exception{
		List<Event> l = eventDAO.queryEvent(start, end, rownum,envionmentGroup.getCode(),scheduleType.getCode());
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

}
