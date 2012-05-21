package com.tmall.asshole.event.common;

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
     * @return 插入数据的主键
     */
    public Integer insertServiceEventDO(Event dao);
    
    public List<Event> queryEvent(int start, int end, int count, String env);
    
    public Integer updateServiceEventDO(Event dao);
    
    public Integer batchChangeEventStatus(int from, int to);
    
	public Integer batchChangeEventStatusBytime(int from, int to, int minute);
	
	public Event queryEventByPrimaryKey(Long id, Integer hashNum);
}
