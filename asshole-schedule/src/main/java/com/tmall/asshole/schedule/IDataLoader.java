package com.tmall.asshole.schedule;

import java.util.List;

/***
 * @author jiuxian.tjo
 * @param <T>
 */
public interface IDataLoader<T> {
	
	/**
	 * 获取需要处理的数据列表
	 * 
	 * @return
	 * @throws LoaderException
	 */
	public List<T> getDataList(int start, int end,int rownum,
			String envionmentGroup) throws Exception;

}
