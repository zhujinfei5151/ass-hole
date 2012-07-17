package com.tmall.asshole.common;

import java.util.List;


/****
 * 
 * @author tangjinou
 *
 */
public interface IEventDAO {
	/**
     * @param dao
     * @return 
     */
    public Long insertEventDO(Event dao);
    
    public List<Event> queryEvent(int start, int end, int count, int env, int processorNumber);
    
    public Integer updateEventDO(Event dao);
    
    public Integer batchChangeEventStatus(int from, int to);    
	
	public Event queryEventByPrimaryKey(Long id,Integer hashNum);
	
	public Long queryCountOfUnExecuteEvent();
}
