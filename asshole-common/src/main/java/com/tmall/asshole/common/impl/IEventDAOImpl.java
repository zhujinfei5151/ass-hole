package com.tmall.asshole.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventConstant;
import com.tmall.asshole.common.IEventDAO;

public class IEventDAOImpl extends SqlMapClientDaoSupport implements IEventDAO {

	public Integer insertEventDO(Event eventDo){
		Object id = getSqlMapClientTemplate().insert("Event.insert", eventDo);
	        return (Integer) id;
	}
	
	public Integer updateEventDO(Event eventDo) {
		Object id = getSqlMapClientTemplate().update("Event.update", eventDo);
        return  (Integer) id;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Event> queryEvent(int start, int end, int count, String env,int scheduleType) {
		
		Map<String,Integer> param = new HashMap<String,Integer>();
    	param.put("start", start);
    	param.put("end", end); 
    	param.put("count",count);
    	param.put("env",EventConstant.ENV.get(env));
		//param.put("status", EventStatus.EVENT_STATUS_UNEXECUTED.getCode());
		List<Event> eventList =  getSqlMapClientTemplate().queryForList("Event.queryEvent", param);
		return eventList;
	}


	public Integer batchChangeEventStatus(int from, int to) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("from", from);
		param.put("to", to);
		Integer count = getSqlMapClientTemplate().update("Event.batchChangeEventStatus", param);
		return count;
	}

	
	
	public Event queryEventByPrimaryKey(Long id,Integer hash_num){
		Map param = new HashMap<String,Integer>();
		param.put("id", id);
		param.put("hash_num", hash_num);
		
		Event event = (Event) getSqlMapClientTemplate(). queryForObject("Event.findByPrimaryKey",param);
		return event;
	}

	@Override
	public List<Event> queryEvent(int start, int end, int count, int env,int scheduleType) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
