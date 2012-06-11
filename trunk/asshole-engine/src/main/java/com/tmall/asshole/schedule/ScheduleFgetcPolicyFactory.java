package com.tmall.asshole.schedule;

public class ScheduleFgetcPolicyFactory {

	public IScheduleFgetcPolicy create(String algorithmType) {
        	return new BasicScheduleFgetcPolicy();
	}
	
	

}
