package com.tmall.asshole.schedule;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.tmall.asshole.schedule.fgetcplolicy.BasicScheduleFgetcPolicy;
import com.tmall.asshole.zkclient.ZKConfig;

public class BasicScheduleFgetcPolicyTest extends TestCase{
	static ZKConfig zkConfig1;
	
	static{
		zkConfig1=new ZKConfig();
		zkConfig1.setRootPath("/test");
		zkConfig1.setZkConnectString("localhost:2181");
		zkConfig1.setZkSessionTimeout(2000);
		zkConfig1.setLocalIPAddressForTest("192.168.1.4");
	}
	
	public void testBasicScheduleFgetcPolicy(){
		
		BasicScheduleFgetcPolicy basicScheduleFgetcPolicy =new BasicScheduleFgetcPolicy(zkConfig1);
		
		List<String> machines = new ArrayList<String>();
		
		machines.add(zkConfig1.getLocalIPAddress());
		
		machines.add("192.168.1.6");
		
		machines.add("192.168.1.5");
		
		basicScheduleFgetcPolicy.onChange(machines);
		
		Assert.assertEquals(0, basicScheduleFgetcPolicy.getStartIndex());
		
		Assert.assertEquals(3332, basicScheduleFgetcPolicy.getEndIndex());
		
		machines.clear();

		machines.add("192.168.1.1");
		
		machines.add("192.168.1.2");
		
        machines.add(zkConfig1.getLocalIPAddress());
        
        basicScheduleFgetcPolicy.onChange(machines);
		
       Assert.assertEquals(6666, basicScheduleFgetcPolicy.getStartIndex());
		
		Assert.assertEquals(10000, basicScheduleFgetcPolicy.getEndIndex());
		
		
	}
	

}
