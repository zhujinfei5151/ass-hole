package com.tmall.asshole.zkclient.data;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ChangeData {
	
	private NodeData nodeData;
	private NodeData parentNodeData;
	private List<String> machines;
	private WatchedEvent  event;

	public ChangeData(NodeData data,NodeData parentNodeData,List<String> machines,WatchedEvent event) {
		this.nodeData = data;
		this.parentNodeData=parentNodeData;
		this.machines=machines;
		this.event = event;
	}
	
	public ChangeData(List<String> machines){
		this.machines=machines;
	}
	

	public NodeData getNodeData() {
		return nodeData;
	}

	public void setNodeData(NodeData nodeData) {
		this.nodeData = nodeData;
	}

	public NodeData getParentNodeData() {
		return parentNodeData;
	}

	public void setParentNodeData(NodeData parentNodeData) {
		this.parentNodeData = parentNodeData;
	}

	public List<String> getMachines() {
		return machines;
	}

	public void setMachines(List<String> machines) {
		this.machines = machines;
	}

	public WatchedEvent getEvent() {
		return event;
	}

	public void setEvent(WatchedEvent event) {
		this.event = event;
	}

	
	
	

}
