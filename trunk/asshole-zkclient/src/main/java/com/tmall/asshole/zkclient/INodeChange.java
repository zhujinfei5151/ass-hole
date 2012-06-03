package com.tmall.asshole.zkclient;

import java.util.List;


/****
 *  
 * @author tangjinou
 */
public interface INodeChange {
	
	void onChange(List<String> machines);

}
