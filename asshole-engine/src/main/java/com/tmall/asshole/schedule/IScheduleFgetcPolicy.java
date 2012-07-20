package com.tmall.asshole.schedule;

import com.tmall.asshole.zkclient.INodeChange;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IScheduleFgetcPolicy extends INodeChange{
	
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
	
	/***
	 *  获取本地IP地址
	 * @return
	 */
	String getExecuteMachineAlias();
	
	
	int getMaxHashNum();
	

}
