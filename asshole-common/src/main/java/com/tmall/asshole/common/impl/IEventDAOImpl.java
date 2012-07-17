package com.tmall.asshole.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;

public class IEventDAOImpl extends SqlMapClientDaoSupport implements IEventDAO {

	/**
	 * ����
	 */
	public Long insertEventDO(Event eventDo){
		Object id = getSqlMapClientTemplate().insert("Event.insert", eventDo);
	        return (Long) id;
	}
    /**
     * ����
     */
	public Integer updateEventDO(Event eventDo) {
		Object id = getSqlMapClientTemplate().update("Event.update", eventDo);
        return  (Integer) id;
	}
	
	/**
	 * ������ѯ  ���ؽ����ʱ���Ⱥ�˳�򷵻�
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
	 * �����޸�״̬
	 */
	public Integer batchChangeEventStatus(int from, int to) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("from", from);
		param.put("to", to);
		Integer count = getSqlMapClientTemplate().update("Event.batchChangeEventStatus", param);
		return count;
	}

	
	/**
	 * ��id��hash_num��ѯ
	 */
	public Event queryEventByPrimaryKey(Long id,Integer hashNum){
		Map param = new HashMap<String,Integer>();
		param.put("id", id);
		param.put("hashNum", hashNum);
		
		Event event = (Event) getSqlMapClientTemplate(). queryForObject("Event.findByPrimaryKey",param);
		return event;
	}



}
