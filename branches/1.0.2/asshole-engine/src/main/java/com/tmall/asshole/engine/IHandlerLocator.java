package com.tmall.asshole.engine;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IHandlerLocator<F,C> {

	IHandler<F, C> lookup(String queryCriteria) throws  Exception;
}