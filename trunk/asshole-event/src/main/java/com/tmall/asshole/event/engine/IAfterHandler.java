package com.tmall.asshole.event.engine;
/****
 * 
 * @author tangjinou
 *
 */
public interface IAfterHandler<F,C> {
	 boolean afterHandle(F f, C c) throws Exception;
}
