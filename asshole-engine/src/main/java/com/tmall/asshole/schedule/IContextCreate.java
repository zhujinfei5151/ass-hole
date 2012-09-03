package com.tmall.asshole.schedule;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IContextCreate<T,C> {
	C create(T t);
}
