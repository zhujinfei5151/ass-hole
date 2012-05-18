package com.tmall.asshole.schedule;
/***
 * 
 * @author jiuxian.tjo
 *
 */
public interface IScheduleFgetcPolicy {
	
	/**
	 * 读取线程需要要抓取的数据范围 de 开始index
	 * 
	 * @return
	 */
	int getStartIndex();

	/**
	 * 读取线程需要要抓取的数据范围 de 结束index
	 * 
	 * @return
	 */
	int getEndIndex();
	
	
	/***
	 * 一次取的数量
	 * 
	 * @return
	 */
	int getRowNum();
	

}
