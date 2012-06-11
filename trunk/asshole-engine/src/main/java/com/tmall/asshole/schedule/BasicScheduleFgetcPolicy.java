package com.tmall.asshole.schedule;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.zkclient.INodeChange;
import com.tmall.asshole.zkclient.ZKConfig;

/******
 * 
 * @author tangjinou
 *
 */
public class BasicScheduleFgetcPolicy implements IScheduleFgetcPolicy , INodeChange{
	
	private int startIndex;
	
	private int endIndex;
	
	private int rownum=1000;
	
	private int max_hash_num=10000;
	
	@Autowired
	private ZKConfig zKConfig;
	
	private List<String> _machines=new ArrayList<String>();
	
	private Lock lock = new ReentrantLock();

	
	public BasicScheduleFgetcPolicy(ZKConfig zKConfig){
		 this.zKConfig = zKConfig;
	}
	public BasicScheduleFgetcPolicy() {
	}
	
	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
	public int getRowNum() {
		return rownum;
	}

	public void onChange(List<String> machines) {
	      Collections.sort(machines);
	      try{
	      lock.lock();
	      
	      for (String ip : machines) {
			    if(!_machines.contains(ip)){
			    	_machines.clear();
			    	_machines.addAll(machines);
			    	onChange();
			    }
		  }
	      }catch (Exception e) {
	    	  
		 }
	      finally{
	    	  lock.unlock();
	      }
	}

	private void onChange() {
		int machine_num = _machines.size();
		int index = _machines.indexOf(zKConfig.getLocalIPAddress());
		int hash_range= max_hash_num/machine_num;
		
		if((machine_num-1)==index){
			//最后一台机器
			startIndex =  index * hash_range;
			endIndex =max_hash_num;
		}else{
			startIndex = index * hash_range;
			endIndex = (index+1) * hash_range -1;
		}
		
		
		  
	}

	
	


}
