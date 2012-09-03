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
    public List<Event> queryEventList(int processInstanceId);
    
    /**
     * 根据当前节点 返回流程中上一个节点 
     * <br>如果上一个节点存在，则正常返回 ；</br>
     * <br>如果当前节点已经是第一个节点，返回本身；</br>
     * <br>如果当前节点不存在，返回null</br>
     */
    public Event queryLastNodeEvent(int processInstanceId,String currentNode);

}
