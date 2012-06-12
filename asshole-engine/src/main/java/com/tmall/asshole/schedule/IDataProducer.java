package com.tmall.asshole.schedule;


/***
 * 
 * @author tangjinou
 *
 * @param <T>
 */
public interface IDataProducer<T> {
     public void addData(T t) throws Exception;
}
