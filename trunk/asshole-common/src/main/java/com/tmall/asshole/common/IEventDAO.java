package com.tmall.asshole.common;

import java.util.List;


/****
 * DAO 存储不一定是DB
 * 
 * @author tangjinou
 *
 */
public interface IEventDAO {
	/**
     * 插入数据
     * @param dao
     * @return 插入数据的主锄1�7
     */
    public Integer insertEventDO(Event dao);
    
    public List<Event> queryEvent(int start, int end, int count, int env, int scheduleType);
    
    public Integer updateEventDO(Event dao);
    
    public Integer batchChangeEventStatus(int from, int to);    
	
	public Event queryEventByPrimaryKey(Long id,Integer hash_num);
}
