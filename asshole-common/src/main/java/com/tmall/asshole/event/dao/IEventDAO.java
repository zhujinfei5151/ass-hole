package com.tmall.asshole.event.dao;

import java.util.List;

import com.taobao.common.dao.persistence.exception.DAOException;
import com.tmall.asshole.event.common.Event;

public interface IEventDAO {

	/**
	 * 插入数据
	 * @return 插入数据的主锄1�7
	 * @throws DAOException 
	 */
	public Long insertEventDO(Event eventDo) throws DAOException;

	/**
	 * 更新记录
	 * @return 受影响的行数
	 */
	public Integer updateEvent(Event eventDo)throws DAOException;

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<Event> queryEvent(int start, int end, int count, String env)throws DAOException;

	/**
	 * 批量修改状�1�7�1�7
	 * @return
	 */
	public Integer batchChangeEventStatus(int oldSatus, int newStatus)throws DAOException;

	/**
	 * 批量修改
	 * @return
	 */
	public Integer batchChangeEventStatusBytime(int from, int to,int minute)throws DAOException;

}






