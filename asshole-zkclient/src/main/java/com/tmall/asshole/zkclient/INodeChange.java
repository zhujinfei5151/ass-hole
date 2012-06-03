package com.tmall.asshole.zkclient;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;

import com.tmall.asshole.zkclient.data.NodeData;

/****
 *  
 * @author tangjinou
 */
public interface INodeChange {
	
	void onChange(NodeData data,NodeData parentNodeData,List<String> machines,WatchedEvent event);
	

}
