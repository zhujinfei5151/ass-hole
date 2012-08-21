package com.tmall.asshole.schedule;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IDataProcessorCallBack<T,C> {
	
	 void callback(T data,C context) throws Exception;

}
