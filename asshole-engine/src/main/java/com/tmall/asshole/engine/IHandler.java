package com.tmall.asshole.engine;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IHandler<F,C> {
	boolean handle(F event,C context) throws Exception;
}
