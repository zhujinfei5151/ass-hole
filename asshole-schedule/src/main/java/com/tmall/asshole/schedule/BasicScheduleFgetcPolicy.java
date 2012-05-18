package com.tmall.asshole.schedule;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class BasicScheduleFgetcPolicy implements IScheduleFgetcPolicy , Watcher{

	public int getStartIndex() {
		return 0;
	}

	public int getEndIndex() {
		return 0;
	}
	public void process(WatchedEvent event){
		
	}

	public int getRowNum() {
		return 0;
	}

}
