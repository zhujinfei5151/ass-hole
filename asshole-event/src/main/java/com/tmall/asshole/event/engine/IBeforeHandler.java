package com.tmall.asshole.event.engine;

/***
 * 
 * @author tangjinou
 *
 * @param <F>
 * @param <C>
 */
public interface IBeforeHandler<F,C> {
	boolean beforeHandle(F event,C context) throws Exception;
}
