package com.tmall.asshole.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;

public class EventDAO extends SqlMapClientDaoSupport implements IEventDAO {

	/**
	 * 锟斤拷锟斤拷
	 */
	public Long insertEventDO(Event eventDo){
		Object id = getSqlMapClientTemplate().insert("Event.insert", eventDo);
	        return (Long) id;
	}
    /**
     * 锟斤拷锟斤拷
     */
	public Integer updateEventDO(Event eventDo) {
		Object id = getSqlMapClientTemplate().update("Event.update", eventDo);
        return  (Integer) id;
	}
	
	/**
	 * 锟斤拷锟斤拷锟斤拷询  锟斤拷锟截斤拷锟斤拷锟绞憋拷锟斤拷群锟剿筹拷蚍祷锟�
	 */
	public List<Event> queryEvent(int start, int end, int count, int env,int process_number) {
		
		Map<String,Integer> param = new HashMap<String,Integer>();
    	param.put("start", start);
    	param.put("end", end); 
    	param.put("count",count);
    	param.put("env",env);
    	param.put("processorNumber",process_number);
    	List<Event> eventList =  getSqlMapClientTemplate().queryForList("Event.eventQuery", param);
		return eventList;
	}

	/**
	 * 锟斤拷锟斤拷锟睫革拷状态
	 */
	public Integer batchChangeEventStatus(int from, int to) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("from", from);
		param.put("to", to);
		Integer count = getSqlMapClientTemplate().update("Event.batchChangeEventStatus", param);
		return count;
	}

	
	/**
	 * 锟斤拷id锟斤拷hash_num锟斤拷询
	 */
	public Event queryEventByPrimaryKey(Long id,Integer hash_num){
		Map param = new HashMap<String,Integer>();
		param.put("id", id);
		param.put("hash_num", hash_num);
		
		Event event = (Event) getSqlMapClientTemplate(). queryForObject("Event.findByPrimaryKey",param);
		return event;
	}



}
