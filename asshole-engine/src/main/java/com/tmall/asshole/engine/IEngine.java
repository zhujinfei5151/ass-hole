package com.tmall.asshole.engine;


/***
 * 
 * @author tangjinou
 *
 */
public interface IEngine<F,C> {
	/**
	 * @param fuel
	 * @param context
	 * @return
	 */
	boolean fire(F fuel,C context) throws Exception;
}
