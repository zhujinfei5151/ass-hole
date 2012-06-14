package com.tmall.asshole.schedule;

public interface IDataProcessorCallBack<T,C> {
	
	 void callback(T data,C context) throws Exception;

}
