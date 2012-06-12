package com.tmall.asshole.schedule;

import java.util.List;

import com.tmall.asshole.common.EventEnv;
import com.tmall.asshole.common.ScheduleType;

/***
 * @author jiuxian.tjo
 * @param <T>
 */
public interface IDataLoader<T> {
	
	/**
	 * 获取需要处理的数据列表
	 * @param scheduleType TODO
	 * 
	 * @return
	 * @throws LoaderException
	 */
	public List<T> getDataList(int start, int end,int rownum,
			EventEnv envionmentGroup, ScheduleType scheduleType) throws Exception;

}
