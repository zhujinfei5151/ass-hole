package com.tmall.asshole.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventConstant;
import com.tmall.asshole.common.IEventDAO;

public class IEventDAOImpl extends SqlMapClientDaoSupport implements IEventDAO {

	/**
	 * 插入
	 */
	public Integer insertEventDO(Event eventDo){
		Object id = getSqlMapClientTemplate().insert("Event.insert", eventDo);
	        return (Integer) id;
	}
    /**
     * 更新
     */
	public Integer updateEventDO(Event eventDo) {
		Object id = getSqlMapClientTemplate().update("Event.update", eventDo);
        return  (Integer) id;
	}
	
	/**
	 * 批量查询  返回结果按照时间先后顺序返回
	 */
	public List<Event> queryEvent(int start, int end, int count, int env,int processorNumber) {
		
		Map<String,Integer> param = new HashMap<String,Integer>();
    	param.put("start", start);
    	param.put("end", end); 
    	param.put("count",count);
    	param.put("env",env);
    	param.put("processorNumber",processorNumber);

    	List<Event> eventList =  getSqlMapClientTemplate().queryForList("Event.eventQuery", param);
		return eventList;
	}

	/**
	 * 批量修改状态
	 */
	public Integer batchChangeEventStatus(int from, int to) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("from", from);
		param.put("to", to);
		Integer count = getSqlMapClientTemplate().update("Event.batchChangeEventStatus", param);
		return count;
	}

	
	/**
	 * 按id和hash_num查询
	 */
	public Event queryEventByPrimaryKey(Long id,Integer hashNum){
		Map param = new HashMap<String,Integer>();
		param.put("id", id);
		param.put("hashNum", hashNum);
		
		Event event = (Event) getSqlMapClientTemplate(). queryForObject("Event.findByPrimaryKey",param);
		return event;
	}



}
