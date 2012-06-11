package com.tmall.asshole.engine;

import java.util.Map;

public interface IHandlerRegister<K,F,C> {

	public void registHandler(String eventName,IHandler<F, C> handler);
	
	public void registHandler(Map<K,IHandler<F, C>> paramMap);
}
