package com.tmall.asshole.schedule;


/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IDataProducer<T> {
     public void addData(T t) throws Exception;
}
