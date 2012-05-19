package com.tmall.asshole.event.engine;

/*****
 * 
 * @author tangjinou
 *
 * @param <F>
 * @param <C>
 */
public interface IHandler<F,C> {
	boolean handle(F event,C context) throws Exception;
}
