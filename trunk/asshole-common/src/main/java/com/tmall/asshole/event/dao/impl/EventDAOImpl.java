package com.tmall.asshole.event.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.taobao.common.dao.persistence.exception.DAOException;
import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventConstant;
import com.tmall.asshole.event.common.EventStatus;
import com.tmall.asshole.event.dao.EventDAO;

public class EventDAOImpl extends SqlMapClientDaoSupport implements EventDAO {

	public Long insertEventDO(Event eventDo) throws DAOException{
		Object id = getSqlMapClientTemplate().insert("Event.insert", eventDo);
	        return (Long) id;
	}
	
	public Integer updateEvent(Event eventDo) throws DAOException{
		Object id = getSqlMapClientTemplate().update("Event.update", eventDo);
        return  (Integer) id;
	}
	@SuppressWarnings("unchecked")
	public List<Event> queryEvent(int start, int end, int count, String env) {
		
		Map<String,Integer> param = new HashMap<String,Integer>();
    	param.put("start", start);
    	param.put("end", end); 
    	param.put("count",count);
    	param.put("env",EventConstant.ENV.get(env));
		//param.put("status", EventStatus.EVENT_STATUS_UNEXECUTED.getCode());
		List<Event> eventList =  getSqlMapClientTemplate().queryForList("Event.queryEvent", param);
		return eventList;
	}

	public Integer batchChangeEventStatus(int oldStatus, int newStatus) throws DAOException{
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("from", oldStatus);
		param.put("to", newStatus);
		Integer count = getSqlMapClientTemplate().update("Event.batchChangeStatus", param);
		return count;
	}

	
	
	@SuppressWarnings("unchecked")
	public Integer batchChangeEventStatusBytime(int from, int to,int minute) {
		Map param = new HashMap();
        param.put("from", from);
        param.put("to", to);
        Date queryDate = DateUtils.addMinutes(new Date(), -minute);
        param.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(queryDate));
        int count = getSqlMapClientTemplate().update("Event.batchChangeStatusByTime", param);
		return count;
	}

}
