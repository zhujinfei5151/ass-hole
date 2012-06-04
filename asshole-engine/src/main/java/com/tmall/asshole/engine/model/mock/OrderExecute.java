package com.tmall.asshole.engine.model.mock;

public class OrderExecute {
	
	public boolean execute(Order o,String s){
		System.out.println("service order execute!");
		System.out.println(s);
		System.out.println("name:"+o.getName());
		return true;
	}
}
