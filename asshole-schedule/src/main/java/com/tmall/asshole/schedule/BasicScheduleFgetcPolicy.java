package com.tmall.asshole.schedule;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
/******
 * 
 *  基本抓取算法
 *  
 * 
 * @author tangjinou
 *
 */
public class BasicScheduleFgetcPolicy implements IScheduleFgetcPolicy , Watcher{
	
	private int startIndex;
	
	private int endIndex;
	
	private int rownum;

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
	public int getRowNum() {
		return rownum;
	}
	
	public void process(WatchedEvent event){
		   //有的机器上线状态
		
		
	}


}
