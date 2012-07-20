package com.tmall.asshole.engine;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IBeforeHandler<F,C> {
	boolean beforeHandle(F event,C context) throws Exception;
}
