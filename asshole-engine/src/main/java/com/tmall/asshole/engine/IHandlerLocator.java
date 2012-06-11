package com.tmall.asshole.engine;

/****
 * @author tangjinou
 *
 * @param <F>
 * @param <C>
 */
public interface IHandlerLocator<F,C> {

	IHandler<F, C> lookup(String queryCriteria) throws  Exception;
}