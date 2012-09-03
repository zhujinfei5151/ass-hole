package com.tmall.asshole.common;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
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
    
	/**
     * 根据instanceId返回当前所有流程   倒排序
     */
    public List<Event> queryEventList(Long processInstanceId);
    
    /**
     * 根据当前节点 返回流程中上一个节点 
     * <br>如果当前节点不存在，返回null</br>
     * <br>否则返回按时间逆序最近的一个节点 ；</br>
     */
    public Event queryLastNodeEvent(Long processInstanceId,String currentNode);

}
