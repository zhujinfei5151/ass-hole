package com.tmall.asshole.zkclient;

import java.util.List;


/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface INodeChange {
	
	void onChange(List<String> machines);

}
