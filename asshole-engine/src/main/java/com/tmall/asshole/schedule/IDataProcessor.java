package com.tmall.asshole.schedule;

/***
 * @author jiuxian.tjo
 */
public interface IDataProcessor<T> {
	
	/**
	 * 处理过程 处理完的数据一定要显示更改为已处理，不然下次还会取到
	 * 
	 * @param data
	 * @throws SchedulerException
	 */
	public abstract void process(T data) throws Exception;

}
