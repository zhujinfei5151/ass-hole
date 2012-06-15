package com.tmall.asshole.schedule.fgetcplolicy;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.schedule.IScheduleFgetcPolicy;
import com.tmall.asshole.zkclient.ZKConfig;

/******
 * 
 * @author tangjinou
 *
 */
public class BasicScheduleFgetcPolicy implements IScheduleFgetcPolicy{
	private static transient Log logger = LogFactory
			.getLog(BasicScheduleFgetcPolicy.class);
	
	private int startIndex;
	
	private int endIndex;
	
	private int rownum=1000;
	
	private int max_hash_num=10000;
	
	@Autowired
	private ZKConfig zKConfig;
	
	private List<String> _machines=new ArrayList<String>();
	
	private Lock lock = new ReentrantLock();
	
	public ZKConfig getZKConfig() {
		return zKConfig;
	}
	public void setZKConfig(ZKConfig zKConfig) {
		this.zKConfig = zKConfig;
	}
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
		  if(machines==null){
			  this.startIndex=0;
			  this.endIndex=0;
			  this.rownum=0;
			  logger.info("machines == null , so change startIndex=0,endIndex=0,rownum=0");
			  return;
		  }
		
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
		//for test
		if(zKConfig==null){
			zKConfig = new ZKConfig();
		}
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
	@Override
	public String getExecuteMachineAlias() {
		return zKConfig.getLocalIPAddress()==null?"": zKConfig.getLocalIPAddress();
	}

	
	


}
